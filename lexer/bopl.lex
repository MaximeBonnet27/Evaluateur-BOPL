
/* Dclarations */
package com.aps.parser;

%%
%byaccj

%{
private Parser yyparser;
public Yylex(java.io.Reader r, Parser yyparser){
	this(r);
	this.yyparser = yyparser;
}
%}

num = [0-9]+ ("."[0-9]+)?
fin = [ \n\r\t]
id = [a-zA-Z_] [a-zA-Z0-9_]*
classtype = Obj | Void | Int | Bool
commentaire = \/\/.*[\n\r]
/* Productions */
%%


"("|
")"|
","|
";" { return (int) yycharat(0); }
"+" { return Parser.ADD; }
"-" { return Parser.SUB; }
"*" { return Parser.MUL; }
"/" { return Parser.DIV; }
"=" { return Parser.EQ; }
"<" { return Parser.LT; }
"not" { return Parser.NOT; }
"and" { return Parser.AND; }
"or" { return Parser.OR; }
"null" { return Parser.NIL; }
"true" { return Parser.TRUE; }
"false" { return Parser.FALSE; }
"new" { return Parser.NEW; }
"instanceof" {return Parser.INSTANCEOF;}
"return" {return Parser.RETURN;}
"writeln" {return Parser.WRITELN;}
"program" {return Parser.PROGRAM;}
"class" { return Parser.CLASS; }
"let" { return Parser.LET; }
"in" { return Parser.IN; }
"is" { return Parser.IS; }
"extends" { return Parser.EXTENDS; }
"vars" { return Parser.VARS; }
":=" {return Parser.SET;}
"." {return Parser.GET;}
"if" {return Parser.IF;}
"then" {return Parser.THEN;}
"else" {return Parser.ELSE;}
"do" { return Parser.DO; }
"while" { return Parser.WHILE;}
"begin" {return Parser.BEGIN;}
"end" {return Parser.END;}
"methods" { return Parser.METHODS; }
{classtype} {yyparser.yylval = new ParserVal(yytext().toLowerCase()); return Parser.CLASSTYPE; }
{id} { yyparser.yylval = new ParserVal(yytext().toLowerCase()); return Parser.ID; }
{num} { yyparser.yylval = new ParserVal(Double.parseDouble(yytext())); return Parser.NUM; }
{fin} {}
{commentaire} {}
[^] { System.err.println("Unexpected char : '" + yytext() + "'"); }
