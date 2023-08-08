package src.ast.astnode.definition;

import src.ast.Position;
import src.ast.Visitor;
import src.ast.astnode.AstNode;
import java.util.ArrayList;
import src.ast.astnode.TypeNode;

public class VariableDefinition extends AstNode{
    public String name;
    public TypeNode type;

    public VariableDefinition(Position pos_,String name_,TypeNode type_) {
        super(pos_);
        this.type=type_;
        this.name=name_;
    }

    @Override
    public void accept(Visitor vis) {
        vis.visit(this);
    }
}
