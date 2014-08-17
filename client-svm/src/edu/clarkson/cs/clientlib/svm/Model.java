package edu.clarkson.cs.clientlib.svm;

import java.io.File;

public interface Model {

	public void save(File file);

	public void load(File file);
}
