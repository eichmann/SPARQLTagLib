package edu.uiowa.slis.SPARQLTagLib.util;

public class Prefix {
	String prefix = null;
	String baseURI = null;
	
	public Prefix(String prefix, String baseURI) {
		this.prefix = prefix;
		this.baseURI = baseURI;
	}
	
	public String toString() {
		return "PREFIX " + prefix + ": <" + baseURI + ">";
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	public String getBaseURI() {
		return baseURI;
	}
	
	public void setBaseURI(String baseURI) {
		this.baseURI = baseURI;
	}

}
