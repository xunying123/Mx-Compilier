// Generated from C:/Users/xun_y/IdeaProjects/Mx-Compilier/src/antlr\MxParser.g4 by ANTLR 4.12.0
package src.antlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MxParser}.
 */
public interface MxParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MxParser#fileAnalyze}.
	 * @param ctx the parse tree
	 */
	void enterFileAnalyze(MxParser.FileAnalyzeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#fileAnalyze}.
	 * @param ctx the parse tree
	 */
	void exitFileAnalyze(MxParser.FileAnalyzeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#blocks}.
	 * @param ctx the parse tree
	 */
	void enterBlocks(MxParser.BlocksContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#blocks}.
	 * @param ctx the parse tree
	 */
	void exitBlocks(MxParser.BlocksContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#function}.
	 * @param ctx the parse tree
	 */
	void enterFunction(MxParser.FunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#function}.
	 * @param ctx the parse tree
	 */
	void exitFunction(MxParser.FunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void enterParameterList(MxParser.ParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void exitParameterList(MxParser.ParameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(MxParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(MxParser.ParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassDeclaration(MxParser.ClassDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassDeclaration(MxParser.ClassDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#classBody}.
	 * @param ctx the parse tree
	 */
	void enterClassBody(MxParser.ClassBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#classBody}.
	 * @param ctx the parse tree
	 */
	void exitClassBody(MxParser.ClassBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#construction}.
	 * @param ctx the parse tree
	 */
	void enterConstruction(MxParser.ConstructionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#construction}.
	 * @param ctx the parse tree
	 */
	void exitConstruction(MxParser.ConstructionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCall(MxParser.FunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCall(MxParser.FunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(MxParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(MxParser.IdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#basicVariable}.
	 * @param ctx the parse tree
	 */
	void enterBasicVariable(MxParser.BasicVariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#basicVariable}.
	 * @param ctx the parse tree
	 */
	void exitBasicVariable(MxParser.BasicVariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#typename}.
	 * @param ctx the parse tree
	 */
	void enterTypename(MxParser.TypenameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#typename}.
	 * @param ctx the parse tree
	 */
	void exitTypename(MxParser.TypenameContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(MxParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(MxParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaration(MxParser.VariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaration(MxParser.VariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#newExperssion}.
	 * @param ctx the parse tree
	 */
	void enterNewExperssion(MxParser.NewExperssionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#newExperssion}.
	 * @param ctx the parse tree
	 */
	void exitNewExperssion(MxParser.NewExperssionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#newVariable}.
	 * @param ctx the parse tree
	 */
	void enterNewVariable(MxParser.NewVariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#newVariable}.
	 * @param ctx the parse tree
	 */
	void exitNewVariable(MxParser.NewVariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void enterBlockStatement(MxParser.BlockStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void exitBlockStatement(MxParser.BlockStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(MxParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(MxParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#stringLiteral}.
	 * @param ctx the parse tree
	 */
	void enterStringLiteral(MxParser.StringLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#stringLiteral}.
	 * @param ctx the parse tree
	 */
	void exitStringLiteral(MxParser.StringLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#thisLiteral}.
	 * @param ctx the parse tree
	 */
	void enterThisLiteral(MxParser.ThisLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#thisLiteral}.
	 * @param ctx the parse tree
	 */
	void exitThisLiteral(MxParser.ThisLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#logicLiteral}.
	 * @param ctx the parse tree
	 */
	void enterLogicLiteral(MxParser.LogicLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#logicLiteral}.
	 * @param ctx the parse tree
	 */
	void exitLogicLiteral(MxParser.LogicLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#nullLiteral}.
	 * @param ctx the parse tree
	 */
	void enterNullLiteral(MxParser.NullLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#nullLiteral}.
	 * @param ctx the parse tree
	 */
	void exitNullLiteral(MxParser.NullLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#numberLiteral}.
	 * @param ctx the parse tree
	 */
	void enterNumberLiteral(MxParser.NumberLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#numberLiteral}.
	 * @param ctx the parse tree
	 */
	void exitNumberLiteral(MxParser.NumberLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void enterExpressionStatement(MxParser.ExpressionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void exitExpressionStatement(MxParser.ExpressionStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#emptyStatement}.
	 * @param ctx the parse tree
	 */
	void enterEmptyStatement(MxParser.EmptyStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#emptyStatement}.
	 * @param ctx the parse tree
	 */
	void exitEmptyStatement(MxParser.EmptyStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#branchStatement}.
	 * @param ctx the parse tree
	 */
	void enterBranchStatement(MxParser.BranchStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#branchStatement}.
	 * @param ctx the parse tree
	 */
	void exitBranchStatement(MxParser.BranchStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#loopStatement}.
	 * @param ctx the parse tree
	 */
	void enterLoopStatement(MxParser.LoopStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#loopStatement}.
	 * @param ctx the parse tree
	 */
	void exitLoopStatement(MxParser.LoopStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#continueStatement}.
	 * @param ctx the parse tree
	 */
	void enterContinueStatement(MxParser.ContinueStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#continueStatement}.
	 * @param ctx the parse tree
	 */
	void exitContinueStatement(MxParser.ContinueStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void enterBreakStatement(MxParser.BreakStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void exitBreakStatement(MxParser.BreakStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(MxParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(MxParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#controlStatement}.
	 * @param ctx the parse tree
	 */
	void enterControlStatement(MxParser.ControlStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#controlStatement}.
	 * @param ctx the parse tree
	 */
	void exitControlStatement(MxParser.ControlStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(MxParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(MxParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(MxParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(MxParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#newArrayExpression}.
	 * @param ctx the parse tree
	 */
	void enterNewArrayExpression(MxParser.NewArrayExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#newArrayExpression}.
	 * @param ctx the parse tree
	 */
	void exitNewArrayExpression(MxParser.NewArrayExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#newArrayEmpty}.
	 * @param ctx the parse tree
	 */
	void enterNewArrayEmpty(MxParser.NewArrayEmptyContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#newArrayEmpty}.
	 * @param ctx the parse tree
	 */
	void exitNewArrayEmpty(MxParser.NewArrayEmptyContext ctx);
}