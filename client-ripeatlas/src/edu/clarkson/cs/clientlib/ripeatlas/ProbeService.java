package edu.clarkson.cs.clientlib.ripeatlas;

import edu.clarkson.cs.clientlib.ripeatlas.api.probe.ProbeGetRequest;
import edu.clarkson.cs.clientlib.ripeatlas.api.probe.ProbeListRequest;

public class ProbeService {

	private Environment env;

	public ProbeGetRequest get(String id) {
		ProbeGetRequest req = new ProbeGetRequest();
		req.setId(id);

		req.setGson(env.getGson());
		req.setParser(env.getReader());
		req.setHttpClient(env.getHttpClient());

		return req;
	}

	public ProbeListRequest list(int offset, int limit, Object... query) {
		ProbeListRequest request = new ProbeListRequest();
		request.setLimit(limit);
		request.setOffset(offset);
		request.setQuery(query);

		request.setGson(env.getGson());
		request.setParser(env.getReader());
		request.setHttpClient(env.getHttpClient());

		return request;
	}

	public Environment getEnv() {
		return env;
	}

	public void setEnv(Environment env) {
		this.env = env;
	}

}
