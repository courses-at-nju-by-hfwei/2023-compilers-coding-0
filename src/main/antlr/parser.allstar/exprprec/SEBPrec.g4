// The translated grammar for SEB.g4
grammar SEBPrec;

@header {
package parser.allstar.exprprec;
}

s : e[0] EOF;

e[int _p]
    : (  '-' e[4]
       | INT
      )
      (  {3 >= $_p}? '^' e[3]
       | {2 >= $_p}? '+' e[3]
      )*
    ;

INT : [1-9] ;
WS  : [ \t\n\r]+ -> skip ;