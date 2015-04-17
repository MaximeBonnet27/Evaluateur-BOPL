
/* Dclarations */

%{

import java.io.*;
import java.util.*;
import com.aps.ast.*;
import com.aps.ast.call.*;
import com.aps.ast.declarations.*;
import com.aps.ast.expressions.*;
import com.aps.ast.instructions.*;
import com.aps.ast.operators.*;

%}

/* Symboles terminaux */

%token FIN

%token <dval> NUM
%token <sval> CLASSTYPE 
%token <sval> ID
%token NIL TRUE FALSE
%token NEW INSTANCEOF SET GET CLASS EXTENDS IS
%token NOT AND OR
%token EQ LT
%token ADD SUB MUL DIV
%token RETURN 
%token WRITELN
%token IF THEN ELSE
%token PROGRAM 
%token BEGIN END
%token LET IN 
%token VARS METHODS
%token DO WHILE


%type <obj> Expression
%type <obj> ClassType 
%type <obj> InstrList
%type <obj> InstrSeq
%type <obj> InstrSeqNonEmpty
%type <obj> Instr
%type <obj> ArgDecList
%type <obj> ArgDecs
%type <obj> ArgDec
%type <obj> Classes 
%type <obj> NonEmptyClasses
%type <obj> Locals
%type <obj> Class
%type <obj> Extends
%type <obj> VarDecList
%type <obj> MethodList
%type <obj> VarDecs
%type <obj> VarDec
%type <obj> Ids
%type <obj> Id
%type <obj> Methods
%type <obj> Method
%type <obj> ArgList
%type <obj> Args

%nonassoc EQ LT
%nonassoc INSTANCEOF
%left ADD SUB OR
%left MUL DIV AND
%right NOT

%start Prog
%% 
/* Productions */

Prog: PROGRAM Classes Locals InstrList { this.ast=new ASTProgram((ArrayList<ASTClass>)$2,(ArrayList<ASTDeclaration>)$3,(ASTSequence)$4); } 
;

Classes: {$$ = new ArrayList<ASTClass>();} 
       | NonEmptyClasses { $$ = $1;}
;

NonEmptyClasses : Class 			{ArrayList<ASTClass> classes=new ArrayList<ASTClass>();
									classes.add((ASTClass)$1);
									$$=classes;}
       | Class NonEmptyClasses 		{ArrayList<ASTClass> classes=(ArrayList<ASTClass>)$2;
       								classes.add(0,(ASTClass)$1);
       								$$=classes;}
;

Locals: 				{ $$ = new ArrayList<ASTDeclaration>(); }
      | LET VarDecs IN 	{ $$ = $2; }
;

InstrList: BEGIN InstrSeq END { $$ = $2; }
;

Class : CLASS Id Extends IS VarDecList MethodList { $$ =  new ASTClass((ASTid)$2,(ASTClassType)$3,(ArrayList<ASTDeclaration>)$5,(ArrayList<ASTMethod>)$6); }
;

Extends :  				{ $$ = new ASTClassType("obj"); }
	| EXTENDS ClassType { $$ = $2; }
;

VarDecList : 			{ $$ = new ArrayList<ASTDeclaration>(); }
	   | VARS VarDecs 	{ $$ = $2; }
;


MethodList : 				{ $$ = new ArrayList<ASTMethod>(); }
	   | METHODS Methods 	{ $$ = $2; }
	   ;

ClassType : CLASSTYPE 	{ $$ = new ASTClassType($1); }
	  | ID 				{ $$ = new ASTClassType($1); }
;

VarDecs : VarDec 		{ ArrayList<ASTDeclaration> declarations = new ArrayList<ASTDeclaration>();
						declarations.add((ASTDeclaration)$1);
						$$ = declarations; }
	| VarDecs VarDec 	{ ArrayList<ASTDeclaration> declarations = (ArrayList<ASTDeclaration>) $1;
						declarations.add(0,(ASTDeclaration)$2);
						$$ = declarations; }
;

VarDec : ClassType Ids ";" 	{ $$ = new ASTDeclaration((ASTClassType)$1,(ArrayList<ASTid>)$2); }
;

Ids : Id 			{ ArrayList<ASTid> ids =  new ArrayList<ASTid>();
					ids.add((ASTid)$1);
					$$ = ids; } 
    | Id "," Ids = 	{ ArrayList<ASTid> ids = (ArrayList<ASTid>) $3;
					ids.add(0,(ASTid)$1);
					$$ = ids; }
;

Id : ID { $$ = new ASTid($1); }	
;

Methods : Method 		{ ArrayList<ASTMethod> methods= new ArrayList<ASTMethod>();
						methods.add((ASTMethod)$1);
						$$ = methods; }
	| Methods Method 	{ ArrayList<ASTMethod> methods=(ArrayList<ASTMethod> ) $1;
						methods.add(0,(ASTMethod)$2);
						$$ = methods; }
;

Method : ClassType Id "(" ArgDecList ")" Locals InstrList 	{ $$ = new ASTMethod((ASTClassType)$1,(ASTid)$2,(ArrayList<ASTDeclaration>)$4,(ArrayList<ASTDeclaration>)$6,(ASTSequence)$7); }
;

