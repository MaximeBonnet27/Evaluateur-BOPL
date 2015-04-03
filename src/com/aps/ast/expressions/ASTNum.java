/**
 * 
 */
package com.aps.ast.expressions;

import com.aps.environnement.IEnvironment;

/**
 * @author 3100381
 *
 */
public class ASTNum extends ASTExpression{

	private Integer val;
	
	public ASTNum(double val) {
		super();
		this.val = (int) val;
	}

	@Override
	public Object eval(IEnvironment env) throws Exception{
		return val;
	}

	
	
}
