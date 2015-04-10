//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";



package com.aps.parser;



//#line 5 "lexer/bopl.y"

import java.io.*;
import java.util.*;
import com.aps.ast.*;
import com.aps.ast.call.*;
import com.aps.ast.declarations.*;
import com.aps.ast.expressions.*;
import com.aps.ast.instructions.*;
import com.aps.ast.operators.*;

//#line 28 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short FIN=257;
public final static short NUM=258;
public final static short CLASSTYPE=259;
public final static short ID=260;
public final static short NIL=261;
public final static short TRUE=262;
public final static short FALSE=263;
public final static short NEW=264;
public final static short INSTANCEOF=265;
public final static short SET=266;
public final static short GET=267;
public final static short CLASS=268;
public final static short EXTENDS=269;
public final static short IS=270;
public final static short NOT=271;
public final static short AND=272;
public final static short OR=273;
public final static short EQ=274;
public final static short LT=275;
public final static short ADD=276;
public final static short SUB=277;
public final static short MUL=278;
public final static short DIV=279;
public final static short RETURN=280;
public final static short WRITELN=281;
public final static short IF=282;
public final static short THEN=283;
public final static short ELSE=284;
public final static short PROGRAM=285;
public final static short BEGIN=286;
public final static short END=287;
public final static short LET=288;
public final static short IN=289;
public final static short VARS=290;
public final static short METHODS=291;
public final static short DO=292;
public final static short WHILE=293;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,   10,   10,   11,   11,   12,   12,    3,   13,   14,
   14,   15,   15,   16,   16,    2,    2,   17,   17,   18,
   19,   19,   20,   21,   21,   22,    7,    7,    8,    8,
    9,    4,    4,    5,    5,    5,    6,    6,    6,    6,
    6,    6,    6,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    1,   23,   23,   24,   24,
};
final static short yylen[] = {                            2,
    4,    0,    1,    1,    2,    0,    3,    3,    6,    0,
    2,    0,    2,    0,    2,    1,    1,    1,    2,    3,
    1,    3,    1,    1,    2,    7,    0,    1,    3,    1,
    2,    0,    1,    3,    2,    1,    6,    3,    5,    2,
    2,    6,    4,    1,    1,    1,    1,    1,    3,    6,
    2,    3,    3,    3,    3,    3,    3,    3,    3,    2,
    3,    3,    0,    1,    1,    3,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    3,    0,   23,    0,    0,    0,
    5,    0,    0,   16,   17,    0,    0,   18,    0,    1,
   11,    0,    0,    0,    7,   19,   47,   44,   45,   46,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   33,
    0,    0,    0,    0,   20,    0,   60,    0,   48,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    8,    0,    0,    0,    0,    9,
   22,    0,    0,    0,   62,   61,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   34,    0,    0,    0,   24,
    0,    0,   43,    0,    0,    0,   25,    0,    0,    0,
    0,    0,    0,    0,    0,   42,    0,    0,    0,    0,
    0,   30,   50,    0,   31,    0,    0,    0,   29,   26,
};
final static short yydgoto[] = {                          2,
   38,   16,   20,   39,   40,   41,  110,  111,  112,    4,
    5,   10,    6,   13,   44,   70,   17,   18,   23,   49,
   89,   90,  102,  103,
};
final static short yysindex[] = {                      -271,
 -250,    0, -219, -244,    0, -250,    0, -221, -235, -234,
    0, -235, -206,    0,    0, -219, -243,    0,  -40,    0,
    0, -222,    8,   27,    0,    0,    0,    0,    0,    0,
 -235,  158,  158,  158,  158,  158,  158, -167, -214,    0,
   15, -186, -235, -210,    0, -219,    0, -185,    0,  -73,
  -73, -107, -188,  136, -235, -219,  158,  158,  158,  158,
  158,  158,  158,  158,    0,  -40,  158, -235, -235,    0,
    0, -219, -234, -234,    0,    0,  -32, -185,  -94, -135,
 -135,  -94,  -94, -185, -185,    0,  -73, -219, -235,    0,
   54, -189,    0,  158,  158,   57,    0,  158, -234,  -73,
  -73,   60,   58, -235,   74,    0,    0,  158, -219,   76,
   67,    0,    0,  -73,    0, -244, -235, -234,    0,    0,
};
final static short yyrindex[] = {                         0,
 -227,    0,    0, -159,    0, -223,    0, -142,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0, -158,    0,
    0, -259,    0,   81,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
 -154,  176,    0, -233,    0,    0,    0,   -8,    0,  -58,
  -57,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0, -152,    0, -249,    0,    0,
    0,    0,    0,    0,    0,    0,  191,   13,   75,  -31,
  -10,   95,  115,   34,   55,    0,  -55,    0, -230,    0,
  -29,    0,    0,    0,  112,    0,    0,  112,    0,  -54,
  -38,    0,  116,  120,    0,    0,  160,    0,    0,    0,
  121,    0,    0,   -1,    0, -159,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   88,   14,  -52,    0,   97,    0,    0,    0,   47,    0,
  169,   63,    0,    0,    0,    0,  137,   -6,  135,    4,
    0,   98,   90,    0,
};
final static int YYTABLESIZE=470;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         37,
   40,   41,   65,   38,   39,   65,    8,   95,   12,   58,
   26,   49,   58,    1,   49,   14,   15,    3,   13,   24,
   92,   93,   42,   14,   15,   21,   12,   58,   12,   49,
   59,   12,   51,   59,   14,   51,   13,   15,   13,   66,
    7,   13,   66,    9,   47,   25,  106,   12,   59,   24,
   51,   19,   14,   52,   14,   15,   52,   15,    2,   77,
    2,   26,    4,   22,    4,  120,   45,   43,   76,   42,
   46,   52,   65,   66,   56,   91,   55,   56,   72,   67,
   69,   72,   88,   57,   58,   59,   60,   61,   62,   63,
   64,   96,   56,   98,   99,   57,  104,   55,   57,   56,
  107,  108,   88,   74,   57,   58,   59,   60,   61,   62,
   63,   64,  115,   57,  113,   53,  116,  109,   53,   48,
   50,   51,   52,   53,   54,  117,    6,   10,   32,   55,
  109,   72,   36,   53,   35,   54,   57,   58,   54,   21,
   61,   62,   63,   64,   78,   79,   80,   81,   82,   83,
   84,   85,   63,   54,   87,   55,   64,   55,   55,   72,
   27,   28,   86,  119,   57,   58,   59,   60,   61,   62,
   63,   64,   72,   55,   11,   73,   75,   57,  118,   68,
   71,  100,  101,   63,   64,  101,   97,  105,    0,    0,
    0,   55,    0,   72,    0,  114,    0,   37,   57,   58,
   59,   60,   61,   62,   63,   64,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   27,   37,    7,
   28,   29,   30,   31,    0,    0,    0,    0,   40,   41,
   32,   38,   39,   94,    0,   49,    0,   49,    0,   33,
   34,   35,   49,   49,   49,   49,   49,   49,   49,   49,
    0,   58,   36,   49,    0,   58,   51,   49,    0,    0,
   58,    0,   49,   51,   51,   51,   51,   51,   51,   51,
   51,    0,   59,    0,   51,    0,   59,   52,   51,    0,
    0,   59,    0,   51,   52,   52,   52,   52,   52,   52,
   52,   52,    0,    0,    0,   52,    0,    0,   56,   52,
    0,    0,    0,    0,   52,   56,   56,   56,   56,   56,
   56,   56,   56,    0,    0,    0,   56,    0,    0,   57,
   56,    0,    0,    0,    0,   56,   57,   57,   57,   57,
   57,   57,   57,   57,    0,    0,    0,   57,    0,   53,
    0,   57,    0,    0,    0,    0,   57,   53,   53,   53,
   53,   53,    0,    0,    0,    0,    0,   53,    0,   54,
    0,   53,    0,    0,    0,    0,   53,   54,   54,   54,
   54,   54,    0,    0,    0,    0,    0,   54,    0,   55,
    0,   54,    0,    0,    0,    0,   54,   55,   55,   55,
   55,   55,    0,    0,    0,    0,    0,   55,    0,    0,
   55,   55,   72,    0,    0,    0,   55,   57,   58,   59,
   60,   61,   62,   63,   64,   27,    0,    7,   28,   29,
   30,   31,    0,    0,   50,    0,   50,    0,   32,    0,
    0,   50,   50,   50,   50,   50,   50,   50,   50,    0,
   48,    0,   48,    0,    0,    0,   37,   48,   48,   48,
   48,   48,   48,   48,   48,   49,    0,   49,    0,    0,
    0,    0,   49,   49,   49,   49,   49,   49,   49,   49,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   59,   59,   41,   59,   59,   44,    3,   40,  268,   41,
   17,   41,   44,  285,   44,  259,  260,  268,  268,   16,
   73,   74,   19,  259,  260,   12,  286,   59,  288,   59,
   41,  291,   41,   44,  268,   44,  286,  268,  288,   41,
  260,  291,   44,  288,   31,  289,   99,  269,   59,   46,
   59,  286,  286,   41,  288,  286,   44,  288,  286,   56,
  288,   68,  286,  270,  288,  118,   59,  290,   55,   66,
   44,   59,  287,   59,   41,   72,  265,   44,  267,  266,
  291,  267,   69,  272,  273,  274,  275,  276,  277,  278,
  279,   88,   59,   40,  284,   41,   40,  265,   44,  267,
   41,   44,   89,  292,  272,  273,  274,  275,  276,  277,
  278,  279,  109,   59,   41,   41,   41,  104,   44,   32,
   33,   34,   35,   36,   37,   59,  286,  270,  287,  265,
  117,  267,  287,   59,  287,   41,  272,  273,   44,   59,
  276,  277,  278,  279,   57,   58,   59,   60,   61,   62,
   63,   64,   41,   59,   67,   41,   41,  265,   44,  267,
   41,   41,   66,  117,  272,  273,  274,  275,  276,  277,
  278,  279,  267,   59,    6,  283,   41,  272,  116,   43,
   46,   94,   95,  278,  279,   98,   89,   98,   -1,   -1,
   -1,  265,   -1,  267,   -1,  108,   -1,   40,  272,  273,
  274,  275,  276,  277,  278,  279,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  258,   59,  260,
  261,  262,  263,  264,   -1,   -1,   -1,   -1,  287,  287,
  271,  287,  287,  266,   -1,  265,   -1,  267,   -1,  280,
  281,  282,  272,  273,  274,  275,  276,  277,  278,  279,
   -1,  283,  293,  283,   -1,  287,  265,  287,   -1,   -1,
  292,   -1,  292,  272,  273,  274,  275,  276,  277,  278,
  279,   -1,  283,   -1,  283,   -1,  287,  265,  287,   -1,
   -1,  292,   -1,  292,  272,  273,  274,  275,  276,  277,
  278,  279,   -1,   -1,   -1,  283,   -1,   -1,  265,  287,
   -1,   -1,   -1,   -1,  292,  272,  273,  274,  275,  276,
  277,  278,  279,   -1,   -1,   -1,  283,   -1,   -1,  265,
  287,   -1,   -1,   -1,   -1,  292,  272,  273,  274,  275,
  276,  277,  278,  279,   -1,   -1,   -1,  283,   -1,  265,
   -1,  287,   -1,   -1,   -1,   -1,  292,  273,  274,  275,
  276,  277,   -1,   -1,   -1,   -1,   -1,  283,   -1,  265,
   -1,  287,   -1,   -1,   -1,   -1,  292,  273,  274,  275,
  276,  277,   -1,   -1,   -1,   -1,   -1,  283,   -1,  265,
   -1,  287,   -1,   -1,   -1,   -1,  292,  273,  274,  275,
  276,  277,   -1,   -1,   -1,   -1,   -1,  283,   -1,   -1,
  265,  287,  267,   -1,   -1,   -1,  292,  272,  273,  274,
  275,  276,  277,  278,  279,  258,   -1,  260,  261,  262,
  263,  264,   -1,   -1,  265,   -1,  267,   -1,  271,   -1,
   -1,  272,  273,  274,  275,  276,  277,  278,  279,   -1,
  265,   -1,  267,   -1,   -1,   -1,  287,  272,  273,  274,
  275,  276,  277,  278,  279,  265,   -1,  267,   -1,   -1,
   -1,   -1,  272,  273,  274,  275,  276,  277,  278,  279,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=293;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'",null,null,"','",
