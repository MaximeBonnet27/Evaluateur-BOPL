/**
 * 
 */
package com.aps.ast.call;

import com.aps.ast.AST;
import com.aps.ast.declarations.ASTClass;
import com.aps.ast.declarations.ASTClassType;
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

	@Override
	public Object clone() {
		return new ASTInstance((ASTClass) classe.clone());
	}

}
