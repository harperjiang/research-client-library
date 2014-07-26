package edu.clarkson.cs.persistence;

import javax.persistence.EntityManager;

public class JpaDao {
	
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
