package com.aps.ast.operators;

import com.aps.ast.IAST;
import com.aps.ast.expressions.ASTExpression;

public abstract class AbstractBinaryOperator extends ASTExpression{

	protected IAST leftOperand, rightOperand;

	public AbstractBinaryOperator(IAST leftOperand, IAST rightOperand) {
		super();
		this.leftOperand = leftOperand;
		this.rightOperand = rightOperand;
	}
		
}
