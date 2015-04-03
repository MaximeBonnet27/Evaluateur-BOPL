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
public class LessThanOperator extends AbstractBinaryOperator{

	/**
	 * @param leftOperand
	 * @param rightOperand
	 */
	public LessThanOperator(IAST leftOperand, IAST rightOperand) {
		super(leftOperand, rightOperand);
	}

	@Override
	public Object eval(IEnvironment env) throws Exception {
		return ltz((Integer) leftOperand.eval(env) - (Integer) rightOperand.eval(env));
	}

	private Integer ltz(Integer x){
		return (x < 0) ? AST.CONSTANT_TRUE : AST.CONSTANT_FALSE;
	}
	
}
