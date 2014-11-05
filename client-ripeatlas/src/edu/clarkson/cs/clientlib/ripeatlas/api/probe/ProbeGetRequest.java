package edu.clarkson.cs.clientlib.ripeatlas.api.probe;

import java.text.MessageFormat;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import edu.clarkson.cs.clientlib.ripeatlas.api.Configuration;
import edu.clarkson.cs.httpjson.http.Request;

public class ProbeGetRequest extends Request<ProbeGetResponse> {

	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ProbeGetRequest() {
		super(MessageFormat.format("{0}{1}", Configuration.BASE_URL,
				"api/v1/probe/"));
	}

	@Override
	protected HttpUriRequest buildRequest() {
		HttpGet get = new HttpGet(MessageFormat.format("{0}{1}/", getUrl(),
				String.valueOf(id)));
		return get;
	}

}
