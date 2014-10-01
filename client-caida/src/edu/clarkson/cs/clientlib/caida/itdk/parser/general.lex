/* JFlex example: part of Java language lexer specification */
package edu.clarkson.cs.clientlib.caida.itdk.parser;

import java_cup.runtime.*;

%%

%class NodeLexer
%unicode
%line
%column

%cup
%eofval{ 
 return new java_cup.runtime.Symbol(Syms.EOF);
%eofval}

%eofclose
%{
  StringBuffer string = new StringBuffer();

  private Symbol symbol(int type) {
    return new Symbol(type, yyline, yycolumn);
  }
  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline, yycolumn, value);
  }
%}

IP = \d{1,3}"."\d{1,3}"."\d{1,3}"."\d{1,3}
ID = N\d+ 
SPACE = [ \t]
%%
/* rules */
"node"			{ return symbol(Syms.NODE); }
":"				{ return symbol(Syms.COLON); }
{IP}           	{ return symbol(Syms.IP, yytext()); }
{ID}			{ return symbol(Syms.ID); }
{SPACE}			{ }