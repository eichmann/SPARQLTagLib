package edu.uiowa.slis.SPARQLTagLib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.apache.taglibs.standard.tag.common.core.Util;

import edu.uiowa.slis.SPARQLTagLib.util.Endpoint;

@SuppressWarnings("serial")
public class SetEndpointTag extends TagSupport {
    static Logger logger = Logger.getLogger(SetEndpointTag.class);

    String sparqlURL = null;
    String user = null;
    String password = null;

    Endpoint endpoint = null;

    private int scope = PageContext.PAGE_SCOPE;
    private String var = null;

    public SetEndpointTag() {
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
	logger.debug("setting var " + var + " to " + sparqlURL);
	endpoint = new Endpoint(sparqlURL);
	pageContext.setAttribute(var, endpoint, scope);
	return EVAL_BODY_INCLUDE;
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
