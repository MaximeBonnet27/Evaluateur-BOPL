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
public class NotOperator extends AbstractUnaryOperator{

	/**
	 * @param operand
	 */
	public NotOperator(IAST operand) {
		super(operand);
	}

	@Override
	public Object eval(IEnvironment env) throws Exception {
		return ((Integer) operand.eval(env) + 1) % 2;
	}

}
