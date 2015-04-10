/**
 * 
 */
package com.aps.ast.instructions;

import com.aps.ast.AST;
import com.aps.ast.expressions.IASTExpression;
import com.aps.environnement.IEnvironment;

/**
 * @author 3100381
 *
 */
public class ASTWhile extends ASTInstruction {

	private IASTExpression condition;
	private ASTSequence seq;
	
	public ASTWhile(IASTExpression condition, ASTSequence seq) {
		super();
		this.condition = condition;
		this.seq = seq;
	}

	@Override
	public Object eval(IEnvironment env) throws Exception {
		Object val = AST.CONSTANT_NULL;
		while(AST.isTrue((Integer) condition.eval(env)) && val == AST.CONSTANT_NULL){
				val = seq.eval(env);
		}
		return val;
	}

}