null,null,null,null,null,null,null,null,null,null,null,null,null,null,"';'",
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,"FIN","NUM","CLASSTYPE","ID","NIL","TRUE","FALSE",
"NEW","INSTANCEOF","SET","GET","CLASS","EXTENDS","IS","NOT","AND","OR","EQ",
"LT","ADD","SUB","MUL","DIV","RETURN","WRITELN","IF","THEN","ELSE","PROGRAM",
"BEGIN","END","LET","IN","VARS","METHODS","DO","WHILE",
};
final static String yyrule[] = {
"$accept : Prog",
"Prog : PROGRAM Classes Locals InstrList",
"Classes :",
"Classes : NonEmptyClasses",
"NonEmptyClasses : Class",
"NonEmptyClasses : Class NonEmptyClasses",
"Locals :",
"Locals : LET VarDecs IN",
"InstrList : BEGIN InstrSeq END",
"Class : CLASS Id Extends IS VarDecList MethodList",
"Extends :",
"Extends : EXTENDS ClassType",
"VarDecList :",
"VarDecList : VARS VarDecs",
"MethodList :",
"MethodList : METHODS Methods",
"ClassType : CLASSTYPE",
"ClassType : ID",
"VarDecs : VarDec",
"VarDecs : VarDecs VarDec",
"VarDec : ClassType Ids ';'",
"Ids : Id",
"Ids : Id ',' Ids",
"Id : ID",
"Methods : Method",
"Methods : Methods Method",
"Method : ClassType Id '(' ArgDecList ')' Locals InstrList",
"ArgDecList :",
"ArgDecList : ArgDecs",
"ArgDecs : ArgDecs ';' ArgDec",
"ArgDecs : ArgDec",
"ArgDec : ClassType Id",
"InstrSeq :",
"InstrSeq : InstrSeqNonEmpty",
"InstrSeqNonEmpty : Instr ';' InstrSeqNonEmpty",
"InstrSeqNonEmpty : Instr ';'",
"InstrSeqNonEmpty : Instr",
"Instr : Expression GET Id '(' ArgList ')'",
"Instr : Id SET Expression",
"Instr : Expression GET Id SET Expression",
"Instr : RETURN Expression",
"Instr : WRITELN Expression",
"Instr : IF Expression THEN InstrList ELSE InstrList",
"Instr : WHILE Expression DO InstrList",
"Expression : NIL",
"Expression : TRUE",
"Expression : FALSE",
"Expression : NUM",
"Expression : Id",
"Expression : Expression GET Id",
"Expression : Expression GET Id '(' ArgList ')'",
"Expression : NOT Expression",
"Expression : Expression AND Expression",
"Expression : Expression OR Expression",
"Expression : Expression ADD Expression",
"Expression : Expression SUB Expression",
"Expression : Expression MUL Expression",
"Expression : Expression DIV Expression",
"Expression : Expression EQ Expression",
"Expression : Expression LT Expression",
"Expression : NEW ClassType",
"Expression : Expression INSTANCEOF ClassType",
"Expression : '(' Expression ')'",
"ArgList :",
"ArgList : Args",
"Args : Expression",
"Args : Args ',' Expression",
};

