parser grammar MxParser;

options {
  tokenVocab=MxLexer;
}
// no labels

fileAnalyze : blocks* EOF;

blocks : function | variableDeclaretion | class;

function : typename identifier '(' parameterList? ')' blockStatement;
parameterList : (parameter ',')* parameter;
parameter : typename identifier;
class : 'class' identifier '{' classBody* '}' ';';
classBody : variableDeclaretion | function | construction;
construction : identifier '(' ')' blockStatement;

//会不会匹配错误

identifier : Identifier;
basicVarible : Void | Bool | Int | String ;
typename : basicVarible | identifier | typename '[' ']' ;
declaretion : identifier ('=' experssion)?;
variableDeclaretion : typename (declaretion ',')* declaretion ';';

//会不会有空语句

blockStatement : '{' statement* '}' ;
statement : variableDeclaretion | experssionStatement | branchStatement | loopStatement | controlStatement | blockStatement | emptyStatement;
experssionStatement : experssion ';';
emptyStatement : ';';
branchStatement : If '(' experssion ')' statement (Else statement)?;
loopStatement :   (While '(' expression ')' statement )
                | (For '(' variableDeclaretion experssion? ';'experssion? ')' statement)
                | (For '(' experssion? ';' experssion? ';' experssion? ')'statement);
continueStatement : Continue ';';
breakStatement : Break ';';
returnStatement : Return ';';
controlStatement : continueStatement | breakStatement | returnStatement ;

stringLiteral : StringLiteral;
thisLiteral : 'this';
logicLiteral : LogicLiteral;
nullLiteral : NullLiteral ;
numberLiteral : InterLiteral;


