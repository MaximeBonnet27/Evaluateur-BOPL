/**
 * 
 */
package com.aps.ast;

import java.util.ArrayList;

import com.aps.ast.declarations.ASTClass;
import com.aps.ast.declarations.ASTDeclaration;
import com.aps.ast.instructions.ASTSequence;
import com.aps.environnement.IEnvironment;

/**
 * @author 3100381
 *
 */
public class ASTProgram extends AST{

	private ArrayList<ASTClass> classes;
	private ArrayList<ASTDeclaration> locals;
	private ASTSequence seq;
	
	
	public ASTProgram(ArrayList<ASTClass> classes,ArrayList<ASTDeclaration> locals, ASTSequence seq) {
		super();
		this.classes = classes;
		this.locals = locals;
		this.seq = seq;
	}

	@Override
	public Object eval(IEnvironment env) throws Exception{
		for(ASTClass c : classes){
			env=(IEnvironment) c.eval(env);
		}
		
		for(ASTDeclaration d : locals){
			env=(IEnvironment)d.eval(env);
		}
		
		Object resultat= seq.eval(env);
		
		System.out.println("environnement:"
				+ "[");
		env.print();
		System.out.println("]");
		
		return resultat;
	}

	/* (non-Javadoc)
	 * @see com.aps.ast.AST#clone()
	 */
	@Override
	public Object clone() {
		return new ASTProgram((ArrayList<ASTClass>)classes.clone(),(ArrayList<ASTDeclaration>)locals.clone(),(ASTSequence)seq.clone());
	}

}
