package src.codegen;

import src.IR.basic.*;
import src.IR.instructments.*;
import src.IR.irtype.*;
import src.ast.BuiltIn;
import src.ast.Type;
import src.ast.Visitor;
import src.ast.astnode.FileAnalyze;
import src.ast.astnode.ParameterNode;
import src.ast.astnode.TypeNode;
import src.ast.astnode.definition.*;
import src.ast.astnode.expression.*;
import src.ast.astnode.statement.*;
import src.semantic.GlobalScope;
import src.semantic.Scope;

import java.util.ArrayList;
import java.util.HashMap;

public class IRBuilder implements Visitor, BuiltIn {
    IRFunction currentFunction = null;
    IRStruct currentStruct = null;
    IRBlock currentBlock = null;
    GlobalScope globalScope;
    Scope currentScope;
    IRFileAnalyze root;
    HashMap<String, IRStruct> structMap = new HashMap<>();
    HashMap<String, Integer> arraySize = new HashMap<>();
    int index = -1;

    public IRBuilder(IRFileAnalyze rr, GlobalScope gg) {
        this.root = rr;
        this.globalScope = gg;
        this.currentScope = gg;
    }

    @Override
    public void visit(TypeNode node) {
        node.irType = typeTrans(node.type, false);
    }

    @Override
    public void visit(VariableDeclaration node) {
        node.dec.forEach(uu -> uu.accept(this));
    }

    @Override
    public void visit(ParameterNode node) {
        for (index = 0; index < node.list.size(); index++) {
            Declaration dd = node.list.get(index);
            assert dd.iniVal == null;
            dd.accept(this);
            IRRegister in = new IRRegister("", dd.type.irType);
            currentFunction.para.add(in);
            currentBlock.add(new IRStore(currentBlock, in, currentScope.getIRVar(dd.name), index + (currentStruct == null ? 0 : 1)));
        }
        index = -1;
    }

    @Override
    public void visit(Break node) {
        currentBlock.ter = new IRJump(currentBlock, currentScope.inLoo.next);
        currentBlock.isFinished = true;
    }

    @Override
    public void visit(Continue node) {
        if (currentScope.inLoo instanceof While) {
            currentBlock.ter = new IRJump(currentBlock, currentScope.inLoo.cond);
        } else {
            currentBlock.ter = new IRJump(currentBlock, ((For) currentScope.inLoo).block);
        }
        currentBlock.isFinished = true;
    }

    @Override
    public void visit(Expression node) {
        if (node.express != null) node.express.accept(this);
    }

    @Override
    public void visit(For node) {
        currentScope = new Scope(currentScope, node);
        if (node.var != null) node.var.accept(this);
        if (node.exp1 != null) node.exp1.accept(this);
        node.cond = new IRBlock(currentFunction, "for.cond_", currentBlock.depth + 1);
        node.loop = new IRBlock(currentFunction, "for,loop_", currentBlock.depth + 1);
        node.block = new IRBlock(currentFunction, "for.step_", currentBlock.depth + 1);
        node.next = new IRBlock(currentFunction, "for.end_", currentBlock.depth);
        node.next.ter = new IRJump(currentBlock, node.cond);
        currentBlock.isFinished = true;
        currentBlock = currentFunction.add(node.cond);
        if (node.loopExp != null) {
            node.loopExp.accept(this);
            currentBlock.ter = new IRBranch(currentBlock, getCond(node.loopExp), node.loop, node.next);
        } else {
            currentBlock.ter = new IRJump(currentBlock, node.loop);
        }
        currentBlock.isFinished = true;
        currentScope = new Scope(currentScope);
        currentBlock = currentFunction.add(node.loop);
        node.sta.forEach(ss -> ss.accept(this));
        currentBlock.ter = new IRJump(currentBlock, node.block);
        currentBlock.isFinished = true;
        currentBlock = currentFunction.add(node.block);
        currentScope = currentScope.parent;
        if (node.exp2 != null) node.exp2.accept(this);
        currentBlock.ter = new IRJump(currentBlock, node.cond);
        currentBlock.isFinished = true;
        currentBlock = currentFunction.add(node.next);
        currentScope = currentScope.parent;
    }

