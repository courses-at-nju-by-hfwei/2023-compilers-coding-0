grammar Expr;

@header {
package ag;
}

prog : stat+ ;

stat : expr
     | ID '=' expr
     ;

expr returns [int val]
    : left = expr op = ('*' | '/') right = expr
    | left = expr op = ('+' | '-') right = expr
    | '(' expr ')'
    | ID
    | INT
    ;

ADD : '+' ;
SUB : '-' ;
MUL : '*' ;
DIV : '/' ;

ID : [a-z] ;
INT : [0-9] ;
WS : [ \t\r\n] -> skip;