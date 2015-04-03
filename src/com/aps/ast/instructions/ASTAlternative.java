/**
 * 
 */
package com.aps.ast.instructions;

import com.aps.ast.AST;
import com.aps.ast.expressions.ASTExpression;
import com.aps.environnement.IEnvironment;

/**
 * @author 3100381
 *
 */
public class ASTAlternative extends ASTInstruction{

	private ASTExpression condition;
	private ASTSequence seq1;
	private ASTSequence seq2;
	
	public ASTAlternative(ASTExpression condition, ASTSequence seq1,
			ASTSequence seq2) {
		super();
		this.condition = condition;
		this.seq1 = seq1;
		this.seq2 = seq2;
	}

	@Override
	public Object eval(IEnvironment env) throws Exception{
		if(AST.isTrue((Integer) condition.eval(env))){
			return seq1.eval(env);
		}
		else{
			return seq2.eval(env);
		}
	}

}
