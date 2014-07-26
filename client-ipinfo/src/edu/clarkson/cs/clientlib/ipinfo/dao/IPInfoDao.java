package edu.clarkson.cs.clientlib.ipinfo.dao;

import org.eclipse.persistence.queries.ScrollableCursor;

import edu.clarkson.cs.clientlib.ipinfo.model.IPInfo;

public interface IPInfoDao {
	
	public IPInfo find(String ip);

	public void save(IPInfo info);

	public ScrollableCursor all();
}
