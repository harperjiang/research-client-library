package edu.clarkson.cs.clientlib.ipinfo;

import org.eclipse.persistence.queries.ScrollableCursor;

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
	public ScrollableCursor all() {
		return new ScrollableCursor() {
			public int size() {
				return 0;
			}
		};
	}

}
