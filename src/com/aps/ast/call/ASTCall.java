/**
 * 
 */
package com.aps.ast.call;

import java.util.ArrayList;

import com.aps.ast.AST;
import com.aps.ast.declarations.ASTMethod;
import com.aps.ast.expressions.ASTExpression;
import com.aps.ast.expressions.ASTid;
import com.aps.ast.expressions.IASTExpression;
import com.aps.ast.instructions.IASTInstruction;
import com.aps.environnement.IEnvironment;


/**
 * @author 3100381
 *
 */
public class ASTCall extends AST implements IASTInstruction, IASTExpression{
	private ASTExpression instance;
	private ASTid methode;
	private ArrayList<ASTExpression> args;
	
	public ASTCall(ASTExpression instance, ASTid methode, ArrayList<ASTExpression> args) {
		super();
		this.instance = instance;
		this.methode = methode;
		this.args = args;
	}

	@Override
	public Object eval(IEnvironment env) throws Exception{
		ASTInstance instance= (ASTInstance) this.instance.eval(env);
		return ((ASTMethod)((ASTMethod)(methode.eval(instance.getDictionnaire()))).clone()).call(env,instance, args);
	}

	@Override
	public Object clone() {
		return new ASTCall((ASTExpression)instance.clone(), (ASTid)methode.clone(), (ArrayList<ASTExpression>)args.clone());
	}
	
	
	
}
