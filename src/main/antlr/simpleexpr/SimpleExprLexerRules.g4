lexer grammar SimpleExprLexerRules;

// Comment out the following lines
// Otherwise, there will be duplicate package statements in the genearted files
// @header {
// package simpleexpr;
// }

SEMI : ';' ;
ASSIGN : '=' ;
IF : 'if' ;
MUL : '*' ;
DIV : '/' ;
ADD : '+' ;
SUB : '-' ;
LPAREN : '(' ;
RPAREN : ')' ;

ID : (LETTER | '_') WORD* ;
INT : '0' | ([1-9] DIGIT*) ;
FLOAT : INT '.' DIGIT*
      | '.' DIGIT+
      ;

WS : [ \t\r\n]+ -> skip ;

//SL_COMMENT : '//' .*? '\n' -> skip ;
SL_COMMENT2 : '//' ~[\n]* '\n' -> skip;
DOC_COMMENT : '/**' .*? '*/' -> skip ;
ML_COMMENT : '/*' .*? '*/' -> skip ;

fragment LETTER : [a-zA-Z] ;
fragment DIGIT : [0-9] ;
fragment WORD : LETTER | DIGIT | '_' ;