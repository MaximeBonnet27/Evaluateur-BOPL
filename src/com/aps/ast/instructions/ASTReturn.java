/**
 * 
 */
package com.aps.ast.instructions;

import com.aps.ast.AST;
import com.aps.ast.IAST;
import com.aps.ast.expressions.ASTExpression;
import com.aps.ast.expressions.IASTExpression;
import com.aps.ast.operators.AbstractUnaryOperator;
import com.aps.environnement.IEnvironment;

/**
 * @author 3100381
 *
 */
public class ASTReturn extends ASTInstruction{

	private IASTExpression exp;
		
	public ASTReturn(IASTExpression obj) {
		exp = obj;
	}

	@Override
	public Object eval(IEnvironment env) throws Exception{
		return exp.eval(env);
	}
	
	

}
