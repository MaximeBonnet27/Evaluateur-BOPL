/**
 * 
 */
package com.aps.ast.declarations;

import java.util.ArrayList;

import com.aps.ast.AST;
import com.aps.ast.call.ASTInstance;
import com.aps.ast.expressions.ASTExpression;
import com.aps.ast.expressions.ASTid;
import com.aps.ast.expressions.IASTExpression;
import com.aps.environnement.Environment;
import com.aps.environnement.IEnvironment;

/**
 * @author 3100381
 *
 */
public class ASTClass extends ASTExpression{

	private ASTid name;
	private ASTClassType superClassType;
	private ASTClass superClass;
	private ArrayList<ASTDeclaration> fields;
	private ArrayList<ASTMethod> methods;


	private IEnvironment dictionnaireClasse;


	public ASTClass(ASTid name, ASTClassType superClass,ArrayList<ASTDeclaration> fields, ArrayList<ASTMethod> methods) {
		super();
		this.name = name;
		this.superClassType = superClass;
		this.fields = fields;
		this.methods = methods;

	}

	public void setDictionnaireClasse(IEnvironment dictionnaireClasse) {
		this.dictionnaireClasse = dictionnaireClasse;
	}

	@Override
	public Object eval(IEnvironment env)throws Exception{
		//if(superClass.toString().equals("obj")){
			superClass=(ASTClass) superClassType.eval(env);
			this.dictionnaireClasse=Environment.EMPTYENV;
		/*}else{

			ASTInstance superclass=new ASTInstance((ASTClass)superClass.eval(env));
			this.dictionnaireClasse = superclass.getDictionnaire();
		}*/

		for(ASTDeclaration d : fields){
			dictionnaireClasse=(IEnvironment) d.eval(dictionnaireClasse);
		}
		for(ASTMethod m : methods){
			dictionnaireClasse=(IEnvironment)m.eval(dictionnaireClasse);
		}

		
		return env.extend(name.toString(), this);
	}

	@Override
	public IASTExpression clone(){
		ASTClass resultat= new ASTClass((ASTid)name.clone(),(ASTClassType)superClassType.clone(),(ArrayList<ASTDeclaration>)fields.clone(),(ArrayList<ASTMethod>) methods.clone());
		resultat.setDictionnaireClasse(dictionnaireClasse.clone());
		return resultat;
	}

	public IEnvironment getDictionnaireClasse() {
		return dictionnaireClasse;
	}


	public String getName() {
		return name.toString();
	}

	
	public ASTClass getSuperClass() {
		return superClass;
	}

	@Override
	public String toString() {
		return "ASTClass [name=" + name + "]";
	}


}
