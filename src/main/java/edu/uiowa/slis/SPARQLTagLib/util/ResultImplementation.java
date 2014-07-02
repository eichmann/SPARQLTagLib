package edu.uiowa.slis.SPARQLTagLib.util;

import java.util.SortedMap;

import javax.servlet.jsp.jstl.sql.Result;

import org.apache.log4j.Logger;

import com.hp.hpl.jena.query.ResultSet;

public class ResultImplementation implements Result {
	static Logger logger = Logger.getLogger(ResultImplementation.class);
	ResultSet rs = null;
	
	String[] columnNames = null;
	int rowCount = 0;
	
	public ResultImplementation(ResultSet rs) {
		this.rs = rs;
		columnNames = getColumnNames();
	}

	@Override
	public String[] getColumnNames() {
		String[] result = new String[rs.getResultVars().size()];
		int fence = 0;
		for (String var : rs.getResultVars()) {
			logger.debug("var: " + var);
			result[fence++] = var;
		}
		return result;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SortedMap[] getRows() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[][] getRowsByIndex() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isLimitedByMaxRows() {
		return false;
	}

}