    @Override
    public void visit(If node) {
        node.exp.accept(this);
        IRBasic cond = getCond(node.exp);
        IRBlock tt = currentBlock;
        IRBlock next = new IRBlock(currentFunction, "if.end_", currentBlock.depth);
        next.ter = currentBlock.ter;
        IRBlock then = new IRBlock(currentFunction, "if.then_", next, currentBlock.depth);
        currentScope = new Scope(currentScope);
        currentBlock.isFinished = true;
        currentBlock = currentFunction.add(then);
        node.trueS.forEach(ss -> ss.accept(this));
        currentScope = currentScope.parent;
        if (node.falseS != null && !node.falseS.isEmpty()) {
            IRBlock elseB = new IRBlock(currentFunction, "if.end_", currentBlock.depth);
            currentScope = new Scope(currentScope);
            currentBlock.isFinished = true;
            currentBlock = currentFunction.add(elseB);
            node.falseS.forEach(ss -> ss.accept(this));
            currentScope = currentScope.parent;
            tt.ter = new IRBranch(tt, cond, then, elseB);
        } else {
            tt.ter = new IRBranch(tt, cond, then, next);
        }
        currentBlock.isFinished = true;
        currentBlock = currentFunction.add(next);
    }

    @Override
    public void visit(Return node) {
        if (node.exp != null) {
            node.exp.accept(this);
            addStore(currentFunction.ret, node.exp);
        }
        currentBlock.ter = new IRJump(currentBlock, currentFunction.exit);
        currentBlock.isFinished = true;
    }

    @Override
    public void visit(While node) {
        node.cond = new IRBlock(currentFunction, "while.cond_", currentBlock.depth + 1);
        node.loop = new IRBlock(currentFunction, "while.loop_", currentBlock.depth + 1);
        node.next = new IRBlock(currentFunction, "while.end_", currentBlock.depth);
        node.next.ter = currentBlock.ter;
        currentBlock.ter = new IRJump(currentBlock, node.cond);
        currentBlock.isFinished = true;
        currentBlock = currentFunction.add(node.cond);
        node.loopExp.accept(this);
        currentBlock.ter = new IRBranch(currentBlock, getCond(node.loopExp), node.loop, node.next);
        currentBlock = currentFunction.add(node.loop);
        currentScope = new Scope(currentScope, node);
        node.sta.forEach(ss -> ss.accept(this));
        currentScope = currentScope.parent;
        currentBlock.ter = new IRJump(currentBlock, node.cond);
        currentBlock.isFinished = true;
        currentBlock = currentFunction.add(node.next);
    }

    @Override
    public void visit(ArrayEx node) {
        node.array.accept(this);
        node.index.accept(this);
        IRRegister dest = new IRRegister("", getValue(node.array).type);
        currentBlock.add(new IRGetelementptr(currentBlock, getValue(node.array), dest, getValue(node.index)));
        node.store = dest;
    }

