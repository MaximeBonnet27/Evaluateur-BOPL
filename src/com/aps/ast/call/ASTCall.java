/**
 * 
 */
package com.aps.ast.call;

import java.util.ArrayList;

import com.aps.ast.AST;
import com.aps.ast.declarations.ASTMethod;
import com.aps.ast.expressions.ASTid;
import com.aps.ast.expressions.IASTExpression;
import com.aps.ast.instructions.IASTInstruction;
import com.aps.environnement.IEnvironment;


/**
 * @author 3100381
 *
 */
public class ASTCall extends AST implements IASTInstruction, IASTExpression{
	private IASTExpression instance;
	private ASTid methode;
	private ArrayList<IASTExpression> args;
	
	public ASTCall(IASTExpression instance, ASTid methode, ArrayList<IASTExpression> args) {
		super();
		this.instance = instance;
		this.methode = methode;
		this.args = args;
	}

	@Override
	public Object eval(IEnvironment env) throws Exception{
		ASTInstance instance= (ASTInstance) this.instance.eval(env);
		ASTMethod m=instance.getMethode(methode.toString());
		m = (ASTMethod)m.clone();
		return m.call(env,instance, args);
	}

	@Override
	public IASTExpression clone() {
		return new ASTCall(instance.clone(), (ASTid)methode.clone(), (ArrayList<IASTExpression>)args.clone());
	}
	
	
	
}
