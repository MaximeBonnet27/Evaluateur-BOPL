/**
 * 
 */
package com.aps.ast.instructions;

import com.aps.ast.AST;
import com.aps.ast.expressions.ASTExpression;
import com.aps.ast.expressions.IASTExpression;
import com.aps.environnement.IEnvironment;

/**
 * @author 3100381
 *
 */
public class ASTWrite extends ASTInstruction{

	private IASTExpression exp;
	public ASTWrite(IASTExpression operand) {
		exp = operand;
	}

	@Override
	public Object eval(IEnvironment env) throws Exception {
		System.out.println(" stdout : " + exp.eval(env));
		return null;
	}

}
