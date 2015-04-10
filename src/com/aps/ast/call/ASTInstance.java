/**
 * 
 */
package com.aps.ast.call;

import java.util.ArrayList;

import com.aps.ast.AST;
import com.aps.ast.declarations.ASTClass;
import com.aps.ast.declarations.ASTClassType;
import com.aps.ast.declarations.ASTMethod;
import com.aps.ast.expressions.ASTid;
import com.aps.environnement.IEnvironment;

/**
 * @author 3100381
 *
 */
public class ASTInstance extends AST {

	private ASTClass classe;

	public ASTInstance(ASTClass classe) {
		super();
		this.classe=(ASTClass) classe.clone();
		this.classe.setDictionnaireClasse(getDictionnaire().extend("self", this));
		if(classe.getSuperClass() != null)
			this.classe.setDictionnaireClasse(getDictionnaire().extend("super",new ASTInstance(classe.getSuperClass())));
	}

	@Override
	public Object eval(IEnvironment env) throws Exception {
		return this;
	}

	public IEnvironment getDictionnaire() {
		return this.classe.getDictionnaireClasse();
	}

	public String getClasseName(){
		return classe.getName();
	}

	public ASTInstance getSuper(){
		try {
			return ((ASTInstance)getDictionnaire().getValue("super"));
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public String toString() {
		return "ASTInstance [classe=" + classe + "]";
	}

	@Override
	public Object clone() {
		return new ASTInstance((ASTClass) classe.clone());
	}
	
	public Object getAttribute(String name){
		Object att = getDictionnaire().getValue(name);
		if(att==null)
			att=getSuper().getAttribute(name);
		return att;
	}
	
	public void updateAttribute(String name,Object value){
		
	}
	
	public ASTMethod getMethode(String name){
		ASTMethod methode;
		System.out.println(name+"? [");
		System.out.println(getDictionnaire()+"\n]");
		methode = (ASTMethod) getDictionnaire().getValue(name);
		if(methode==null)
			methode=(ASTMethod) getSuper().getAttribute(name);
		return methode;
	}
	

}
