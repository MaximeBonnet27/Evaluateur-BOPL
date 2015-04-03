package com.aps.process;

import java.io.File;

import com.aps.environnement.Environment;
import com.aps.parser.Parser;

public class Process extends AbstractProcess {
	public Process(Parser parser,File file) {
		super(parser,file);
	}

	@Override
	public Object interpret() {
		System.out.println(file.toString());
		parser.parse(file.toString());
		Object resultat=null;
		
		try {
			resultat=parser.getAST().eval(Environment.EMPTYENV);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultat;
	}
	
	
}