//#line 222 "lexer/bopl.y"

private Yylex lexer;
public static IAST ast;

private int yylex(){
	int yyl_return = -1;
	try{
		yylval = new ParserVal(0);
		yyl_return = lexer.yylex();
	}
	catch(IOException e){
		System.err.println("IO Error : " + e);
	}
	return yyl_return;
}

public void yyerror(String error){
	System.err.println("Error : " + error);
}

public Parser (Reader r){
	lexer = new Yylex(r, this);
}



public void parse(String fileName){
	Parser yyparser = null;
	try{
	yyparser = new Parser(new FileReader(fileName));
	}
	catch(FileNotFoundException e){
		System.err.println("Not found");
		return;
	}
	yyparser.yyparse();

	System.out.println("Parsing complete, have a nice day!");
}

public IAST getAST(){ return this.ast; }
	



			
//#line 453 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 74 "lexer/bopl.y"
{ this.ast=new ASTProgram((ArrayList<ASTClass>)val_peek(2).obj,(ArrayList<ASTDeclaration>)val_peek(1).obj,(ASTSequence)val_peek(0).obj); }
break;
case 2:
//#line 77 "lexer/bopl.y"
{yyval.obj = new ArrayList<ASTClass>();}
break;
case 3:
//#line 78 "lexer/bopl.y"
{ yyval.obj = val_peek(0).obj;}
break;
case 4:
//#line 81 "lexer/bopl.y"
{ArrayList<ASTClass> classes=new ArrayList<ASTClass>();
									classes.add((ASTClass)val_peek(0).obj);
									yyval.obj=classes;}
