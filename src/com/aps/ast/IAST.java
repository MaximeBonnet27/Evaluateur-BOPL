package com.aps.ast;

import com.aps.environnement.IEnvironment;

public interface IAST {
	
	public Object eval(IEnvironment env) throws Exception;
	
}
