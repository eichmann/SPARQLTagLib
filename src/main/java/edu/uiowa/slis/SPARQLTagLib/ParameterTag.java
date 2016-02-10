package edu.uiowa.slis.SPARQLTagLib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import edu.uiowa.slis.SPARQLTagLib.util.Endpoint;
import edu.uiowa.slis.SPARQLTagLib.util.Parameter;

@SuppressWarnings("serial")
public class ParameterTag extends TagSupport {
    static Logger logger = Logger.getLogger(ParameterTag.class);

    String var = null;
    String value = null;
    String type = "literal";

    Endpoint endpoint = null;

    public ParameterTag() {
	super();
	init();
    }

    private void init() {
	var = null;
	value = null;
	type = "literal";
    }

    public int doStartTag() throws JspException {
	QueryTag theQueryTag = (QueryTag) findAncestorWithClass(this, QueryTag.class);

	if (theQueryTag == null)
	    throw new JspTagException("No SPARQL query for parameter specified");

	if (theQueryTag != null)
	    theQueryTag.addParameter(new Parameter(var, value, type));

	logger.debug("parameter " + var + " : " + value);
	return SKIP_BODY;
    }

    public void release() {
	init();
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
}
