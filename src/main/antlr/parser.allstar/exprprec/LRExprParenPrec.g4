grammar LRExprParenPrec;

// Bugs in the antlr plugin? It does not parse correctly.
// Test with command line (remember to comment out the following @header line).

@header {
package parser.allstar.exprprec;
}

stat : expr[0] ';' EOF;

expr[int _p]
    : (  '(' expr[0] ')'
       | INT
       | ID
      )
      (  {5 >= $_p}? '*' expr[6]
       | {4 >= $_p}? '+' expr[5]
      )*
    ;

ID : [a-z] ;
INT : [0-9] ;
WS  : [ \t\n\r]+ -> skip ;