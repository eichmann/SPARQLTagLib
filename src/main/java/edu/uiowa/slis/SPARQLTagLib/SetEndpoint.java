package edu.uiowa.slis.SPARQLTagLib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.taglibs.standard.tag.common.core.Util;

@SuppressWarnings("serial")
public class SetEndpoint extends TagSupport {
	String sparqlURL = null;
	String user = null;
	String password = null;
	
	private int scope = PageContext.PAGE_SCOPE;
	private String var = null;
	
	public SetEndpoint() {
		super();
		init();
	}
	
	private void init() {
		sparqlURL = null;
		user = null;
		password = null;
		
		scope = PageContext.PAGE_SCOPE;
		var = null;
	}
	
    public int doStartTag() throws JspException {
	    pageContext.setAttribute(var, sparqlURL, scope);
    	return SKIP_BODY;
    }
    
    public void release() {
    	init();
    }
    
    public void setScope(String scope) {
        this.scope = Util.getScope(scope);
    }

    public void setVar(String var) {
	this.var = var;
    }

    public String getSparqlURL() {
		return sparqlURL;
	}
	
	public void setSparqlURL(String sparqlURL) {
		this.sparqlURL = sparqlURL;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

}
