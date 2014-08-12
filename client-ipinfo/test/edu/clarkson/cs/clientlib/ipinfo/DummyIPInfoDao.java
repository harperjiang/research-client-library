package edu.clarkson.cs.clientlib.ipinfo;

import java.util.List;

import edu.clarkson.cs.clientlib.ipinfo.dao.IPInfoDao;
import edu.clarkson.cs.clientlib.ipinfo.model.IPInfo;

public class DummyIPInfoDao implements IPInfoDao {

	@Override
	public IPInfo find(String ip) {
		return null;
	}

	@Override
	public void save(IPInfo info) {

	}

	@Override
	public Cursor<IPInfo> all() {
		return null;
	}

	@Override
	public List<IPInfo> allatonce() {
		return null;
	}

}
