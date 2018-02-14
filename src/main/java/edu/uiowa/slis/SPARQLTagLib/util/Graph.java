package edu.uiowa.slis.SPARQLTagLib.util;

import java.lang.reflect.InvocationTargetException;
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

public class Graph {
    static Logger logger = Logger.getLogger(Graph.class);
    Model model = null;
    Vector<Prefix> prefixVector = new Vector<Prefix>();
    Vector<Parameter> parameterVector = new Vector<Parameter>();

    public Graph(Model model) {
	this.model = model;
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

    public Model getModel() {
	return model;
    }

    public void setModel(Model model) {
	this.model = model;
    }

    public ResultSet getResultSet(String sparqlStatement) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
	Query theQuery = null;

	if (parameterVector.size() == 0) {
	    theQuery = QueryFactory.create(sparqlStatement, Syntax.syntaxARQ);
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

	QueryExecution theClassExecution = QueryExecutionFactory.create(theQuery, model);
	return theClassExecution.execSelect();
    }

    public boolean getAskResponse(String sparqlStatement) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
	Query theQuery = null;

	if (parameterVector.size() == 0) {
	    theQuery = QueryFactory.create(sparqlStatement, Syntax.syntaxARQ);
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

	QueryExecution theClassExecution = QueryExecutionFactory.create(theQuery, model);
	return theClassExecution.execAsk();
    }

}
