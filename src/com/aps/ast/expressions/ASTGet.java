/**
 * 
 */
package com.aps.ast.expressions;

import com.aps.ast.IAST;
import com.aps.ast.call.ASTInstance;
import com.aps.ast.operators.AbstractBinaryOperator;
import com.aps.environnement.IEnvironment;

/**
 * @author 3100381
 *
 */
public class ASTGet extends AbstractBinaryOperator{

	/**
	 * @param leftOperand
	 * @param rightOperand
	 */
	public ASTGet(IAST leftOperand, IAST rightOperand) {
		super(leftOperand, rightOperand);
	}

	@Override
	public Object eval(IEnvironment env) throws Exception{
		ASTInstance instance=(ASTInstance) leftOperand.eval(env);
		return instance.getDictionnaire().getValue(rightOperand.toString());
	}

}
