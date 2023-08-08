package src.ast.astnode.statement;

import src.ast.Position;
import src.ast.astnode.AstNode;

public abstract class Statement extends AstNode{
    public Statement (Position pos) {
        super(pos);
    }
}
