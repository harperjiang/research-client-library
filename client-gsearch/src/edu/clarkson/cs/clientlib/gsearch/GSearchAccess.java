package edu.clarkson.cs.clientlib.gsearch;

import edu.clarkson.cs.clientlib.gsearch.api.GSearchRequest;
import edu.clarkson.cs.httpjson.DefaultHttpService;

public class GSearchAccess extends DefaultHttpService {

	private Environment env;

	public Environment getEnv() {
		return env;
	}

	public void setEnv(Environment env) {
		this.env = env;
	}

	public GSearchRequest search(String queryString) {
		GSearchRequest request = new GSearchRequest();
		request.setGson(env.getGson());
		request.setHttpClient(env.getHttpClient());
		request.setParser(env.getReader());
		request.setQueryString(queryString);
		return request;
	}
}
