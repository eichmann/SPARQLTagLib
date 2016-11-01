package edu.uiowa.slis.SPARQLTagLib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import edu.uiowa.slis.SPARQLTagLib.util.Endpoint;
import edu.uiowa.slis.SPARQLTagLib.util.Prefix;

@SuppressWarnings("serial")
public class PrefixTag extends TagSupport {
    static Logger logger = Logger.getLogger(PrefixTag.class);

    String prefix = null;
    String baseURI = null;

    Endpoint endpoint = null;

    public PrefixTag() {
	super();
	init();
    }

    private void init() {
	prefix = null;
	baseURI = null;
    }

    public int doStartTag() throws JspException {
	SetEndpointTag theEndpointTag = (SetEndpointTag) findAncestorWithClass(this, SetEndpointTag.class);
	SetTriplestoreTag theTriplestoreTag = (SetTriplestoreTag) findAncestorWithClass(this, SetTriplestoreTag.class);
	QueryTag theQueryTag = (QueryTag) findAncestorWithClass(this, QueryTag.class);

	if (theEndpointTag == null && theTriplestoreTag == null && theQueryTag == null)
	    throw new JspTagException("No SPARQL endpoint or triplestore for prefix specified");

	if (theTriplestoreTag != null)
	    theTriplestoreTag.triplestore.addPrefix(new Prefix(prefix, baseURI));

	if (theEndpointTag != null)
	    theEndpointTag.endpoint.addPrefix(new Prefix(prefix, baseURI));

	if (theQueryTag != null)
	    theQueryTag.addPrefix(new Prefix(prefix, baseURI));

	logger.debug("prefix " + prefix + " : " + baseURI);
	return SKIP_BODY;
    }

    public void release() {
	init();
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
