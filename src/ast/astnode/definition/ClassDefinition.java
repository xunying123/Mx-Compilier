package src.ast.astnode.definition;

import src.ast.Position;
import src.ast.Visitor;
import src.ast.astnode.AstNode;

import java.util.ArrayList;
import java.util.HashMap;

public class ClassDefinition extends AstNode {
    public String name;
    public ArrayList<VariableDeclaration> var=new ArrayList<>();
    public ArrayList<FunctionDefinition> func=new ArrayList<>();
    public Construction con;
    public HashMap<String,FunctionDefinition> funcMap =new HashMap<>();
    public HashMap<String,Declaration> varMap = new HashMap<>();

    public ClassDefinition(Position pos_,String name) {
        super(pos_);
        this.name=name;
    }

    public FunctionDefinition getFunc(String name) {
        return funcMap.get(name);
    }

    @Override
    public void accept(Visitor vis) {
        vis.visit(this);
    }
}
