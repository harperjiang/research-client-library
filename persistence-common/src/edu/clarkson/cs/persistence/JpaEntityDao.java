package edu.clarkson.cs.persistence;

import javax.persistence.EntityTransaction;

public abstract class JpaEntityDao<T extends EntityObject> extends JpaDao {

	public void save(T object) {
		EntityTransaction t = getEntityManager().getTransaction();
		t.begin();

		if (object.getId() == null) {
			getEntityManager().persist(object);
		} else {
			getEntityManager().merge(object);
		}

		t.commit();
	}

}
