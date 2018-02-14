package edu.uiowa.slis.SPARQLTagLib.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Vector;

import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;

public class Triplestore {
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

    public ResultSet getResultSet(String sparqlStatement) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
	ClassLoader classLoader = getClass().getClassLoader();
	Class<?> clas = classLoader.loadClass(container);
	Object instance = clas.newInstance();
	Method method = clas.getMethod("getResultSet", new Class[] { String.class});
	return (ResultSet) method.invoke(instance, new Object[] { sparqlStatement });
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
