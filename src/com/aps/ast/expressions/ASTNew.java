/**
 * 
 */
package com.aps.ast.expressions;

import com.aps.ast.IAST;
import com.aps.ast.call.ASTInstance;
import com.aps.ast.declarations.ASTClass;
import com.aps.ast.declarations.ASTClassType;
import com.aps.ast.operators.AbstractUnaryOperator;
import com.aps.environnement.IEnvironment;

/**
 * @author 3100381
 *
 */
public class ASTNew extends AbstractUnaryOperator{

	
	public ASTNew(IAST operand) {
		super(operand);
	}

	@Override
	public Object eval(IEnvironment env) throws Exception{
		return new ASTInstance((ASTClass) operand.eval(env));
	}

}
