grammar SimpleExpr;

prog : stat* EOF ;

stat : expr ';'
     | ID '=' expr ';'
     | 'if' expr ';'
     ;

expr : expr ('*' | '/') expr
     | expr ('+' | '-') expr
     | '(' expr ')'
     | ID
     | INT
     | FLOAT
     ;



// ?

SEMI : ';' ;
ASSIGN : '=' ;
IF : 'if' ;
MUL : '*' ;
DIV : '/' ;
ADD : '+' ;
SUB : '-' ;
LPAREN : '(' ;
RPAREN : ')' ;
ID : (LETTER | '_') (LETTER | DIGIT | '_')* ;
INT : '0' | [1-9] DIGIT* ;

FLOAT : INT '.' DIGIT*
      | '.' DIGIT+
      ;

WS : [ \t\r\n]+ -> skip ;

//SL_COMMENT : '//' .*? '\n' -> skip ;
SL_COMMENT2 : '//' ~[\n]* '\n' -> skip ;
DOC_COMMENT : '/**' .*? '*/' -> skip ;
ML_COMMENT : '/*' .*? '*/' -> skip ;

fragment DIGIT : [0-9] ;
fragment LETTER : [a-zA-Z] ;