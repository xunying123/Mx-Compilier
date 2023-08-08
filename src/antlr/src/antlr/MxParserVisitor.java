// Generated from C:/Users/xun_y/IdeaProjects/Mx-Compilier/src/antlr\MxParser.g4 by ANTLR 4.12.0
package src.antlr;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MxParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MxParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MxParser#fileAnalyze}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFileAnalyze(MxParser.FileAnalyzeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#blocks}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlocks(MxParser.BlocksContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction(MxParser.FunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#parameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterList(MxParser.ParameterListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameter(MxParser.ParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#classDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDeclaration(MxParser.ClassDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#classBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassBody(MxParser.ClassBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#construction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstruction(MxParser.ConstructionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#functionCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCall(MxParser.FunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(MxParser.IdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#basicVariable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBasicVariable(MxParser.BasicVariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#typename}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypename(MxParser.TypenameContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(MxParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#variableDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclaration(MxParser.VariableDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#newExperssion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewExperssion(MxParser.NewExperssionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#newVariable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewVariable(MxParser.NewVariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#blockStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStatement(MxParser.BlockStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(MxParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#stringLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringLiteral(MxParser.StringLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#thisLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThisLiteral(MxParser.ThisLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#logicLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicLiteral(MxParser.LogicLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#nullLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNullLiteral(MxParser.NullLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#numberLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumberLiteral(MxParser.NumberLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#expressionStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionStatement(MxParser.ExpressionStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#emptyStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmptyStatement(MxParser.EmptyStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#branchStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBranchStatement(MxParser.BranchStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#loopStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoopStatement(MxParser.LoopStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#continueStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinueStatement(MxParser.ContinueStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#breakStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakStatement(MxParser.BreakStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(MxParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#controlStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitControlStatement(MxParser.ControlStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(MxParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(MxParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#newArrayExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewArrayExpression(MxParser.NewArrayExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#newArrayEmpty}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewArrayEmpty(MxParser.NewArrayEmptyContext ctx);
}