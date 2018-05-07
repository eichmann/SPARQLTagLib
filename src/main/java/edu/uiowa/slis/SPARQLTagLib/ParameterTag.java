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
	ConstructTag theConstructTag = null;
	DescribeTag theDescribeTag = null;
	AskTag theAskTag = null;
	QueryTag theQueryTag = (QueryTag) findAncestorWithClass(this, QueryTag.class);

	if (theQueryTag == null)
	    theConstructTag = (ConstructTag) findAncestorWithClass(this, ConstructTag.class);

	if (theQueryTag == null)
	    theDescribeTag = (DescribeTag) findAncestorWithClass(this, DescribeTag.class);

	if (theQueryTag == null)
	    theAskTag = (AskTag) findAncestorWithClass(this, AskTag.class);

	if (theQueryTag == null && theConstructTag == null && theDescribeTag == null && theAskTag == null)
	    throw new JspTagException("No SPARQL statement for parameter specified");

	if (theQueryTag != null)
	    theQueryTag.addParameter(new Parameter(var, value, type));

	if (theConstructTag != null)
	    theConstructTag.addParameter(new Parameter(var, value, type));

	if (theDescribeTag != null)
	    theDescribeTag.addParameter(new Parameter(var, value, type));

	if (theAskTag != null)
	    theAskTag.addParameter(new Parameter(var, value, type));

	logger.debug("parameter " + var + " : " + value);
	return SKIP_BODY;
    }

    public int doEndTag() throws JspException {
	clearServiceState();
	return EVAL_PAGE;
    }

    public void doFinally() {
    }
    
    void clearServiceState() {
	type = "literal";
	endpoint = null;
	value = null;
	var = null;
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