ArgDecList : 	{ $$ = new ArrayList<ASTDeclaration>(); }
	| ArgDecs 	{ $$ = $1; }	   
;

ArgDecs: ArgDecs ";" ArgDec 	{ ArrayList<ASTDeclaration> declarations = (ArrayList<ASTDeclaration>)$1;
								declarations.add((ASTDeclaration)$3);
								$$ = declarations; }
	| ArgDec 					{ ArrayList<ASTDeclaration> declarations = new ArrayList<ASTDeclaration>();
								declarations.add((ASTDeclaration)$1);
								$$ = declarations; } 
;

ArgDec: ClassType Id { $$ = new ASTDeclaration((ASTClassType)$1,(ASTid)$2); }
;

InstrSeq : 					{ $$ = new ASTSequence(new ArrayList<IASTInstruction>()); }
	 | InstrSeqNonEmpty 	{ $$ = $1; }
;

InstrSeqNonEmpty: 	
	 Instr ";" InstrSeqNonEmpty     {((ASTSequence) $3).push((IASTInstruction)$1);
									$$ = $3; }
	| Instr ";" 					{ ASTSequence seq = new ASTSequence(new ArrayList<IASTInstruction>());
									seq.push((IASTInstruction)$1);
									$$ = seq; }
	| Instr 						{ ASTSequence seq = new ASTSequence(new ArrayList<IASTInstruction>());
                                    seq.push((IASTInstruction)$1);
                                    $$ = seq; }
;

Instr: 
	 Expression GET Id '(' ArgList ')'				{ $$ = new ASTCall((IASTExpression)$1,(ASTid)$3,(ArrayList<IASTExpression>)$5); }
	| Expression GET Id SET Expression             { $$ = new ASTSetField((IASTExpression)$1,(ASTid)$3,(IASTExpression)$5); }
	| Id SET Expression 							{ $$ = new ASTSetVar((ASTid)$1,(IASTExpression)$3); }
	| RETURN Expression 							{ $$ = new ASTReturn((IASTExpression)$2);}
	| WRITELN Expression 							{ $$ = new ASTWrite((IASTExpression)$2); }
	| IF Expression THEN InstrList ELSE InstrList  	{ $$ = new ASTAlternative((IASTExpression)$2,(ASTSequence)$4,(ASTSequence)$6); }
	| WHILE Expression DO InstrList 				{ $$ = new ASTWhile((IASTExpression)$2,(ASTSequence)$4); }
;

Expression : 
	 NIL 								{ $$ = ASTExpression.NULL; }
    | TRUE 								{ $$ = ASTExpression.TRUE; }
 	| FALSE 							{ $$ = ASTExpression.FALSE; }
	| NUM 								{ $$ = new ASTNum($1); }
	| Id 								{ $$ = $1; }
	| Expression GET Id 				{ $$ = new ASTGet((IASTExpression)$1,(ASTid)$3); }
	| Expression GET Id '(' ArgList ')' { $$ = new ASTCall((IASTExpression)$1,(ASTid)$3,(ArrayList<IASTExpression>)$5); }
	| NOT Expression 					{ $$ = new NotOperator((IASTExpression)$2); }
	| Expression AND Expression 		{ $$ = new AndOperator((IASTExpression)$1,(IASTExpression)$3); }
    | Expression OR Expression 			{ $$ = new OrOperator((IASTExpression)$1,(IASTExpression)$3); }
	| Expression ADD Expression 		{ $$ = new AddOperator((IASTExpression)$1,(IASTExpression)$3); }
	| Expression SUB Expression 		{ $$ = new SubOperator((IASTExpression)$1,(IASTExpression)$3); }
	| Expression MUL Expression 		{ $$ = new MultOperator((IASTExpression)$1,(IASTExpression)$3); }
	| Expression DIV Expression 		{ $$ = new DivOperator((IASTExpression)$1,(IASTExpression)$3); }
    | Expression EQ Expression 			{ $$ = new EqualsOperator((IASTExpression)$1,(IASTExpression)$3); }
	| Expression LT Expression 			{ $$ = new LessThanOperator((IASTExpression)$1,(IASTExpression)$3); }
	| NEW ClassType 					{ $$ = new ASTNew((ASTClassType)$2); }
    | Expression INSTANCEOF ClassType 	{ $$ = new ASTInstanceOf((IASTExpression)$1,(ASTClassType)$3); }
	| '(' Expression ')'  				{ $$ = $2; }
;
	
ArgList : 	{ $$ = new ArrayList<IASTExpression>(); }
	| Args 	{ $$ = $1; }
;

Args : Expression 			{ ArrayList<IASTExpression> expressions=new ArrayList<IASTExpression>();
                            expressions.add((IASTExpression)$1);
                            $$ = expressions; }
     | Args "," Expression 	{ ArrayList<IASTExpression> expressions=(ArrayList<IASTExpression>)$1;
     						expressions.add((IASTExpression)$3);
     						$$ = expressions; }
;

%%

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
	



			
