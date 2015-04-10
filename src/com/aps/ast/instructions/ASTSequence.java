/**
 * 
 */
package com.aps.ast.instructions;

import java.util.ArrayList;

import com.aps.ast.AST;
import com.aps.environnement.IEnvironment;

/**
 * @author 3100381
 * 
 */
public class ASTSequence extends ASTInstruction{

	private ArrayList<IASTInstruction> instructions;

	public ASTSequence(ArrayList<IASTInstruction> instructions) {
		this.instructions = instructions;
	}

	public void push(IASTInstruction i){
		instructions.add(0,i);
	}
	
	@Override
	public Object eval(IEnvironment env)throws Exception{
		Object val;
		if (instructions == null || instructions.isEmpty())
			return AST.CONSTANT_NULL;
		else {
			for (IASTInstruction i : instructions) {
				val = i.eval(env);
				if(i instanceof ASTReturn){
					return val;
				}
			}
		}

		return AST.CONSTANT_NULL;
	}

}
