package edu.clarkson.cs.persistence;

import java.text.MessageFormat;

import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

public abstract class JpaEntityDao<T extends EntityObject> extends JpaDao<T>
		implements EntityDao<T> {

	public T find(int id) {
		String sql = MessageFormat.format(
				"select t from {0} t where t.id = :id", getParamClass()
						.getSimpleName());
		try {
			return getEntityManager().createQuery(sql, getParamClass())
					.setParameter("id", id).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

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
