/**
 * 
 */
package com.aps.ast.declarations;

import com.aps.ast.AST;
import com.aps.environnement.IEnvironment;

/**
 * @author 3100381
 *
 */
public class ASTClassType extends AST{

	private String name;
	
	
	public ASTClassType(String name) {
		super();
		this.name = name;
	}
	
	@Override
	public Object eval(IEnvironment env) throws Exception{
		return env.getValue(name);
	}
	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public Object clone() {
		return new ASTClassType(name);
	}
	
	

}
