package src.IR.basic;

import src.IR.instructments.*;

import java.util.LinkedList;

public class IRBlock {
    public String name;
    public IRFunction parent = null;
    public LinkedList<IROrders> insts = new LinkedList<>();
    public IRTerminal ter = null;
    public int depth;
    public boolean isFinished = false;
    public static int cnt = 0;

    public IRBlock(IRFunction fun, String name, int depth) {
        this.parent = fun;
        this.name = name + String.valueOf(cnt++);
        this.depth = depth;
    }

    public IRBlock(IRFunction fun, String na, IRBlock tot, int dep) {
        this(fun, na, dep);
        this.ter = new IRJump(this, tot);
    }

    public void add(IROrders ooo) {
        if (isFinished) return;
        if (ooo instanceof IRAlloca) {
            parent.alloca.add((IRAlloca) ooo);
        } else if (ooo instanceof IRTerminal) {
            ter = (IRTerminal) ooo;
        } else {
            insts.add(ooo);
        }
    }

    public String toString() {
        String rr = name + ":\n";
        for (IROrders o : insts) rr += " " + o + "\n";
        if (ter != null) rr += " " + ter + "\n";
        return rr;
    }

    public void accept(IRVisitor vis) {
        vis.visit(this);
    }
}
