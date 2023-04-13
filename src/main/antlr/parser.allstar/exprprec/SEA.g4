// Problem 3 of the 2022-Compilers-A exam
grammar SEA;

@header {
package parser.allstar.exprprec;
}

s : e ;
e : e '!'
  | <assoc=right> e '^' e
  | e '+' e
  | ID
  ;

ID : [a-z]+ ;
WS : [ \t\r\n]+ -> skip ;