    @Override
    public void visit(BinaryEx node) {
        node.leftNode.accept(this);
        if (!node.op.equals("&&") && !node.op.equals("||")) node.rightNode.accept(this);
        else {
            IRRegister temp = new IRRegister(".shortCirTemp", new IRPtr(irBool));
            currentBlock.add(new IRAlloca(currentBlock, irBool, temp));
            IRBlock rB = new IRBlock(currentFunction, "rheBlock_", currentBlock.depth);
            IRBlock trueB = new IRBlock(currentFunction, "trueBlock_", currentBlock.depth);
            IRBlock falseB = new IRBlock(currentFunction, "falseBlock_", currentBlock.depth);
            IRBlock nB = new IRBlock(currentFunction, "shortCir.end_", currentBlock.depth);
            nB.ter = node.op.equals("&&") ? new IRBranch(currentBlock, getCond(node.leftNode), rB, falseB) : new IRBranch(currentBlock, getCond(node.leftNode), trueB, rB);
            currentBlock.isFinished = true;
            currentBlock = currentFunction.add(rB);
            node.rightNode.accept(this);
            currentBlock.ter = new IRBranch(currentBlock, getCond(node.rightNode), trueB, falseB);
            currentBlock.isFinished = true;
            currentBlock = currentFunction.add(trueB);
            currentBlock.add(new IRStore(currentBlock, irBoolTrue, temp));
            currentBlock.ter = new IRJump(currentBlock, nB);
            currentBlock.isFinished = true;
            currentBlock = currentFunction.add(falseB);
            currentBlock.add(new IRStore(currentBlock, irBoolFalse, temp));
            currentBlock.ter = new IRJump(currentBlock, nB);
            currentBlock.isFinished = true;
            currentBlock = currentFunction.add(nB);
            IRRegister ltemp = new IRRegister(".loadTemp", irBool);
            currentBlock.add(new IRLoad(currentBlock, ltemp, temp));
            node.value = new IRRegister("", irCond);
            currentBlock.add(new IRTrunc(currentBlock, (IRRegister) node.value, ltemp, irCond));
            return;
        }
        IRRegister dest = null;
        IRType opType = null;
        String op = null;
        if (node.leftNode.type.equals(string) || node.rightNode.type.equals(string)) {
            switch (node.op) {
                case "+" -> {
                    node.value = new IRRegister("", irString);
                    currentBlock.add(new IRCall(currentBlock, (IRRegister) node.value, irString, "mx_stradd", getValue(node.leftNode), getValue(node.rightNode)));
                }
                case "<" -> {
                    node.value = stringCmp("mx_strlt", getValue(node.leftNode), getValue(node.rightNode));
                }
                case "<=" -> {
                    node.value = stringCmp("mx_strle", getValue(node.leftNode), getValue(node.rightNode));
                }
                case ">" -> {
                    node.value = stringCmp("mx_strgt", getValue(node.leftNode), getValue(node.rightNode));
                }
                case ">=" -> {
                    node.value = stringCmp("mx_strge", getValue(node.leftNode), getValue(node.rightNode));
                }
                case "==" -> {
                    node.value = stringCmp("mx_streq", getValue(node.leftNode), getValue(node.rightNode));
                }
                case "!=" -> {
                    node.value = stringCmp("mx_strne", getValue(node.leftNode), getValue(node.rightNode));
                }
            }
        } else {
            IRBasic ll = getValue(node.leftNode);
            IRBasic rr = getValue(node.rightNode);
            switch (node.op) {
                case "+" -> {
                    if (ll instanceof IRConst && rr instanceof IRConst) {
                        node.value = new IRIntConst(((IRIntConst) ll).value + ((IRIntConst) rr).value);
                        op = "add";
                        break;
                    }
                }
                case "-" -> {
                    if (ll instanceof IRConst && rr instanceof IRConst) {
                        node.value = new IRIntConst(((IRIntConst) ll).value - ((IRIntConst) rr).value);
                        op = "sub";
                        break;
                    }
                }
                case "*" -> {
                    if (ll instanceof IRConst && rr instanceof IRConst) {
                        node.value = new IRIntConst(((IRIntConst) ll).value * ((IRIntConst) rr).value);
                        op = "mul";
                        break;
                    }
                }
                case "/" -> {
                    if (ll instanceof IRConst && rr instanceof IRConst) {
                        node.value = new IRIntConst(((IRIntConst) ll).value / ((IRIntConst) rr).value);
                        op = "sdiv";
                        break;
                    }
                }
                case "%" -> {
                    if (ll instanceof IRConst && rr instanceof IRConst) {
                        node.value = new IRIntConst(((IRIntConst) ll).value % ((IRIntConst) rr).value);
                        op = "srem";
                        break;
                    }
                }
                case "<<" -> {
                    if (ll instanceof IRConst && rr instanceof IRConst) {
                        node.value = new IRIntConst(((IRIntConst) ll).value << ((IRIntConst) rr).value);
                        op = "shl";
                        break;
                    }
                }
                case ">>" -> {
                    if (ll instanceof IRConst && rr instanceof IRConst) {
                        node.value = new IRIntConst(((IRIntConst) ll).value >> ((IRIntConst) rr).value);
                        op = "ashr";
                        break;
                    }
                }
                case "&" -> {
                    if (ll instanceof IRConst && rr instanceof IRConst) {
                        node.value = new IRIntConst(((IRIntConst) ll).value & ((IRIntConst) rr).value);
                        op = "and";
                        break;
                    }
                }
                case "|" -> {
                    if (ll instanceof IRConst && rr instanceof IRConst) {
                        node.value = new IRIntConst(((IRIntConst) ll).value | ((IRIntConst) rr).value);
                        op = "or";
                        break;
                    }
                }
                case "^" -> {
                    if (ll instanceof IRConst && rr instanceof IRConst) {
                        node.value = new IRIntConst(((IRIntConst) ll).value ^ ((IRIntConst) rr).value);
                        op = "xor";
                        break;
                    }
                }
                case "<" -> {
                    if (ll instanceof IRConst && rr instanceof IRConst) {
                        node.value = new IRCondConst(((IRIntConst) ll).value < ((IRIntConst) rr).value);
                        op = "slt";
                        break;
                    }
                }
                case "<=" -> {
                    if (ll instanceof IRConst && rr instanceof IRConst) {
                        node.value = new IRCondConst(((IRIntConst) ll).value <= ((IRIntConst) rr).value);
                        op = "sle";
                        break;
                    }
                }
                case ">" -> {
                    if (ll instanceof IRConst && rr instanceof IRConst) {
                        node.value = new IRCondConst(((IRIntConst) ll).value > ((IRIntConst) rr).value);
                        op = "sgt";
                        break;
                    }
                }
                case ">=" -> {
                    if (ll instanceof IRConst && rr instanceof IRConst) {
                        node.value = new IRCondConst(((IRIntConst) ll).value >= ((IRIntConst) rr).value);
                        op = "sge";
                        break;
                    }
                }
                case "==" -> {
                    if (ll instanceof IRConst && rr instanceof IRConst) {
                        node.value = new IRCondConst(((IRIntConst) ll).value != ((IRIntConst) rr).value);
                        op = "eq";
                        break;
                    }
                }
                case "!=" -> {
                    if (ll instanceof IRConst && rr instanceof IRConst) {
                        node.value = new IRCondConst(((IRIntConst) ll).value != ((IRIntConst) rr).value);
                        op = "ne";
                        break;
                    }
                }
            }
            if (node.value != null) return;
            switch (node.op) {
                case "+", "-", "*", "/", "%", "<<", ">>", "&", "|", "^" -> {
                    opType = irInt;
                    dest = new IRRegister("", irInt);
                    currentBlock.add(new IRCalc(currentBlock, opType, dest, ll, rr, op));
                }
                case ">", ">=", "<", "<=" -> {
                    opType = irInt;
                    dest = new IRRegister("", irCond);
                    currentBlock.add(new IRIcmp(currentBlock, opType, dest, ll, rr, op));
                }
                case "==", "!=" -> {
                    if (ll.type instanceof IRInt && ll.type != irInt) {
                        IRRegister tmp = new IRRegister("", irInt);
                        currentBlock.add(new IRZext(currentBlock, tmp, ll, irInt));
                        ll = tmp;
                    }
                    if (rr.type instanceof IRInt && rr.type != irInt) {
                        IRRegister tmp = new IRRegister("", irInt);
                        currentBlock.add(new IRZext(currentBlock, tmp, rr, irInt));
                        rr = tmp;
                    }
                    opType = node.leftNode.type.equals(Null) ? node.rightNode.getIRType() : node.leftNode.getIRType();
                    dest = new IRRegister("tmp", irCond);
                    currentBlock.add(new IRIcmp(currentBlock, opType, dest, ll, rr, op));
                }

            }
            node.value = dest;
        }
    }

