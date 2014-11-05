package edu.clarkson.cs.clientlib.geolite;

import javax.persistence.Persistence;

import edu.clarkson.cs.clientlib.geolite.dao.JpaBlockDao;
import edu.clarkson.cs.clientlib.geolite.dao.JpaLocationDao;
import edu.clarkson.cs.common.BeanContext;
import edu.clarkson.cs.common.ContextSet;

public class GeoliteContextSet implements ContextSet {

	@Override
	public void apply() {
		Environment env = new Environment();

		// Prepare JPA environment
		env.setEm(Persistence.createEntityManagerFactory("geolite")
				.createEntityManager());

		JpaBlockDao blockDao = new JpaBlockDao();
		blockDao.setEntityManager(env.getEm());
		BeanContext.get().put("blockDao", blockDao);

		JpaLocationDao locationDao = new JpaLocationDao();
		locationDao.setEntityManager(env.getEm());
		BeanContext.get().put("locationDao", locationDao);
		
		GeoliteAccess geoAccess = new GeoliteAccess();
		geoAccess.setBlockDao(blockDao);
		geoAccess.setLocationDao(locationDao);
		BeanContext.get().put("geoAccess",geoAccess);
	}

}
