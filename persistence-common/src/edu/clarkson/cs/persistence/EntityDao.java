package edu.clarkson.cs.persistence;

public interface EntityDao<T extends EntityObject> extends Dao<T> {
	public T find(int id);

	public void save(T object);
}
