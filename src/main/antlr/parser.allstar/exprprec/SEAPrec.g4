// The translated grammar for SEA.g4
grammar SEAPrec;

@header {
package parser.allstar.exprprec;
}

s : e[0] EOF;

e[int _p]
    : ( INT )
      (  {3 >= $_p}? '^' e[3]
       | {2 >= $_p}? '+' e[3]
       | {4 >= $_p}? '!'
      )*
    ;

INT : [1-9] ;
WS  : [ \t\n\r]+ -> skip ;