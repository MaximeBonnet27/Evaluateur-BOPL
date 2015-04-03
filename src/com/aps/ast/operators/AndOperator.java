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
public class AndOperator extends AbstractBinaryOperator {

	/**
	 * @param leftOperand
	 * @param rightOperand
	 */
	public AndOperator(IAST leftOperand, IAST rightOperand) {
		super(leftOperand, rightOperand);
	}

	@Override
	public Object eval(IEnvironment env) throws Exception {
		return (Integer) leftOperand.eval(env) * (Integer) rightOperand.eval(env);
	}

}
