package src.ast.astnode.statement;

import src.ast.Position;
import src.ast.Visitor;
import src.ast.astnode.expression.ExpressionNode;

public class Expression  extends Statement{
    public ExpressionNode expre;
    public  Expression(Position pos,ExpressionNode node) {
        super(pos);
        this.expre=node;
    }

    @Override
    public void accept(Visitor vis) {
        vis.visit(this);
    }
}
