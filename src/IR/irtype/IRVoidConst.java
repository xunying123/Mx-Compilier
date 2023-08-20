package src.IR.irtype;

public class IRVoidConst extends IRConst{
    public IRVoidConst() {
        super(irVoid);
    }

    @Override
    public String toString() {
        return "void";
    }

    @Override
    public boolean isZero() {
        return false;
    }

    @Override
    public boolean equals(IRConst ot) {
        return ot instanceof IRVoidConst;
    }

    @Override
    public String toStringT() {
        return toString();
    }
}
