package src.semantic;

import src.ast.Type;
import src.ast.astnode.definition.ClassDefinition;
import src.ast.astnode.definition.FunctionDefinition;
import src.ast.astnode.statement.baseloop;

import java.util.HashMap;

public class Scope {
    public HashMap<String, Type> members = new HashMap<>();
    public Scope parent = null;
    public boolean inLoop = false;
    public boolean inClass = false;
    FunctionDefinition inFun = null;
    ClassDefinition inCla = null;
    baseloop inLoo = null;

    public Scope() {
    }


    public Scope(Scope par) {
        this.parent = par;
        this.inLoop=par.inLoop;
        this.inClass=par.inClass;
        this.inFun=par.inFun;
        this.inCla=par.inCla;
        this.inLoo=par.inLoo;
    }

    public Type getType(String name) {
        if(members.containsKey(name)) {
            return members.get(name);
        } else {
            return parent==null? null:parent.getType(name);
        }

    }

    public void addMem(String name, Type t) {
        members.put(name,t);
    }

    public boolean hasMem(String name) {
        return members.containsKey(name);
    }



}
