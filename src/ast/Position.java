package src.ast;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

public class Position {
    private final int line;
    private final int column;

    public Position(int line, int column) {
        this.column = column;
        this.line = line;
    }

    public Position(Token token) {
        this.line = token.getLine();
        this.column = token.getCharPositionInLine();
    }

    public Position(ParserRuleContext ctx) {
        this(ctx.getStart());
    }

    public String tostring() {
        return line + ":" + column;
    }

    public int line () {
        return this.line;
    }

    public int column () {
        return this.column;
    }

}
