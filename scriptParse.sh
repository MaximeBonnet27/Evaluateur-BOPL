#! /bin/bash

jflex lexer/bopl.lex
mv lexer/Yylex.java src/com/aps/parser/
lexer/byacc -Jpackage="com.aps.parser" lexer/bopl.y && mv Parser.java src/com/aps/parser/ && mv ParserVal.java src/com/aps/parser/

