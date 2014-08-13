package edu.clarkson.cs.persistence.dv;

import java.util.Date;

import javax.persistence.EntityManager;

public class JpaDataVersionDao implements DataVersionDao {

	private EntityManager entityManager;

	@Override
	public Date get(String key) {
		Date date = (Date) getEntityManager()
				.createNativeQuery(
						"select last_update from data_version where name = ?")
				.setParameter(1, key).getSingleResult();
		return date;
	}

	@Override
	public void update(String key, Date newdate) {
		getEntityManager().getTransaction().begin();
		getEntityManager()
				.createNativeQuery(
						"update data_version set last_update = ? where name = ?")
				.setParameter(1, newdate).setParameter(2, key).executeUpdate();
		getEntityManager().getTransaction().commit();
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
