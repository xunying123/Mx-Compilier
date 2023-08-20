package src.IR.basic;

import src.IR.irtype.IRType;
import src.ast.BuiltIn;

public abstract class IRBasic implements BuiltIn {
    public IRType type;

    public IRBasic(IRType tt) {
        this.type=tt;
    }

    public abstract String toString () ;

    public abstract String toStringT();
}
