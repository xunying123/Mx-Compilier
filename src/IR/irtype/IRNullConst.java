package src.IR.irtype;

public class IRNullConst extends IRConst{
    public IRNullConst() {
        super(irNull);
    }

    public IRNullConst (IRType tt) {
        super(tt);
    }

    @Override
    public String toString() {
        return "null";
    }

    @Override
    public boolean isZero() {
        return true;
    }

    @Override
    public boolean equals(IRConst ot) {
        return ot instanceof IRNullConst;
    }

    @Override
    public String toStringT() {
        return type==irNull?toString():type+" "+toString();
    }
}