    @Override
    public void visit(AssignEx node) {
        node.rightNode.accept(this);
        node.leftNode.accept(this);
        node.store = node.leftNode.store;
        node.value = getValue(node.rightNode);
        addStore(node.store, node.rightNode);
    }

    @Override
    public void visit(ObjectEx node) {
        node.obj.accept(this);
        IRType objType = getValue(node.obj).type;
        node.objAddr = (IRRegister) node.obj.value;
        assert objType instanceof IRPtr;
        objType = ((IRPtr) objType).PointTo();
        if (objType instanceof IRStruct) {
            IRType mem = ((IRStruct) objType).get(node.objTo);
            if (mem != null) {
                node.store = new IRRegister("", new IRPtr(mem));
                currentBlock.add(new IRGetelementptr(currentBlock, getValue(node.obj), node.store, BuiltIn.irInt0, new IRIntConst(((IRStruct) objType).offset.get(node.objTo))));
            }
        }
    }

    @Override
    public void visit(FunctionCall node) {
        node.express.forEach(ee -> ee.accept(this));
    }

    @Override
    public void visit(FunctionEx node) {
        node.functi.accept(this);
        FunctionDefinition fun = node.functi.func;
        String funcName = fun.className == null ? fun.Name : fun.className + "." + fun.Name;
        fun.returnType.irType = typeTrans(fun.returnType.type, true);
        IRCall call = new IRCall(currentBlock, fun.returnType.irType, funcName);
        if (fun == ArraySizeFunc) {
            IRRegister arr = ((ObjectEx) node.functi).objAddr;
            IRRegister t1, t2 = new IRRegister("", irInt);
            if (arr.type.toString().equals("i32*")) {
                t1 = arr;
            } else {
                t1 = new IRRegister("", irInt);
                currentBlock.add(new IRBitcast(currentBlock, arr, irInt, t1));
            }
            currentBlock.add(new IRGetelementptr(currentBlock, arr, t1, t2, irInt1));
            node.value = new IRRegister("", irInt);
            currentBlock.add(new IRLoad(currentBlock, (IRRegister) node.value, t2));
        } else {
            if (fun == StringLengthFunc) call.name = "_mx_strlen";
            else if (fun == substringFunc) call.name = "_mx_substring";
            else if (fun == parseFunc) call.name = "_mx_parseInt";
            else if (fun == ordFunc) call.name = "_mx_ord";

            if (fun.className != null) {
                if (node.functi instanceof ObjectEx) {
                    call.args.add(((ObjectEx) node.functi).objAddr);
                } else {
                    IRRegister thisP = currentScope.getIRVar("this");
                    IRRegister thisVal = new IRRegister("", ((IRPtr) thisP.type).PointTo());
                    currentBlock.add(new IRLoad(currentBlock, thisVal, thisP));
                    call.args.add(thisVal);
                }
            }
            if (node.exps != null) {
                node.exps.accept(this);
                node.exps.express.forEach(aa -> call.args.add(getValue(aa)));
            }
            if (fun.returnType.irType != irVoid) {
                call.call = new IRRegister("", fun.returnType.irType);
            }
            currentBlock.add(call);
            node.value = call.call;
        }
    }

