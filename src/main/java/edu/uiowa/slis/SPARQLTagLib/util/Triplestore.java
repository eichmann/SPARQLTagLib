package edu.uiowa.slis.SPARQLTagLib.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Vector;

import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.Syntax;
import org.apache.jena.rdf.model.Model;
import org.apache.log4j.Logger;

import edu.uiowa.slis.SPARQLTagLib.ParameterTag;

public class Triplestore {
    static Logger logger = Logger.getLogger(Triplestore.class);
    String container = null;
    Vector<Prefix> prefixVector = new Vector<Prefix>();

    public Triplestore(String container) {
	this.container = container;
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

    public String getContainer() {
	return container;
    }

    public void setContainer(String container) {
	this.container = container;
    }

    public ResultSet getResultSet(String sparqlStatement, Vector<Parameter> parameterVector) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
	ClassLoader classLoader = getClass().getClassLoader();
	Class<?> clas = classLoader.loadClass(container);
	Object instance = clas.newInstance();
	Method method = clas.getMethod("getResultSet", new Class[] { String.class});
	if (parameterVector.size() == 0) {
	    return (ResultSet) method.invoke(instance, new Object[] { sparqlStatement });
	} else {
	    ParameterizedSparqlString parameterizedString = new ParameterizedSparqlString(sparqlStatement);
	    for (Parameter theParameter : parameterVector) {
		if (theParameter.isIRI())
		    parameterizedString.setIri(theParameter.getVar(), theParameter.getValue());
		else
		    parameterizedString.setLiteral(theParameter.getVar(), theParameter.getValue());
	    }
	    logger.debug("parameterized query: " + parameterizedString.toString());
	    return (ResultSet) method.invoke(instance, new Object[] { parameterizedString.toString() });
	}
    }

    public boolean getAskResponse(String sparqlStatement) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
	ClassLoader classLoader = getClass().getClassLoader();
	Class<?> clas = classLoader.loadClass(container);
	Object instance = clas.newInstance();
	Method method = clas.getMethod("getAskResponse", new Class[] { String.class});
	return (Boolean) method.invoke(instance, new Object[] { sparqlStatement });
    }

    public Model getModel(String sparqlStatement) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
	ClassLoader classLoader = getClass().getClassLoader();
	Class<?> clas = classLoader.loadClass(container);
	Object instance = clas.newInstance();
	Method method = clas.getMethod("getModel", new Class[] { String.class});
	return (Model) method.invoke(instance, new Object[] { sparqlStatement });
    }

}