break;
case 5:
//#line 84 "lexer/bopl.y"
{ArrayList<ASTClass> classes=(ArrayList<ASTClass>)val_peek(0).obj;
       								classes.add(0,(ASTClass)val_peek(1).obj);
       								yyval.obj=classes;}
break;
case 6:
//#line 89 "lexer/bopl.y"
{ yyval.obj = new ArrayList<ASTDeclaration>(); }
break;
case 7:
//#line 90 "lexer/bopl.y"
{ yyval.obj = val_peek(1).obj; }
break;
case 8:
//#line 93 "lexer/bopl.y"
{ yyval.obj = val_peek(1).obj; }
break;
case 9:
//#line 96 "lexer/bopl.y"
{ yyval.obj =  new ASTClass((ASTid)val_peek(4).obj,(ASTClassType)val_peek(3).obj,(ArrayList<ASTDeclaration>)val_peek(1).obj,(ArrayList<ASTMethod>)val_peek(0).obj); }
break;
case 10:
//#line 99 "lexer/bopl.y"
{ yyval.obj = new ASTClassType("obj"); }
break;
case 11:
//#line 100 "lexer/bopl.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 12:
//#line 103 "lexer/bopl.y"
{ yyval.obj = new ArrayList<ASTDeclaration>(); }
break;
case 13:
//#line 104 "lexer/bopl.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 14:
//#line 108 "lexer/bopl.y"
{ yyval.obj = new ArrayList<ASTMethod>(); }
break;
case 15:
//#line 109 "lexer/bopl.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 16:
//#line 112 "lexer/bopl.y"
{ yyval.obj = new ASTClassType(val_peek(0).sval); }
break;
case 17:
//#line 113 "lexer/bopl.y"
{ yyval.obj = new ASTClassType(val_peek(0).sval); }
break;
case 18:
//#line 116 "lexer/bopl.y"
{ ArrayList<ASTDeclaration> declarations = new ArrayList<ASTDeclaration>();
						declarations.add((ASTDeclaration)val_peek(0).obj);
						yyval.obj = declarations; }
