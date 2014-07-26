package edu.clarkson.cs.clientlib.ripeatlas.api.probe;

import edu.clarkson.cs.clientlib.ripeatlas.Environment;

public class ProbeService {

	public ProbeGetRequest get(String id) {
		ProbeGetRequest req = new ProbeGetRequest();
		req.setId(id);

		req.setGson(Environment.getEnvironment().getGson());
		req.setParser(Environment.getEnvironment().getReader());
		req.setHttpClient(Environment.getEnvironment().getHttpClient());

		return req;
	}

	public ProbeListRequest list(int offset, int limit, Object... query) {
		ProbeListRequest request = new ProbeListRequest();
		request.setLimit(limit);
		request.setOffset(offset);
		request.setQuery(query);

		request.setGson(Environment.getEnvironment().getGson());
		request.setParser(Environment.getEnvironment().getReader());
		request.setHttpClient(Environment.getEnvironment().getHttpClient());

		return request;
	}
}
