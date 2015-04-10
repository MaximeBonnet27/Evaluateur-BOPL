/**
 * 
 */
package com.aps.ast.expressions;

import com.aps.ast.AST;
import com.aps.environnement.IEnvironment;

/**
 * @author 3100381
 *
 */
public abstract class ASTExpression extends AST implements IASTExpression{

	public static final True TRUE = new True();
	public static final False FALSE = new False();
	public static final Null NULL = new Null();

	private static class True extends ASTExpression{
		@Override
		public Object eval(IEnvironment env) throws Exception {
			return AST.CONSTANT_TRUE;
		}
		
	}
	private static class False extends ASTExpression{
		@Override
		public Object eval(IEnvironment env) throws Exception {
			return AST.CONSTANT_FALSE;
		}
	}
	private static class Null extends ASTExpression{

		@Override
		public Object eval(IEnvironment env) throws Exception {
			return AST.CONSTANT_NULL;
		}
		
	}
	@Override
	public IASTExpression clone() {
		return this;
	}
	
	
	
}
