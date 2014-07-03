package edu.uiowa.slis.SPARQLTagLib.util;

public class Parameter {
	String var = null;
	String value = null;
	
	public Parameter(String var, String value) {
		this.var = var;
		this.value = value;
	}
	
	public String getVar() {
		return var;
	}
	
	public void setVar(String var) {
		this.var = var;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}

}
