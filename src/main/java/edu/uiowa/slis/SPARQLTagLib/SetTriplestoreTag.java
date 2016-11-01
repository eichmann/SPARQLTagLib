package edu.uiowa.slis.SPARQLTagLib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.apache.taglibs.standard.tag.common.core.Util;

import edu.uiowa.slis.SPARQLTagLib.util.Triplestore;

@SuppressWarnings("serial")
public class SetTriplestoreTag extends TagSupport {
    static Logger logger = Logger.getLogger(SetTriplestoreTag.class);

    String container = null;
    String user = null;
    String password = null;

    Triplestore triplestore = null;

    private int scope = PageContext.PAGE_SCOPE;
    private String var = null;

    public SetTriplestoreTag() {
	super();
	init();
    }

    private void init() {
	container = null;
	user = null;
	password = null;

	scope = PageContext.PAGE_SCOPE;
	var = null;
    }

    public int doStartTag() throws JspException {
	logger.debug("setting var " + var + " to " + container);
	triplestore = new Triplestore(container);
	pageContext.setAttribute(var, triplestore, scope);
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

    public String getContainer() {
	return container;
    }

    public void setContainer(String container) {
	this.container = container;
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