break;
case 19:
//#line 119 "lexer/bopl.y"
{ ArrayList<ASTDeclaration> declarations = (ArrayList<ASTDeclaration>) val_peek(1).obj;
						declarations.add(0,(ASTDeclaration)val_peek(0).obj);
						yyval.obj = declarations; }
break;
case 20:
//#line 124 "lexer/bopl.y"
{ yyval.obj = new ASTDeclaration((ASTClassType)val_peek(2).obj,(ArrayList<ASTid>)val_peek(1).obj); }
break;
case 21:
//#line 127 "lexer/bopl.y"
{ ArrayList<ASTid> ids =  new ArrayList<ASTid>();
					ids.add((ASTid)val_peek(0).obj);
					yyval.obj = ids; }
break;
case 22:
//#line 130 "lexer/bopl.y"
 	{ ArrayList<ASTid> ids = (ArrayList<ASTid>) val_peek(0).obj;
					ids.add(0,(ASTid)val_peek(2).obj);
					yyval.obj = ids; }
break;
case 23:
//#line 135 "lexer/bopl.y"
{ yyval.obj = new ASTid(val_peek(0).sval); }
break;
case 24:
//#line 138 "lexer/bopl.y"
{ ArrayList<ASTMethod> methods= new ArrayList<ASTMethod>();
						methods.add((ASTMethod)val_peek(0).obj);
						yyval.obj = methods; }
