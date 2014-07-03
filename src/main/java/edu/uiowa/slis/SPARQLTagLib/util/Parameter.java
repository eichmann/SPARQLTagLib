package edu.uiowa.slis.SPARQLTagLib.util;

public class Parameter {
	String var = null;
	String value = null;
	String type = null;
	
	public Parameter(String var, String value, String type) {
		this.var = var;
		this.value = value;
		this.type = type.toLowerCase();
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

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public boolean isIRI() {
		return "iri".equals(type);
	}

}
