/**
 * 
 */
package com.aps.ast.instructions;

import com.aps.ast.AST;
import com.aps.environnement.IEnvironment;

/**
 * @author 3100381
 *
 */
public abstract class ASTInstruction extends AST implements IASTInstruction {

	@Override
	public Object clone() {
		return this;
	}

	
}
