package edu.clarkson.cs.clientlib.svm;

import edu.clarkson.cs.clientlib.svm.DataSet.Row;


public interface DataSet extends Iterable<Row> {

	public interface Row {
		public Object get(int column);
	}

}
