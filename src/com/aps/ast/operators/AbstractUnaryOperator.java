/**
 * 
 */
package com.aps.ast.operators;

import com.aps.ast.IAST;
import com.aps.ast.expressions.ASTExpression;

/**
 * @author 3100381
 *
 */
public abstract class AbstractUnaryOperator extends ASTExpression{
	protected IAST operand;

	public AbstractUnaryOperator(IAST operand) {
		super();
		this.operand = operand;
	}
	

}