    @Override
    public void visit(NewEXp node) {
        IRType tt = typeTrans(node.type, false);
        if (node.dim > 0) {
            node.value = node.List.size() > 0 ? newArray(tt, 0, node.List) : new IRNullConst(tt);
        } else {
            IRStruct claType = (IRStruct) ((IRPtr) tt).PointTo();
            IRRegister callRe = new IRRegister("", irString);
            currentBlock.add(new IRCall(currentBlock, callRe, irString, "malloc", new IRIntConst(claType.size)));
            node.value = new IRRegister("", tt);
            currentBlock.add(new IRBitcast(currentBlock, callRe, tt, (IRRegister) node.value));
            if (claType.con) {
                currentBlock.add(new IRCall(currentBlock, null, irVoid, claType.name + "." + claType.name, node.value));
            }
        }
    }

    @Override
    public void visit(Untary node) {
        node.exp.accept(this);
        IRRegister dest = null;
        String op = null;
        switch (node.op) {
            case "++" -> {
                op = "add";
                node.value = getValue(node.exp);
                dest = new IRRegister("", irInt);
                currentBlock.add(new IRCalc(currentBlock, irInt, dest, node.value, BuiltIn.irInt1, op));
                currentBlock.add(new IRStore(currentBlock, dest, node.exp.store));
            }
            case "--" -> {
                op = "sub";
                node.value = getValue(node.exp);
                dest = new IRRegister("", irInt);
                currentBlock.add(new IRCalc(currentBlock, irInt, dest, node.value, BuiltIn.irInt1, op));
                currentBlock.add(new IRStore(currentBlock, dest, node.exp.store));
            }
            case "+" -> {
                node.value = getValue(node.exp);
            }
            case "-" -> {
                op = "sub";
                dest = new IRRegister("", irInt);
                currentBlock.add(new IRCalc(currentBlock, irInt, dest, irInt0, getValue(node.exp), op));
            }
            case "~" -> {
                op = "xor";
                dest = new IRRegister("", irInt);
                currentBlock.add(new IRCalc(currentBlock, irInt, dest, getValue(node.exp), irInt1, op));
            }
            case "!" -> {
                assert node.exp.type.equals(Bool);
                op = "not";
                dest = new IRRegister("", irCond);
                currentBlock.add(new IRCalc(currentBlock, irCond, dest, getCond(node.exp), irTrue, op));
                node.value = dest;
            }
        }
    }

    @Override
    public void visit(leftEx node) {
        node.exp.accept(this);
        IRRegister dest = new IRRegister("", irInt);
        String op = null;
        switch (node.op) {
            case "++" -> {
                op = "add";
                currentBlock.add(new IRCalc(currentBlock, irInt, dest, getValue(node.exp), irInt1, op));
                currentBlock.add(new IRStore(currentBlock, dest, node.exp.store));
                node.value = dest;
                node.store = node.exp.store;
            }
            case "--" -> {
                op = "sub";
                currentBlock.add(new IRCalc(currentBlock, irInt, dest, getValue(node.exp), irInt1, op));
                currentBlock.add(new IRStore(currentBlock, dest, node.exp.store));
                node.value = dest;
                node.store = node.exp.store;
            }
        }
    }

    @Override
    public void visit(BlockStatement node) {
        currentScope = new Scope(currentScope);
        node.state.forEach(ss -> ss.accept(this));
        currentScope = currentScope.parent;
    }

