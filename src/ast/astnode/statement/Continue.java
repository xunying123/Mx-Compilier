package src.ast.astnode.statement;

import src.ast.Position;
import src.ast.Visitor;
import src.ast.astnode.statement.*;

public class Continue extends Statement{
    public Continue(Position pos) {
        super(pos);
    }

    @Override
    public void accept(Visitor vis) {
        vis.visit(this);
    }
}
