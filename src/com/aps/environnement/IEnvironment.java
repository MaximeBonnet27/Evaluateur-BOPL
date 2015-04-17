package com.aps.environnement;

public interface IEnvironment {
	public IEnvironment next();
	public void setNext(IEnvironment next);
	public Object getValue(String key);
	public IEnvironment extend(String key, Object value);
	public void update(String key, Object value) throws Exception;
	public IEnvironment concatener(IEnvironment other);
	public void print(String message);
	public IEnvironment clone();
}
