package edu.clarkson.cs.clientlib.lang;

public interface Callback<T> {

	public void done(T result);
}
