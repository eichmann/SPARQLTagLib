package edu.uiowa.slis.SPARQLTagLib.util;

import java.util.Vector;

public class Endpoint {
	String url = null;
	Vector<Prefix> prefixVector = new Vector<Prefix>();
	
	public Endpoint(String url) {
		this.url = url;
	}
	
	public void addPrefix(Prefix prefix) {
		prefixVector.add(prefix);
	}
	
	public String getPrefixesAsString() {
		StringBuffer buffer = new StringBuffer();
		for (Prefix thePrefix : prefixVector) {
			buffer.append(thePrefix.toString() + "\n");
		}
		return buffer.toString();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