    @Override
    public void visit(BasicEx node) {
        if (node.type.equals(Int)) {
            node.value = new IRIntConst(Integer.parseInt(node.ss));
        } else if (node.type.equals(Bool)) {
            node.value = new IRCondConst(node.ss.equals("true"));
        } else if (node.type.equals(string)) {
            IRStringConst ss = root.addString(node.ss.substring(1, node.ss.length() - 1));
            node.value = new IRRegister("", new IRPtr(irChar));
            currentBlock.add(new IRGetelementptr(currentBlock, ss, (IRRegister) node.value, irInt0, irInt0));
        } else if (node.type.equals(Null)) {
            node.value = new IRNullConst();
        } else {
            node.store = currentScope.getIRVar("this");
        }
    }

    @Override
    public void visit(ternaryEx node) {
    }

    @Override
    public void visit(Declaration node) {
        node.type.accept(this);
        if (currentFunction != null) {
            IRRegister dd = new IRRegister(node.name + ".addr", new IRPtr(node.type.irType));
            currentScope.addIRVar(node.name, dd);
            currentBlock.add(new IRAlloca(currentBlock, node.type.irType, dd, index == -1 ? -1 : index + (currentStruct == null ? 0 : 1)));
            if (node.iniVal != null) {
                node.iniVal.accept(this);
                addStore(dd, node.iniVal);
            } else if (node.type.type.isReference() && index == -1) {
                currentBlock.add(new IRStore(currentBlock, new IRNullConst(node.type.irType), dd));
            }
        } else if (currentStruct != null) {
            currentStruct.add(node.type.irType, node.name);
        } else {
            IRGlobalVar gg = new IRGlobalVar(node.name, node.type.irType);
            if (node.iniVal != null && node.iniVal instanceof BasicEx && !node.iniVal.type.equals(string) && !node.iniVal.ss.equals(this)) {
                node.iniVal.accept(this);
                gg.initValue = getValue(node.iniVal) instanceof IRCondConst ? new IRBoolConst(((IRCondConst) node.iniVal.value).value) : node.iniVal.value;
                globalScope.addIRVar(node.name, gg);
            } else {
                gg.initValue = node.type.irType.defaultValue();
                globalScope.addIRVar(node.name, gg);
                if (node.iniVal != null) {
                    gg.CallInit = true;
                    IRFunction tt = currentFunction;
                    IRBlock tb = currentBlock;
                    currentFunction = root.initFunc;
                    currentBlock = root.initBlock;
                    node.iniVal.accept(this);
                    addStore(gg, node.iniVal);
                    root.initBlock = currentBlock;
                    currentFunction = tt;
                    currentBlock = tb;
                }
            }
            root.var.add(gg);
        }
    }

    @Override
    public void visit(FunctionDefinition node) {
        IRBlock.cnt = 0;
        node.returnType.irType = typeTrans(node.returnType.type, true);
        String name = currentStruct != null ? currentStruct.name + "." + node.Name : node.Name;
        currentFunction = new IRFunction(name, node.returnType.irType);
        root.fuc.add(currentFunction);

        currentScope = new Scope(currentScope, node.returnType.type);
        currentBlock = currentFunction.add(new IRBlock(currentFunction, "entry_", 0));
        currentFunction.entry = currentBlock;
        if (currentStruct != null) {
            IRPtr cl = new IRPtr(currentStruct);
            IRRegister tt = new IRRegister("this", cl);
            currentFunction.para.add(tt);
            IRRegister thisA = new IRRegister("this.addr", new IRPtr(cl));
            currentBlock.add(new IRAlloca(currentBlock, cl, thisA, 0));
            currentBlock.add(new IRStore(currentBlock, tt, thisA));
            currentScope.addIRVar("this", thisA);
        }
        if (node.para != null) node.para.accept(this);

        currentFunction.exit = new IRBlock(currentFunction, "return_", 0);
        currentBlock.ter = new IRJump(currentBlock, currentFunction.exit);
        if (node.returnType.type.equals(Void)) {
            currentFunction.exit.ter = new IRRet(currentFunction.exit, irVoidConst);
        } else {
            IRType rVt = node.returnType.irType == irCond ? irBool : node.returnType.irType;
            currentFunction.ret = new IRRegister("retval", new IRPtr(rVt));
            currentFunction.exit.add(new IRAlloca(currentBlock, rVt, currentFunction.ret));
            IRRegister tt;
            IRRegister rr = new IRRegister("ret", rVt);
            currentFunction.exit.add(new IRLoad(currentBlock, rr, currentFunction.ret));
            if (node.returnType.irType == irCond) {
                tt = new IRRegister("", irCond);
                currentFunction.exit.add(new IRTrunc(currentBlock, tt, rr, irCond));
                currentFunction.exit.ter = new IRRet(currentFunction.exit, tt);
            } else {
                currentFunction.exit.ter = new IRRet(currentFunction.exit, rr);
            }
            if (name.equals("main")) root.mainFunc = currentFunction;
            node.sta.forEach(ss -> ss.accept(this));
            node.irFunc = currentFunction;
            currentScope = currentScope.parent;
            currentFunction = null;
            currentBlock = null;
        }
    }

