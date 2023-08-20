package src.IR.irtype;

public class IRCondConst extends IRConst {
    public boolean value;

    public IRCondConst(boolean val) {
        super(irCond);
        this.value=val;
    }

    @Override
    public String toString() {
        return value?"true":"false";
    }

    @Override
    public boolean isZero() {
        return !value;
    }

    @Override
    public boolean equals(IRConst ot) {
        return (ot instanceof IRCondConst) && ((IRCondConst) ot).value==value;
    }

    @Override
    public String toStringT() {
        return "i1 "+toString();
    }
}
