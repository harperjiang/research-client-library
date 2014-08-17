package edu.clarkson.cs.clientlib.svm;

import java.util.ArrayList;
import java.util.List;

import edu.clarkson.cs.clientlib.svm.DataSet.Row;

public class DefaultRow implements Row {

	private List<Object> data;

	public DefaultRow(List<Object> d) {
		super();
		this.data = d;
	}

	public DefaultRow(Object[] d) {
		super();
		this.data = new ArrayList<Object>();
		for (Object o : d)
			data.add(o);
	}

	@Override
	public Object get(int column) {
		return data.get(column);
	}

}
