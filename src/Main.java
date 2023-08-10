package src;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import src.antlr.MxLexer;
import src.antlr.MxParser;
import src.ast.AstBuilder;
import src.ast.astnode.FileAnalyze;
import src.semantic.*;

public class Main {
    public static void main(String[] args) throws Exception {
        CharStream input = CharStreams.fromStream(System.in);
        MxLexer lexer = new MxLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(new MxErrorListener());
        MxParser parser = new MxParser(new CommonTokenStream(lexer));
        parser.removeErrorListeners();
        parser.addErrorListener(new MxErrorListener());
        ParseTree parseTreeRoot = parser.fileAnalyze();
        AstBuilder astbuilder = new AstBuilder();
        FileAnalyze fileA = (FileAnalyze) astbuilder.visit(parseTreeRoot);
        GlobalScope globalScope = new GlobalScope();
        new SymbolCollector(globalScope).visit(fileA);

    }
}
