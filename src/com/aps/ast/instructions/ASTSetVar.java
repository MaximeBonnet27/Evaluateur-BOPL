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
public class ASTSetVar extends ASTInstruction {

	private ASTid id;
	private IASTExpression exp;

	public ASTSetVar(ASTid id, IASTExpression exp) {
		super();
		this.id = id;
		this.exp = exp;
	}

	@Override
	public Object eval(IEnvironment env) throws Exception {
		Object val=exp.eval(env);
		try{
			env.update(id.toString(), val);
		}catch(Exception e){
			ASTid self=new ASTid("self");
			ASTInstance instance=(ASTInstance) self.eval(env);
			instance.updateAttribute(id.toString(), val);
		}
		
		return AST.CONSTANT_NULL;
	}

}
