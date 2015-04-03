package com.aps.process;

import java.io.File;

import com.aps.ast.AST;
import com.aps.environnement.Environment;
import com.aps.environnement.IEnvironment;
import com.aps.parser.Parser;

public abstract class AbstractProcess {
	IEnvironment env;
	Parser parser;
	File file;
	
	public AbstractProcess(Parser parser,File file) {
		initEnvironment();
		this.parser=parser;
		this.file=file;
	}
	
	private void initEnvironment() {
		env = Environment.EMPTYENV;
		env = new Environment(env, "true", AST.CONSTANT_TRUE);
		env = new Environment(env, "false", AST.CONSTANT_FALSE);
		env = new Environment(env, "null", AST.CONSTANT_NULL);
	}
	
	public abstract Object interpret();


}
