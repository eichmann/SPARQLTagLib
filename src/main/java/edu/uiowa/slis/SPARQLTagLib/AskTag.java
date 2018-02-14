package edu.uiowa.slis.SPARQLTagLib;

import java.util.Vector;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.log4j.Logger;
import org.apache.taglibs.standard.tag.common.core.Util;

import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.Syntax;

import edu.uiowa.slis.SPARQLTagLib.util.Graph;
import edu.uiowa.slis.SPARQLTagLib.util.Endpoint;
import edu.uiowa.slis.SPARQLTagLib.util.Parameter;
import edu.uiowa.slis.SPARQLTagLib.util.Prefix;
import edu.uiowa.slis.SPARQLTagLib.util.Triplestore;

// base for cloning: https://svn.java.net/svn/jstl~svn/trunk/impl/src/main/java/org/apache/taglibs/standard/tag/
// https://svn.java.net/svn/jstl~svn/trunk/impl/src/main/java/org/apache/taglibs/standard/tag/common/sql/QueryTagSupport.java

@SuppressWarnings("serial")
public class AskTag extends BodyTagSupport {
    static Logger logger = Logger.getLogger(AskTag.class);

    Endpoint endpoint = null;
    Triplestore triplestore = null;
    Graph graph = null;
    String sparql = null;
    String sparqlStatement = null;
    Vector<Prefix> prefixVector = new Vector<Prefix>();
    Vector<Parameter> parameterVector = new Vector<Parameter>();

    private int scope = PageContext.PAGE_SCOPE;
    private String var = null;

    public AskTag() {
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

	if (endpoint != null) {
	    logger.debug("endpoint: " + endpoint.getUrl());
	    logger.debug("endpoint prefix(es): " + endpoint.getPrefixesAsString());
	}
	if (triplestore != null) {
	    logger.debug("triplestore: " + triplestore.getContainer());
	    logger.debug("triplestore prefix(es): " + triplestore.getPrefixesAsString());
	}
	logger.debug("query prefix(es): " + getPrefixesAsString());
	logger.debug("sparql: " + sparql);
	logger.debug("tag body: " + bodyContent);

	if (sparql != null) {
	    sparqlStatement = endpoint.getPrefixesAsString() + getPrefixesAsString() + sparql;
	} else if (bodyContent != null && endpoint != null) {
	    sparqlStatement = endpoint.getPrefixesAsString() + getPrefixesAsString() + bodyContent;
	} else if (bodyContent != null && triplestore != null) {
	    sparqlStatement = triplestore.getPrefixesAsString() + getPrefixesAsString() + bodyContent;
	} else if (bodyContent != null && graph != null) {
	    sparqlStatement = graph.getPrefixesAsString() + getPrefixesAsString() + bodyContent;
	}

	if (sparql == null && bodyContent.trim().length() == 0) {
	    throw new JspTagException("No SPARQL query specified");
	}

	if (endpoint == null && triplestore == null && graph == null) {
	    throw new JspTagException("No endpoint, triplestore or construct specified");
	}

	logger.info("sparqlStatement: " + sparqlStatement);

	return SKIP_BODY;
    }

    public int doEndTag() throws JspException {
	boolean response = false;
	
	if (endpoint != null)
	    response = getAskResponse(sparqlStatement, endpoint.getUrl());
	if (triplestore != null)
	    try {
		response = triplestore.getAskResponse(sparqlStatement);
	    } catch (Exception e) {
		logger.error("Error raised calling support tag: ", e);
		throw new JspException("Error raised calling support tag");
	    }
	if (graph != null)
	    try {
		response = graph.getAskResponse(sparqlStatement);
	    } catch (Exception e) {
		logger.error("Error raised calling support tag: ", e);
		throw new JspException("Error raised calling support tag");
	    }

	pageContext.setAttribute(var, response, scope);
	return EVAL_PAGE;
    }

    public void doFinally() {
    }

    public void addPrefix(Prefix prefix) {
	prefixVector.add(prefix);
    }

    public void addParameter(Parameter parameter) {
	parameterVector.add(parameter);
    }

    public String getPrefixesAsString() {
	StringBuffer buffer = new StringBuffer();
	for (Prefix thePrefix : prefixVector) {
	    buffer.append(thePrefix.toString() + "\n");
	}
	return buffer.toString();
    }

    boolean getAskResponse(String query, String endpoint) {
	Query theQuery = null;

	if (parameterVector.size() == 0) {
	    theQuery = QueryFactory.create(query, Syntax.syntaxARQ);
	} else {
	    ParameterizedSparqlString parameterizedString = new ParameterizedSparqlString(sparqlStatement);
	    for (Parameter theParameter : parameterVector) {
		if (theParameter.isIRI())
		    parameterizedString.setIri(theParameter.getVar(), theParameter.getValue());
		else
		    parameterizedString.setLiteral(theParameter.getVar(), theParameter.getValue());
	    }
	    logger.debug("parameterized query: " + parameterizedString.toString());
	    theQuery = parameterizedString.asQuery();
	}

	QueryExecution theClassExecution = QueryExecutionFactory.sparqlService(endpoint, theQuery);
	return theClassExecution.execAsk();
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

    public Triplestore getTriplestore() {
        return triplestore;
    }

    public void setTriplestore(Triplestore triplestore) {
        this.triplestore = triplestore;
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }
}
