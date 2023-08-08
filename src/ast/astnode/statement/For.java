package src.ast.astnode.statement;

import src.ast.Position;
import src.ast.Visitor;
import src.ast.astnode.expression.ExpressionNode;
import src.ast.astnode.definition.VariableDefinition;

public class For extends baseloop {
    public VariableDefinition var;
    public ExpressionNode exp1,exp2;

    public For(Position pos_) {
        super(pos_);
    }

    @Override
    public void accept(Visitor vis) {
        vis.visit(this);
    }
}
