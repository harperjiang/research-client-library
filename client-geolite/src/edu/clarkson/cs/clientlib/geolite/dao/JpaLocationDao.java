package edu.clarkson.cs.clientlib.geolite.dao;

import edu.clarkson.cs.clientlib.geolite.model.Location;
import edu.clarkson.cs.persistence.JpaEntityDao;

public class JpaLocationDao extends JpaEntityDao<Location> implements
		LocationDao {

}