    @Override
    public void visit(Construction node) {
        node.toFunc().accept(this);
    }

    @Override
    public void visit(ClassDefinition node) {
        currentScope = new Scope(currentScope, node);
        currentStruct = structMap.get(node.name);
        root.str.add(currentStruct);
        node.var.forEach(vv -> vv.accept(this));
        if (node.con != null) {
            currentStruct.con = true;
            node.con.accept(this);
        }
        node.func.forEach(ff -> ff.className = node.name);
        node.func.forEach(ff -> ff.accept(this));
        currentScope = currentScope.parent;
        currentStruct = null;
    }

    @Override
    public void visit(VarEx node) {
        node.store = currentScope.getIRVar(node.ss);
        if (node.store == null) {
            IRRegister tt = (IRRegister) currentScope.getIRVar("this");
            if (tt != null) {
                IRType oo = ((IRPtr) tt.type).PointTo();
                IRType or = ((IRPtr) oo).PointTo();
                IRRegister thisVal = new IRRegister("this", oo);
                if (((IRStruct) or).hadMem(node.ss)) {
                    currentBlock.add(new IRLoad(currentBlock, thisVal, tt));
                    node.store = new IRRegister("this." + node.ss, new IRPtr(((IRStruct) or).get(node.ss)));
                    currentBlock.add(new IRGetelementptr(currentBlock, thisVal, node.store, irInt0, new IRIntConst(((IRStruct) or).offset.get(node.ss))));
                }
            }
        }
    }

    @Override
    public void visit(FileAnalyze node) {
        node.allFile.forEach(dd -> {
            if (dd instanceof ClassDefinition) {
                structMap.put(((ClassDefinition) dd).name, new IRStruct(((ClassDefinition) dd).name, ((ClassDefinition) dd).varMap.size() << 2));
            }
        });
        node.allFile.forEach(dd -> {
            if (dd instanceof ClassDefinition) dd.accept(this);
        });
        node.allFile.forEach(dd -> {
            if (dd instanceof VariableDeclaration) dd.accept(this);
        });
        node.allFile.forEach(dd -> {
            if (dd instanceof FunctionDefinition) dd.accept(this);
        });

        if (root.initBlock.insts.isEmpty()) {
            root.initFunc = null;
        } else {
            root.initFunc.finish();
            root.fuc.add(root.initFunc);
            IRBlock mainEn = root.mainFunc.blocks.get(0);
            mainEn.insts.add(new IRCall(mainEn, irVoid, "_mx_global_var_init"));
        }
    }

    private IRBasic getValue(ExpressionNode node) {
        if (node.value != null) {
            return node.value;
        } else {
            assert node.store != null;
            IRRegister val = new IRRegister("", ((IRPtr) node.store.type).PointTo());
            currentBlock.add(new IRLoad(currentBlock, val, node.store));
            return node.value = val;
        }
    }

    private IRBasic getCond(ExpressionNode node) {
        IRBasic cond = getValue(node);
        if (cond.type == irBool) {
            if (cond instanceof IRBoolConst bb) {
                return bb.value ? irTrue : irFalse;
            }
            IRRegister tt = new IRRegister("", irCond);
            currentBlock.add(new IRTrunc(currentBlock, tt, cond, irCond));
            return tt;
        }
        return cond;
    }

    private IRType typeTrans(Type tt, boolean ii) {
        IRType ir;
        switch (tt.name) {
            case "int" -> ir = irInt;
            case "bool" -> ir = irBool;
            case "string" -> ir = irString;
            case "void" -> ir = irVoid;
            default -> ir = new IRPtr(structMap.get(tt.name), 1);
        }
        if (tt.dim > 0) ir = new IRPtr(ir, tt.dim);
        return ir;
    }

    private void addStore(IRRegister pp, ExpressionNode node) {
        if (getValue(node).type == irCond) {
            if (node.value instanceof IRCondConst cc) {
                currentBlock.add(new IRStore(currentBlock, cc.value ? irTrue : irFalse, pp));
            } else {
                IRRegister tt = new IRRegister("", irBool);
                currentBlock.add(new IRZext(currentBlock, tt, node.value, irBool));
                currentBlock.add(new IRStore(currentBlock, tt, pp));
            }
        } else {
            if (node.value instanceof IRNullConst) {
                node.value = new IRNullConst(((IRPtr) pp.type).PointTo());
            }
            currentBlock.add(new IRStore(currentBlock, node.value, pp));
        }
    }

