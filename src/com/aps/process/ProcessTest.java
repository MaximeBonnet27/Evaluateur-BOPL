package com.aps.process;

import java.io.File;

import com.aps.parser.Parser;

public class ProcessTest {

	public static void main(String[] args) {
		Process p = new Process(new Parser(),new File("examples/prog6g.bopl"));
		Object returnValue = p.interpret();
		System.out.println("Retour du programme : " + returnValue);
	}
	
}
