package edu.clarkson.cs.persistence;

import java.lang.reflect.ParameterizedType;
import java.text.MessageFormat;
import java.util.List;

import javax.persistence.EntityManager;

import org.eclipse.persistence.queries.ScrollableCursor;

public class JpaDao<T> {

	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public ScrollableCursor all() {
		Class<T> param = getParamClass();
		return (ScrollableCursor) getEntityManager()
				.createQuery(
						MessageFormat.format("select i from {0} i",
								param.getSimpleName()))
				.setHint("eclipselink.cursor.scrollable", true)
				.getSingleResult();
	}

	public List<T> allatonce() {
		Class<T> param = getParamClass();
		return getEntityManager().createQuery(
				MessageFormat.format("select a from {0} a",
						param.getSimpleName()), param).getResultList();
	}

	@SuppressWarnings("unchecked")
	protected Class<T> getParamClass() {
		return (Class<T>) (((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0]);
	}
}
