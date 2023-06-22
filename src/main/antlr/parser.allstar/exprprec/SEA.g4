// Problem 3 of the 2022-Compilers-A exam
grammar SEA;

@header {
package parser.allstar.exprprec;
}

s : e ;
e : e '!'
  | <assoc=right> e '^' e
  | e '+' e
  | INT
  ;

INT : [1-9]+ ;
WS : [ \t\r\n]+ -> skip ;