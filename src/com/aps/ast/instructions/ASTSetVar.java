/**
 * 
 */
package com.aps.ast.instructions;

import com.aps.ast.AST;
import com.aps.ast.expressions.ASTExpression;
import com.aps.ast.expressions.ASTid;
import com.aps.environnement.IEnvironment;

/**
 * @author 3100381
 *
 */
public class ASTSetVar extends ASTInstruction {

	private ASTid id;
	private ASTExpression exp;

	public ASTSetVar(ASTid id, ASTExpression exp) {
		super();
		this.id = id;
		this.exp = exp;
	}

	@Override
	public Object eval(IEnvironment env) throws Exception {
		env.update(id.toString(), exp.eval(env));
		return AST.CONSTANT_NULL;
	}

}
