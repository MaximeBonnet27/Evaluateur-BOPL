/**
 * 
 */
package com.aps.ast;

/**
 * @author 3100381
 *
 */
public abstract class AST implements IAST{
	public static final Integer CONSTANT_TRUE = 1;
	public static final Integer CONSTANT_FALSE = 0;
	public static final Integer CONSTANT_NULL = 0;
	/**
	 * @param eval
	 * @return
	 */
	public static boolean isTrue(Integer eval) {
		return eval != CONSTANT_FALSE;
	}
	
	public abstract Object clone();
}
