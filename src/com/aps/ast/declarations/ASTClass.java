/**
 * 
 */
package com.aps.ast.declarations;

import java.util.ArrayList;

import com.aps.ast.AST;
import com.aps.ast.expressions.ASTExpression;
import com.aps.ast.expressions.ASTid;
import com.aps.environnement.Environment;
import com.aps.environnement.IEnvironment;

/**
 * @author 3100381
 *
 */
public class ASTClass extends ASTExpression{

	private ASTid name;
	private ASTClassType superClass;
	private ArrayList<ASTDeclaration> fields;
	private ArrayList<ASTMethod> methods;

	
	private IEnvironment dictionnaireClasse;
	
			
	public ASTClass(ASTid name, ASTClassType superClass,ArrayList<ASTDeclaration> fields, ArrayList<ASTMethod> methods) {
		super();
		this.name = name;
		this.superClass = superClass;
		this.fields = fields;
		this.methods = methods;
		
	}
	
	private void setDictionnaireClasse(IEnvironment dictionnaireClasse) {
		this.dictionnaireClasse = dictionnaireClasse;
	}

	@Override
	public Object eval(IEnvironment env)throws Exception{
		this.dictionnaireClasse = Environment.EMPTYENV;
		
		for(ASTDeclaration d : fields){
			dictionnaireClasse=(IEnvironment) d.eval(dictionnaireClasse);
		}
		for(ASTMethod m : methods){
			dictionnaireClasse=(IEnvironment)m.eval(dictionnaireClasse);
		}
		
		return env.extend(name.toString(), this);
	}

	@Override
	public Object clone(){
		ASTClass resultat= new ASTClass((ASTid)name.clone(),(ASTClassType)superClass.clone(),(ArrayList<ASTDeclaration>)fields.clone(),(ArrayList<ASTMethod>) methods.clone());
		resultat.setDictionnaireClasse(dictionnaireClasse);
		return resultat;
	}

	public IEnvironment getDictionnaireClasse() {
		return dictionnaireClasse;
	}


	public String getName() {
		return name.toString();
	}
	
	
	
	
}
