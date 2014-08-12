package edu.clarkson.cs.clientlib.ipinfo.dao;

import edu.clarkson.cs.clientlib.ipinfo.model.IPInfo;
import edu.clarkson.cs.persistence.Dao;

public interface IPInfoDao extends Dao<IPInfo> {
	
	public IPInfo find(String ip);

	public void save(IPInfo info);

}
