/**
 * 
 */
package com.aps.ast.instructions;

import com.aps.ast.AST;
import com.aps.ast.call.ASTInstance;
import com.aps.ast.expressions.ASTid;
import com.aps.ast.expressions.IASTExpression;
import com.aps.environnement.IEnvironment;

/**
 * @author 3100381
 *
 */
public class ASTSetField extends ASTInstruction{

	private IASTExpression obj;
	private ASTid field;
	private IASTExpression exp;

	
	public ASTSetField(IASTExpression obj, ASTid field, IASTExpression exp) {
		super();
		this.obj = obj;
		this.field = field;
		this.exp = exp;
	}


	@Override
	public Object eval(IEnvironment env)throws Exception{
		ASTInstance instance=(ASTInstance) obj.eval(env);
		instance.updateAttribute(field.toString(), exp.eval(env));
		return AST.CONSTANT_NULL;
	}
	
}
