package edu.clarkson.cs.persistence;

public interface EntityDao<T extends EntityObject> {
	public T find(int id);

	public void save(T object);
}