break;
case 25:
//#line 141 "lexer/bopl.y"
{ ArrayList<ASTMethod> methods=(ArrayList<ASTMethod> ) val_peek(1).obj;
						methods.add(0,(ASTMethod)val_peek(0).obj);
						yyval.obj = methods; }
break;
case 26:
//#line 146 "lexer/bopl.y"
{ yyval.obj = new ASTMethod((ASTClassType)val_peek(6).obj,(ASTid)val_peek(5).obj,(ArrayList<ASTDeclaration>)val_peek(3).obj,(ArrayList<ASTDeclaration>)val_peek(1).obj,(ASTSequence)val_peek(0).obj); }
break;
case 27:
//#line 149 "lexer/bopl.y"
{ yyval.obj = new ArrayList<ASTDeclaration>(); }
break;
case 28:
//#line 150 "lexer/bopl.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 29:
//#line 153 "lexer/bopl.y"
{ ArrayList<ASTDeclaration> declarations = (ArrayList<ASTDeclaration>)val_peek(2).obj;
								declarations.add((ASTDeclaration)val_peek(0).obj);
								yyval.obj = declarations; }
break;
case 30:
//#line 156 "lexer/bopl.y"
{ ArrayList<ASTDeclaration> declarations = new ArrayList<ASTDeclaration>();
								declarations.add((ASTDeclaration)val_peek(0).obj);
								yyval.obj = declarations; }
break;
case 31:
//#line 161 "lexer/bopl.y"
{ yyval.obj = new ASTDeclaration((ASTClassType)val_peek(1).obj,(ASTid)val_peek(0).obj); }
break;
case 32:
//#line 164 "lexer/bopl.y"
{ yyval.obj = new ASTSequence(new ArrayList<IASTInstruction>()); }
break;
case 33:
//#line 165 "lexer/bopl.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 34:
//#line 169 "lexer/bopl.y"
{((ASTSequence) val_peek(0).obj).push((IASTInstruction)val_peek(2).obj);
									yyval.obj = val_peek(0).obj; }
break;
case 35:
//#line 171 "lexer/bopl.y"
{ ASTSequence seq = new ASTSequence(new ArrayList<IASTInstruction>());
									seq.push((IASTInstruction)val_peek(1).obj);
									yyval.obj = seq; }
break;
case 36:
//#line 174 "lexer/bopl.y"
{ ASTSequence seq = new ASTSequence(new ArrayList<IASTInstruction>());
                                    seq.push((IASTInstruction)val_peek(0).obj);
                                    yyval.obj = seq; }
