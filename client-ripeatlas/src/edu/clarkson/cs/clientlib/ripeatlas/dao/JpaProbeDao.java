package edu.clarkson.cs.clientlib.ripeatlas.dao;

import java.math.BigDecimal;
import java.util.List;

import edu.clarkson.cs.clientlib.ripeatlas.model.Probe;
import edu.clarkson.cs.persistence.JpaEntityDao;

public class JpaProbeDao extends JpaEntityDao<Probe> implements ProbeDao {

	@Override
	public List<Probe> findInCountry(String country) {
		return getEntityManager()
				.createQuery(
						"select p from Probe p " + "where p.publicc = true "
								+ "and p.status = 1 "
								+ "and p.countryCode = :cc", Probe.class)
				.setParameter("cc", country).getResultList();
	}

	@Override
	public List<Probe> findInRange(BigDecimal latmin, BigDecimal latmax,
			BigDecimal longmin, BigDecimal longmax) {
		return getEntityManager()
				.createQuery(
						"select p from Probe p where p.publicc = true "
								+ "and p.status = 1 and (p.latitude between :latmin and :latmax) "
								+ "and (p.longitude between :longmin and :longmax)",
						Probe.class).setParameter("latmin", latmin)
				.setParameter("latmax", latmax)
				.setParameter("longmin", longmin)
				.setParameter("longmax", longmax).getResultList();
	}

}
