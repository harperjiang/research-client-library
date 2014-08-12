package edu.clarkson.cs.persistence;

import java.util.List;

public interface Dao<T> {
	
	public Cursor<T> all();

	public List<T> allatonce();
	
	public static interface Cursor<T> {
		
		public boolean hasNext();
		public T next();
	}
}
