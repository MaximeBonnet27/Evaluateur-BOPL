package com.aps.environnement;

public class Environment implements IEnvironment{
	private String key;
	private Object value;
	private IEnvironment oldEnv;
	
	public static final IEnvironment EMPTYENV=new EmptyEnvironment();
	
	public Environment(IEnvironment oldEnv, String key, Object value){
		this.oldEnv=oldEnv;
		this.key = key;
		this.value = value;
	}
	
	public Object getValue(String key) throws Exception{
		if(this.key.equals(key))
			return value;
		else
			return oldEnv.getValue(key);
	}
	
	@Override
	public IEnvironment extend(String key, Object value) {
		/*this.oldEnv = (IEnvironment) this.clone();
		this.key = key;
		this.value = value; 
		return this;*/
		return new Environment(this, key, value);
	}
	
	@Override
	public void update(String key, Object value) throws Exception {
		if(this.key.equals(key)){
			this.value = value;
		}
		else {
			oldEnv.update(key, value);
		}
	}
	
	@Override
	public IEnvironment concatener(IEnvironment other) {
		return new Environment(oldEnv.concatener(other), key, other);
		/*
		IEnvironment resultat = next();
		while(!(resultat.next() instanceof EmptyEnvironment))
			resultat=resultat.next();
		resultat.setNext(other);
		return this;
		*/
	}

	@Override
	protected Object clone(){
		return new Environment(oldEnv, key, value);
	}

	@Override
	public void print() {
		System.out.println(key+" : "+value);
		oldEnv.print();
		
	}

	@Override
	public IEnvironment next() {
		return oldEnv;
	}
	
	@Override
	public void setNext(IEnvironment next){
		oldEnv=next;
	}
	
	/*************************************/
	
	
	private static class EmptyEnvironment implements IEnvironment{

		@Override
		public Object getValue(String key) {
			return null;
		}

		@Override
		public IEnvironment extend(String key, Object value) {
			return new Environment(this, key, value);
		}

		@Override
		public void update(String key, Object value) throws Exception {
			throw new Exception("key not found : " + key);
		}

		@Override
		public IEnvironment concatener(IEnvironment other) {
			return other;
		}

		@Override
		public void print() {

		}

		@Override
		public IEnvironment next() {
			return null;
		}

		@Override
		public void setNext(IEnvironment next) {
			
		}
		
	}

	

	
}