    private IRRegister stringCmp(String na, IRBasic ll, IRBasic rr) {
        IRRegister tt = new IRRegister("", irBool);
        IRRegister r = new IRRegister("", irCond);
        currentBlock.add(new IRCall(currentBlock, tt, irBool, na, ll, rr));
        currentBlock.add(new IRTrunc(currentBlock, r, tt, irCond));
        return r;
    }

    private IRBasic newArray(IRType type, int at, ArrayList<ExpressionNode> list) {
        IRRegister callReg = new IRRegister("", new IRPtr(irChar));
        list.get(at).accept(this);
        IRBasic cnt = getValue(list.get(at));
        IRBasic size;
        int sizeof = ((IRPtr) type).PointTo().size;
        if (cnt instanceof IRIntConst) {
            size = new IRIntConst(((IRIntConst) cnt).value * sizeof + 4);
        } else {
            IRIntConst tySi = new IRIntConst(sizeof);
            IRRegister tmSi = new IRRegister("", irInt);
            currentBlock.add(new IRCalc(currentBlock, irInt, tmSi, cnt, tySi, "mul"));
            size = new IRRegister("", irInt);
            currentBlock.add(new IRCalc(currentBlock, irInt, (IRRegister) size, tmSi, BuiltIn.irInt4, "add"));
        }
        currentBlock.add(new IRCall(currentBlock, callReg, new IRPtr(irChar), "malloc", size));
        IRRegister ptr, tm1 = new IRRegister("", irIntPtr), tm2 = new IRRegister("", irIntPtr);
        currentBlock.add(new IRBitcast(currentBlock, callReg, irIntPtr, tm1));
        currentBlock.add(new IRStore(currentBlock, cnt, tm1));
        currentBlock.add(new IRGetelementptr(currentBlock, tm1, tm2, irInt1));
        if (type.toString().equals("i32*")) ptr = tm2;
        else {
            ptr = new IRRegister("", type);
            currentBlock.add(new IRBitcast(currentBlock, tm2, type, ptr));
        }
        if (at + 1 < list.size()) {
            IRRegister idx = new IRRegister("", irIntPtr);
            currentBlock.add(new IRAlloca(currentBlock, irInt, idx));
            currentBlock.add(new IRStore(currentBlock, irInt0, idx));
            IRBlock cond = new IRBlock(currentFunction, "for.cond_", currentBlock.depth + 1);
            IRBlock loop = new IRBlock(currentFunction, "for,loop_", currentBlock.depth + 1);
            IRBlock step = new IRBlock(currentFunction, "for.step_", currentBlock.depth + 1);
            IRBlock next = new IRBlock(currentFunction, "for.end_", currentBlock.depth);
            next.ter = new IRJump(currentBlock, cond);
            currentBlock.isFinished = true;

            currentBlock = currentFunction.add(cond);
            IRRegister con = new IRRegister("", irCond);
            IRRegister val = new IRRegister("", irInt);
            currentBlock.add(new IRLoad(currentBlock, val, idx));
            currentBlock.add(new IRIcmp(currentBlock, BuiltIn.irInt, con, val, cnt, "slt"));
            currentBlock.ter = new IRBranch(currentBlock, con, loop, next);
            currentBlock.isFinished = true;

            currentBlock = currentFunction.add(loop);
            IRBasic ipVal = newArray(((IRPtr) type).PointTo(), at + 1, list);
            IRRegister val2 = new IRRegister("", irInt);
            IRRegister iPtr = new IRRegister("", type);
            currentBlock.add(new IRLoad(currentBlock, val2, idx));
            currentBlock.add(new IRGetelementptr(currentBlock, ptr, iPtr, val2));
            currentBlock.add(new IRStore(currentBlock, ipVal, iPtr));
            currentBlock.isFinished = true;

            currentBlock = currentFunction.add(step);
            IRRegister iRe = new IRRegister("", irInt);
            IRRegister val3 = new IRRegister("", irInt);
            currentBlock.add(new IRLoad(currentBlock, val3, idx));
            currentBlock.add(new IRCalc(currentBlock, irInt, iRe, val3, irInt1, "add"));
            currentBlock.add(new IRStore(currentBlock, iRe, idx));
            currentBlock.ter = new IRJump(currentBlock, cond);
            currentBlock.isFinished = true;

            currentBlock = currentFunction.add(next);
        }
        return ptr;
    }
}
