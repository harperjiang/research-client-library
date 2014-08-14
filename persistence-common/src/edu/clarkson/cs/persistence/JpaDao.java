package edu.clarkson.cs.persistence;

import java.lang.reflect.ParameterizedType;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

import org.eclipse.persistence.queries.ScrollableCursor;

public class JpaDao<T> implements Dao<T> {

	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Cursor<T> all() {
		Class<T> param = getParamClass();
		ScrollableCursor sc = (ScrollableCursor) getEntityManager()
				.createQuery(
						MessageFormat.format("select i from {0} i",
								param.getSimpleName()))
				.setHint("eclipselink.cursor.scrollable", true)
				.getSingleResult();
		return new DefaultCursor<T>(sc);
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

	public static final class DefaultCursor<T> implements Cursor<T>,
			Iterator<T> {

		static final int BUFFER_SIZE = 1000;

		private ScrollableCursor sc;

		private List<T> buffer = null;

		private int bufferPointer;

		private int remain;

		private DefaultCursor(ScrollableCursor sc) {
			this.sc = sc;
			this.remain = sc.size();
		}

		@Override
		public boolean hasNext() {
			return remain > 0;
		}

		@Override
		@SuppressWarnings("unchecked")
		public T next() {
			if (remain <= 0) {
				throw new IllegalStateException("No more element");
			}
			if (buffer == null || bufferPointer == buffer.size()) {
				buffer = (List<T>) sc.next(Math.min(BUFFER_SIZE, remain));
				bufferPointer = 0;
			}
			T object = buffer.get((bufferPointer++));
			remain--;
			return object;
		}

		@Override
		public Iterator<T> iterator() {
			return this;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Not supported");
		}
	}
}
