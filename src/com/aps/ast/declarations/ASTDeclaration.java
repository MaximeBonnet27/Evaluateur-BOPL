/**
 * 
 */
package com.aps.ast.declarations;

import java.util.ArrayList;

import com.aps.ast.AST;
import com.aps.ast.expressions.ASTid;
import com.aps.environnement.IEnvironment;

/**
 * @author 3100381
 *
 */
public class ASTDeclaration extends AST{

	private ASTClassType classe;
	private ArrayList<ASTid> variables;
	

	public ASTDeclaration(ASTClassType classe, ASTid variable) {
		super();
		this.classe = classe;
		this.variables = new ArrayList<ASTid>();
		this.variables.add(variable);
	}
	
	public ASTDeclaration(ASTClassType classe, ArrayList<ASTid> variables) {
		super();
		this.classe = classe;
		this.variables = variables;
	}

	@Override
	public Object eval(IEnvironment env) throws Exception{
		
		for(ASTid v : variables){
			env=env.extend(v.toString(), AST.CONSTANT_NULL);
		}
		return env;
	}
	
	public ASTid getId(int index){
		return variables.get(index);
	}

	@Override
	public Object clone() {
		return new ASTDeclaration((ASTClassType)classe.clone(), (ASTid)variables.clone());
	}
	
	
}
