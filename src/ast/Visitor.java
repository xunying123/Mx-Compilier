package src.ast;

import src.ast.astnode.statement.*;
import src.ast.astnode.definition.*;
import src.ast.astnode.*;


public interface Visitor {
    public void visit(TypeNode node);
    public void visit(VariableDefinition node);
    public void visit(ParameterNode node);
    public void visit(Break node);
    public void visit (Continue node);
    public void visit(Expression node);
    public void visit(For node);
    public void visit(If node);
    public void visit(Return node);
    public void visit (While node);
}
