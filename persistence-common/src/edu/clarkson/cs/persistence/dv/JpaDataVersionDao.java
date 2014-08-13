package edu.clarkson.cs.persistence.dv;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JpaDataVersionDao implements DataVersionDao {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private EntityManager entityManager;

	@Override
	public Date get(String key) {
		try {
			ResultSet result = (ResultSet) getEntityManager()
					.createNativeQuery(
							"select last_update from data_version where name = ?")
					.setParameter(1, key).getSingleResult();
			if (result.isAfterLast())
				return null;
			Date date = new Date(result.getTimestamp(1).getTime());
			return date;
		} catch (SQLException e) {
			logger.error("Exception while fetching timestamp", e);
			throw new RuntimeException(e);
		}
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
