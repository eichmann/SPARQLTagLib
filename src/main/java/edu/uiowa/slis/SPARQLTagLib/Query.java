package edu.uiowa.slis.SPARQLTagLib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.taglibs.standard.tag.common.core.Util;

// base for cloning: https://svn.java.net/svn/jstl~svn/trunk/impl/src/main/java/org/apache/taglibs/standard/tag/
// https://svn.java.net/svn/jstl~svn/trunk/impl/src/main/java/org/apache/taglibs/standard/tag/common/sql/QueryTagSupport.java

@SuppressWarnings("serial")
public class Query extends BodyTagSupport {
	String sparql = null;
	
	private int scope = PageContext.PAGE_SCOPE;
	private String var = null;
	
	public Query() {
		super();
		init();
	}
	
	private void init() {
		sparql = null;
		
		scope = PageContext.PAGE_SCOPE;
		var = null;
	}
	
    public int doStartTag() throws JspException {
    	String sparqlStatement = null;
    	
    	if (sparql != null) {
    		sparqlStatement = sparql;
    	} else if (bodyContent != null) {
    		sparqlStatement = bodyContent.getString();
    	}
    	
    	if (sparqlStatement == null || sparqlStatement.trim().length() == 0) {
    	    throw new JspTagException("No SPARQL query specified");
    	}

    	pageContext.setAttribute(var, sparql, scope);
    	return EVAL_BODY_BUFFERED;
    }
    
    public int doEndTag() throws JspException {
    	return EVAL_PAGE;
    }
    
    public void doFinally() {
    }
    
    public void setScope(String scope) {
        this.scope = Util.getScope(scope);
    }

    public void setVar(String var) {
	this.var = var;
    }

    public String getSparql() {
		return sparql;
	}
	
	public void setSparqlURL(String sparql) {
		this.sparql = sparql;
	}
}
