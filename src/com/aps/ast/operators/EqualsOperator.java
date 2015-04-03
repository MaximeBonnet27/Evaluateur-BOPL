/**
 * 
 */
package com.aps.ast.operators;

import com.aps.ast.AST;
import com.aps.ast.IAST;
import com.aps.environnement.IEnvironment;

/**
 * @author 3100381
 *
 */
public class EqualsOperator extends AbstractBinaryOperator{

	/**
	 * @param leftOperand
	 * @param rightOperand
	 */
	public EqualsOperator(IAST leftOperand, IAST rightOperand) {
		super(leftOperand, rightOperand);
	}

	@Override
	public Object eval(IEnvironment env) throws Exception {
		return eqz((Integer) leftOperand.eval(env) - (Integer) rightOperand.eval(env));
	}

	private Integer eqz(Integer x){
		return (x == 0) ? AST.CONSTANT_TRUE : AST.CONSTANT_FALSE;
	}
}
