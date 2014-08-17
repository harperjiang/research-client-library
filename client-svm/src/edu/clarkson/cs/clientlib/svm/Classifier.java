package edu.clarkson.cs.clientlib.svm;

public interface Classifier {

	public DataSet classify(Model model, DataSet input);
}
