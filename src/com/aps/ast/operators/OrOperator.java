/**
 * 
 */
package com.aps.ast.operators;

import com.aps.ast.IAST;
import com.aps.environnement.IEnvironment;

/**
 * @author 3100381
 *
 */
public class OrOperator extends AbstractBinaryOperator{

	/**
	 * @param leftOperand
	 * @param rightOperand
	 */
	public OrOperator(IAST leftOperand, IAST rightOperand) {
		super(leftOperand, rightOperand);
	}

	@Override
	public Object eval(IEnvironment env) throws Exception {
		int x = (Integer) leftOperand.eval(env);
		int y = (Integer) rightOperand.eval(env);
		return (x + y + (x*y) % 2);
	}

}
