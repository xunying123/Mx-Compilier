parser grammar MxParser;

options {
  tokenVocab=MxLexer;
}

fileAnalyze : blocks* EOF;

blocks : function | variableDeclaration | classDeclaration;

function : typename identifier '(' (parameterList?) ')' blockStatement;
parameterList : parameter (',' parameter)*;
parameter : typename identifier;

classDeclaration : 'class' identifier '{' (classBody*) '}' ';';
classBody : variableDeclaration | function | construction;
construction : identifier '(' ')' blockStatement;
functionCall : expression (',' expression)* ;

identifier : Identifier;
basicVariable : Void | Bool | Int | String ;
typename : basicVariable | identifier | typename '[' ']';
declaration : identifier ('=' expression)?;
variableDeclaration : typename declaration (',' declaration)* ';';
newExperssion : 'new' newVariable ('(' ')')?;


newVariable : identifier |
              identifier newArrayExpression+ newArrayEmpty* |
              basicVariable newArrayExpression+ newArrayEmpty*;

blockStatement : '{' statement* '}' ;
statement : variableDeclaration |
            expressionStatement |
            branchStatement |
            loopStatement |
            controlStatement |
            blockStatement |
            emptyStatement;

stringLiteral : StringLiteral;
thisLiteral : 'this';
logicLiteral : 'true' | 'false';
nullLiteral : NullLiteral ;
numberLiteral : InterLiteral;

expressionStatement : expression ';';
emptyStatement : ';';
branchStatement : If '(' expression ')' statement (Else statement)?;
loopStatement : While '(' expression ')' statement |
                For '(' variableDeclaration? ';' expression? ';' expression? ')' statement |
                For '(' expression? ';' expression? ';' expression? ')'statement;
continueStatement : Continue ';';
breakStatement : Break ';';
returnStatement : Return expression? ';';
controlStatement : continueStatement |
                   breakStatement |
                   returnStatement ;

expression :  '(' expression ')'
            | expression '.' identifier
            | expression '.' identifier '(' functionCall ')'
            | expression '(' functionCall ')'
            | expression ('[' expression ']')+
            | expression ('++'|'--')
            | newExperssion
            | <assoc=right> ('++'|'--') expression
            | <assoc=right> ('!' | '-' | '~' | '+') expression
            | expression ('*'|'/'|'%') expression
            | expression ('+'|'-') expression
            | expression ('>>'|'<<') expression
            | expression ('<'|'>'|'<='|'>='|'=='|'!=') expression
            | expression '&' expression
            | expression '|' expression
            | expression '^' expression
            | expression '&&' expression
            | expression '||' expression
            | <assoc=right> expression '=' expression
            | <assoc=right> expression '?' expression ':' expression
            | identifier
            | literal;


literal : stringLiteral |
          thisLiteral |
          logicLiteral |
          nullLiteral |
          numberLiteral ;

newArrayExpression : '[' expression ']';
newArrayEmpty : '[' ']' ;
