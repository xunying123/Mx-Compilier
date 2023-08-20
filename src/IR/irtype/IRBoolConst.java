package src.IR.irtype;

public class IRBoolConst extends IRConst {
    public boolean value;

    public IRBoolConst(boolean va) {
        super(irBool);
        this.value = va;
    }

    @Override
    public boolean isZero() {
        return !value;
    }

    @Override
    public boolean equals(IRConst ot) {
        return (ot instanceof IRBoolConst) && (((IRBoolConst) ot).value == value);
    }

    @Override
    public String toString() {
        return value ? "1" : "0";
    }

    @Override
    public String toStringT() {
        return "i8 " + toString();
    }
}
