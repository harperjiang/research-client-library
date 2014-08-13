package edu.clarkson.cs.clientlib.ripeatlas;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import edu.clarkson.cs.clientlib.lang.BeanContext;
import edu.clarkson.cs.clientlib.ripeatlas.api.ProbeAccess;
import edu.clarkson.cs.clientlib.ripeatlas.dao.ProbeDao;
import edu.clarkson.cs.clientlib.ripeatlas.model.Probe;
import edu.clarkson.cs.persistence.dv.DataVersionDao;

public class ProbeService {

	private ProbeAccess probeAccess;

	private ProbeDao probeDao;

	private DataVersionDao dvDao;

	private Date timestamp;

	protected void checkData() {
		if (timestamp == null) {
			timestamp = dvDao.get("probe");
		}
		if (timestamp == null
				|| System.currentTimeMillis() - timestamp.getTime() >= 86400000l) {
			reload();
			timestamp = new Date();
			dvDao.update("probe", timestamp);
		}
	}

	public Probe getProbe(int id) {
		checkData();
		return getProbeDao().find(id);
	}

	public List<Probe> findProbe(String country) {
		checkData();
		return getProbeDao().findInCountry(country);
	}

	public List<Probe> findProbe(BigDecimal latmin, BigDecimal latmax,
			BigDecimal longmin, BigDecimal longmax) {
		checkData();
		return getProbeDao().findInRange(latmin, latmax, longmin, longmax);
	}

	protected void reload() {
		RipeAtlasEnvironment env = BeanContext.get().get("environment");

		env.getEm().getTransaction().begin();
		env.getEm().createNativeQuery("delete from probe").executeUpdate();
		env.getEm().getTransaction().commit();

		int offset = 0;
		int limit = 1000;

		try {
			while (true) {
				List<Probe> probes = getProbeAccess()
						.list(offset, limit, (Object[]) null).execute()
						.getResult();
				offset += probes.size();
				if (probes.size() == 0)
					break;
				env.getEm().getTransaction().begin();
				for (Probe probe : probes) {
					env.getEm().persist(probe);
				}
				env.getEm().getTransaction().commit();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public ProbeAccess getProbeAccess() {
		return probeAccess;
	}

	public void setProbeAccess(ProbeAccess probeAccess) {
		this.probeAccess = probeAccess;
	}

	public ProbeDao getProbeDao() {
		return probeDao;
	}

	public void setProbeDao(ProbeDao probeDao) {
		this.probeDao = probeDao;
	}

	public DataVersionDao getDvDao() {
		return dvDao;
	}

	public void setDvDao(DataVersionDao dvDao) {
		this.dvDao = dvDao;
	}

}