break;
case 37:
//#line 180 "lexer/bopl.y"
{ yyval.obj = new ASTCall((IASTExpression)val_peek(5).obj,(ASTid)val_peek(3).obj,(ArrayList<IASTExpression>)val_peek(1).obj); }
break;
case 38:
//#line 181 "lexer/bopl.y"
{ yyval.obj = new ASTSetVar((ASTid)val_peek(2).obj,(IASTExpression)val_peek(0).obj); }
break;
case 39:
//#line 182 "lexer/bopl.y"
{ yyval.obj = new ASTSetField((IASTExpression)val_peek(4).obj,(ASTid)val_peek(2).obj,(IASTExpression)val_peek(0).obj); }
break;
case 40:
//#line 183 "lexer/bopl.y"
{ yyval.obj = new ASTReturn((IASTExpression)val_peek(0).obj);}
break;
case 41:
//#line 184 "lexer/bopl.y"
{ yyval.obj = new ASTWrite((IASTExpression)val_peek(0).obj); }
break;
case 42:
//#line 185 "lexer/bopl.y"
{ yyval.obj = new ASTAlternative((IASTExpression)val_peek(4).obj,(ASTSequence)val_peek(2).obj,(ASTSequence)val_peek(0).obj); }
break;
case 43:
//#line 186 "lexer/bopl.y"
{ yyval.obj = new ASTWhile((IASTExpression)val_peek(2).obj,(ASTSequence)val_peek(0).obj); }
break;
case 44:
//#line 190 "lexer/bopl.y"
{ yyval.obj = ASTExpression.NULL; }
break;
case 45:
//#line 191 "lexer/bopl.y"
{ yyval.obj = ASTExpression.TRUE; }
break;
case 46:
//#line 192 "lexer/bopl.y"
{ yyval.obj = ASTExpression.FALSE; }
break;
case 47:
//#line 193 "lexer/bopl.y"
{ yyval.obj = new ASTNum(val_peek(0).dval); }
break;
case 48:
//#line 194 "lexer/bopl.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 49:
//#line 195 "lexer/bopl.y"
{ yyval.obj = new ASTGet((IASTExpression)val_peek(2).obj,(ASTid)val_peek(0).obj); }
break;
case 50:
//#line 196 "lexer/bopl.y"
{ yyval.obj = new ASTCall((IASTExpression)val_peek(5).obj,(ASTid)val_peek(3).obj,(ArrayList<IASTExpression>)val_peek(1).obj); }
break;
case 51:
//#line 197 "lexer/bopl.y"
{ yyval.obj = new NotOperator((IASTExpression)val_peek(0).obj); }
break;
case 52:
//#line 198 "lexer/bopl.y"
{ yyval.obj = new AndOperator((IASTExpression)val_peek(2).obj,(IASTExpression)val_peek(0).obj); }
break;
case 53:
//#line 199 "lexer/bopl.y"
{ yyval.obj = new OrOperator((IASTExpression)val_peek(2).obj,(IASTExpression)val_peek(0).obj); }
break;
case 54:
//#line 200 "lexer/bopl.y"
{ yyval.obj = new AddOperator((IASTExpression)val_peek(2).obj,(IASTExpression)val_peek(0).obj); }
break;
case 55:
//#line 201 "lexer/bopl.y"
{ yyval.obj = new SubOperator((IASTExpression)val_peek(2).obj,(IASTExpression)val_peek(0).obj); }
break;
case 56:
//#line 202 "lexer/bopl.y"
{ yyval.obj = new MultOperator((IASTExpression)val_peek(2).obj,(IASTExpression)val_peek(0).obj); }
break;
case 57:
//#line 203 "lexer/bopl.y"
{ yyval.obj = new DivOperator((IASTExpression)val_peek(2).obj,(IASTExpression)val_peek(0).obj); }
break;
case 58:
//#line 204 "lexer/bopl.y"
{ yyval.obj = new EqualsOperator((IASTExpression)val_peek(2).obj,(IASTExpression)val_peek(0).obj); }
break;
case 59:
//#line 205 "lexer/bopl.y"
{ yyval.obj = new LessThanOperator((IASTExpression)val_peek(2).obj,(IASTExpression)val_peek(0).obj); }
break;
case 60:
//#line 206 "lexer/bopl.y"
{ yyval.obj = new ASTNew((ASTClassType)val_peek(0).obj); }
break;
case 61:
//#line 207 "lexer/bopl.y"
{ yyval.obj = new ASTInstanceOf((IASTExpression)val_peek(2).obj,(ASTClassType)val_peek(0).obj); }
break;
case 62:
//#line 208 "lexer/bopl.y"
{ yyval.obj = val_peek(1).obj; }
break;
case 63:
//#line 211 "lexer/bopl.y"
{ yyval.obj = new ArrayList<IASTExpression>(); }
break;
case 64:
//#line 212 "lexer/bopl.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 65:
//#line 215 "lexer/bopl.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 66:
//#line 216 "lexer/bopl.y"
{ ArrayList<IASTExpression> expressions=(ArrayList<IASTExpression>)val_peek(2).obj;
     						expressions.add((IASTExpression)val_peek(0).obj);
     						yyval.obj = expressions; }
break;
//#line 893 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
