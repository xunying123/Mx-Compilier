package src.IR.irtype;

import src.IR.basic.IRBasic;
import src.ast.BuiltIn;

public class IRInt extends IRType{
    public int length;

    public IRInt(int length_) {
        super("i"+String.valueOf(length_),length_/8);
        this.length=length_;
    }

    @Override
    public String toString() {
        return "i"+String.valueOf(length);
    }

    @Override
    public IRBasic defaultValue() {
        return BuiltIn.irInt0;
    }
}
