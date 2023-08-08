package src.ast.astnode.statement;

import src.ast.Position;
import src.ast.Visitor;
import src.ast.astnode.expression.ExpressionNode;
import java.util.ArrayList;

public class If extends Statement {
    public ExpressionNode exp;
    public ArrayList<Statement> trueS=new ArrayList<>();
    public ArrayList<Statement> falseS=new ArrayList<>();

    public If (Position pos_,ExpressionNode exp) {
        super(pos_);
        this.exp=exp;
    }

    @Override
    public void accept(Visitor vis) {
        vis.visit(this);
    }
}
