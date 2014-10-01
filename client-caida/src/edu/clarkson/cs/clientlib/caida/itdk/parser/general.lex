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

ID = [a-zA-Z][0-9]+
IP = [0-9]{1,3}"."[0-9]{1,3}"."[0-9]{1,3}"."[0-9]{1,3}
SPACE = [ \t]+
%%
/* rules */
"node"		{ return symbol(Syms.NODE); }
":"			{ return symbol(Syms.COLON); }
{ID}		{ return symbol(Syms.ID, yytext()); }
{IP}		{ return symbol(Syms.IP, yytext()); }
{SPACE}		{  }
.			{ throw new IllegalArgumentException("Unrecognized:" + yytext()); }