/**
 * 
 */
package com.aps.ast.declarations;

import java.util.ArrayList;

import com.aps.ast.AST;
import com.aps.ast.IAST;
import com.aps.ast.call.ASTInstance;
import com.aps.ast.expressions.ASTExpression;
import com.aps.ast.expressions.ASTid;
import com.aps.ast.expressions.IASTExpression;
import com.aps.ast.instructions.ASTSequence;
import com.aps.environnement.Environment;
import com.aps.environnement.IEnvironment;

/**
 * @author 3100381
 *
 */
public class ASTMethod extends AST {

	private ASTClassType classe;
	private ASTid id;
	private ArrayList<ASTDeclaration> args;
	private ArrayList<ASTDeclaration> locals;
	private ASTSequence seq;
	
	private IEnvironment envLocal;
	
	public ASTMethod(ASTClassType classe, ASTid id,ArrayList<ASTDeclaration> args, ArrayList<ASTDeclaration> locals,ASTSequence seq) {
		super();
		this.classe = classe;
		this.id = id;
		this.args = args;
		this.locals = locals;
		this.seq = seq;
	}

	private void setEnvLocal(IEnvironment envLocal) {
		this.envLocal = envLocal;
	}
	
	public void setSelf(ASTInstance self){
		envLocal = envLocal.extend("self", self);
	}
	
	@Override
	public Object eval(IEnvironment env) throws Exception{
		envLocal=env.clone();
		for(ASTDeclaration arg : args){
			envLocal=(IEnvironment) arg.eval(envLocal);
		}
		for(ASTDeclaration local : locals){
			envLocal=(IEnvironment) local.eval(envLocal);
		}
		
		envLocal=envLocal.extend(id.toString(), this);//appel recursif
		return env.extend(id.toString(), this);
	}
	
	public Object call(IEnvironment env,ASTInstance instance,ArrayList<IASTExpression> arguments) throws Exception{
		IEnvironment cloneEnvLocal=envLocal.clone();
		for(int i=0;i<args.size();i++){
			cloneEnvLocal.update(args.get(i).getId(0).toString(), arguments.get(i).eval(env));
		}
		cloneEnvLocal=cloneEnvLocal.concatener(instance.getDictionnaire());
		return seq.eval(cloneEnvLocal);
	}

	@Override
	public Object clone() {
		ASTMethod resultat= new ASTMethod((ASTClassType)classe.clone(), (ASTid)id.clone(), (ArrayList<ASTDeclaration>)args.clone(),(ArrayList<ASTDeclaration>) locals.clone(), (ASTSequence)seq.clone());
		resultat.setEnvLocal(envLocal);
		return resultat;
	}

	@Override
	public String toString() {
		return id.toString();
	}
	
	
}
