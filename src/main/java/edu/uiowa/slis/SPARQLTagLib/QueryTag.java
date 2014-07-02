package edu.uiowa.slis.SPARQLTagLib;

import java.util.Vector;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.log4j.Logger;
import org.apache.taglibs.standard.tag.common.core.Util;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.Syntax;

import edu.uiowa.slis.SPARQLTagLib.util.Endpoint;
import edu.uiowa.slis.SPARQLTagLib.util.Prefix;
import edu.uiowa.slis.SPARQLTagLib.util.ResultImplementation;

// base for cloning: https://svn.java.net/svn/jstl~svn/trunk/impl/src/main/java/org/apache/taglibs/standard/tag/
// https://svn.java.net/svn/jstl~svn/trunk/impl/src/main/java/org/apache/taglibs/standard/tag/common/sql/QueryTagSupport.java

@SuppressWarnings("serial")
public class QueryTag extends BodyTagSupport {
	static Logger logger = Logger.getLogger(QueryTag.class);

	Endpoint endpoint = null;
	String sparql = null;
	String sparqlStatement = null;
	Vector<Prefix> prefixVector = new Vector<Prefix>();
	
	private int scope = PageContext.PAGE_SCOPE;
	private String var = null;
	
	public QueryTag() {
		super();
		init();
	}
	
	private void init() {
		endpoint = null;
		sparql = null;
		
		scope = PageContext.PAGE_SCOPE;
		var = null;
	}
	
    public int doAfterBody() throws JspException {
    	String bodyContent = super.getBodyContent().getString();

    	logger.debug("endpoint: " + endpoint.getUrl());
    	logger.debug("endpoint prefix(es): " + endpoint.getPrefixesAsString());
    	logger.debug("query prefix(es): " + getPrefixesAsString());
    	logger.debug("sparql: " + sparql);
    	logger.debug("tag body: " + bodyContent);
    	
    	if (sparql != null) {
    		sparqlStatement = endpoint.getPrefixesAsString() + getPrefixesAsString() + sparql;
    	} else if (bodyContent != null) {
    		sparqlStatement = endpoint.getPrefixesAsString() + getPrefixesAsString() + bodyContent;
    	}
    	
    	if (sparql == null && bodyContent.trim().length() == 0) {
    	    throw new JspTagException("No SPARQL query specified");
    	}

    	logger.debug("sparqlStatement: " + sparqlStatement);

    	return SKIP_BODY;
    }
    
    public int doEndTag() throws JspException {
    	ResultSet crs = getResultSet(sparqlStatement, endpoint.getUrl());
//		while (crs.hasNext()) {
//			QuerySolution sol = crs.nextSolution();
//			logger.debug("solution: " + sol);
//		}
    	pageContext.setAttribute(var, new ResultImplementation(crs), scope);
    	return EVAL_PAGE;
    }
    
    public void doFinally() {
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

	ResultSet getResultSet(String query, String endpoint) {
		com.hp.hpl.jena.query.Query theClassQuery = QueryFactory.create(query, Syntax.syntaxARQ);
		QueryExecution theClassExecution = QueryExecutionFactory.sparqlService(endpoint, theClassQuery);
		return theClassExecution.execSelect();
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
	
	public void setSparql(String sparql) {
		this.sparql = sparql;
	}

    public Endpoint getEndpoint() {
		return endpoint;
	}
	
	public void setEndpoint(Endpoint endpoint) {
		this.endpoint = endpoint;
	}
}
