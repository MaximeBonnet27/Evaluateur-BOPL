/**
 * 
 */
package com.aps.ast.expressions;

import com.aps.environnement.IEnvironment;


/**
 * @author 3100381
 *
 */
public class ASTid extends ASTExpression{

	private String name;

	public ASTid(String string) {
		super();
		this.name = string;
	}

	@Override
	public Object eval(IEnvironment env) throws Exception{
		return env.getValue(name);
	}
	
	public String toString(){
		return name;
	}
	
}
