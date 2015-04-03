/**
 * 
 */
package com.aps.ast.expressions;

import com.aps.ast.AST;
import com.aps.ast.IAST;
import com.aps.ast.call.ASTInstance;
import com.aps.ast.declarations.ASTClassType;
import com.aps.ast.operators.AbstractBinaryOperator;
import com.aps.environnement.IEnvironment;

/**
 * @author 3100381
 *
 */
public class ASTInstanceOf extends AbstractBinaryOperator{

	/**
	 * @param leftOperand
	 * @param rightOperand
	 */
	public ASTInstanceOf(IAST leftOperand, IAST rightOperand) {
		super(leftOperand, rightOperand);
	}

	@Override
	public Object eval(IEnvironment env) throws Exception{
		ASTInstance instance= (ASTInstance) leftOperand.eval(env);
		return instance.getClasseName().equals(rightOperand.toString()) ? AST.CONSTANT_TRUE : AST.CONSTANT_FALSE;
	}

}
