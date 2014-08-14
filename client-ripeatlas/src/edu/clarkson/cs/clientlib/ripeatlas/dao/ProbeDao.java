package edu.clarkson.cs.clientlib.ripeatlas.dao;

import java.math.BigDecimal;
import java.util.List;

import edu.clarkson.cs.clientlib.ripeatlas.model.Probe;
import edu.clarkson.cs.persistence.EntityDao;

public interface ProbeDao extends EntityDao<Probe> {

	List<Probe> findInCountry(String country);

	List<Probe> findInRange(BigDecimal latmin, BigDecimal latmax,
			BigDecimal longmin, BigDecimal longmax);

	List<Probe> findByIp(String ipv4);

}
