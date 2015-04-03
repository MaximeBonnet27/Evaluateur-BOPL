package com.aps.ast.operators;

import com.aps.ast.IAST;
import com.aps.environnement.IEnvironment;

public class AddOperator extends AbstractBinaryOperator{

	public AddOperator(IAST leftOperand, IAST rightOperand) {
		super(leftOperand, rightOperand);
	}

	@Override
	public Object eval(IEnvironment env) throws Exception {
		return (Integer) leftOperand.eval(env) + (Integer) rightOperand.eval(env);
	}

}
