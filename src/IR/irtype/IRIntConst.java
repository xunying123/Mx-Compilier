package src.IR.irtype;

public class IRIntConst extends IRConst{
    public int value;

    public IRIntConst(int value) {
        super(irInt);
        this.value=value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(IRConst ot) {
        return (ot instanceof IRIntConst) && (((IRIntConst)ot).value==value);
    }

    @Override
    public boolean isZero() {
        return value==0;
    }

    @Override
    public String toStringT() {
        return "i32 " +toString();
    }
}
