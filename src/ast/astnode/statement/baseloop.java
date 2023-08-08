package src.ast.astnode.statement;

import src.ast.Position;
import src.ast.astnode.expression.ExpressionNode;
import src.ast.astnode.statement.Statement;
import java.util.ArrayList;
import java.util.Locale;

public abstract class baseloop extends Statement{
    public ExpressionNode exp;
    public ArrayList<Statement> sta =new ArrayList<>();

    public baseloop(Position pos) {
        super(pos);
    }
}
