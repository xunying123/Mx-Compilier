package src.IR.instructments;

import src.IR.basic.IRBasic;
import src.IR.basic.IRBlock;
import src.IR.basic.IRRegister;
import src.IR.basic.IRVisitor;
import src.IR.irtype.IRIntConst;
import src.IR.irtype.IRType;

import java.util.LinkedHashSet;

public class IRCalc extends IROrders{
    public IRType type;
    public String op;
    public IRRegister res;
    public IRBasic ll,rr;

    public IRCalc(IRBlock bb,IRType tt,IRRegister r,IRBasic ll,IRBasic rr,String op) {
        super(bb);
        this.type=tt;
        this.res=r;
        this.op=op;
        this.ll=ll;
        this.rr=rr;
    }

    @Override
    public String toString() {
        return res + " = " +op+" "+ll.toStringT()+", "+rr;
    }

    @Override
    public void accept(IRVisitor vis) {
        vis.visit(this);
    }

    public IRIntConst calc() {
        if(ll instanceof IRIntConst && rr instanceof IRIntConst) {
            int lv = ((IRIntConst)ll).value;
            int rv = ((IRIntConst)rr).value;
            int rr = 0;
            switch (op) {
                case "add" -> rr=lv+rv;
                case "sub" -> rr=lv-rv;
                case "mul" -> rr=lv*rv;
                case "sdiv" -> {
                    if(rv == 0) return null;
                    rr=lv/rv;
                }
                case "srem" -> rr=lv%rv;
                case "shl" -> rr=lv<<rv;
                case "asdr" -> rr=lv>>rv;
                case "and" -> rr=lv&rv;
                case "or" -> rr=lv|rv;
                case "xor" -> rr=lv^rv;
            }
            return new IRIntConst(rr);
        }
        return null;
    }
}
