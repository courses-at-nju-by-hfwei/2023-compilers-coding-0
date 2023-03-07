grammar SimpleExpr;
import SimpleExprLexerRules;

@header {
package simpleexpr;
}

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