package edu.clarkson.cs.clientlib.ripeatlas.api.probe;

import java.text.MessageFormat;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import edu.clarkson.cs.clientlib.common.http.Request;
import edu.clarkson.cs.clientlib.ripeatlas.api.Configuration;

public class ProbeGetRequest extends Request<ProbeGetResponse> {

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ProbeGetRequest() {
		super(MessageFormat.format("{0}{1}", Configuration.BASE_URL,
				"api/v1/probe/"));
	}

	@Override
	protected HttpUriRequest buildRequest() {
		HttpGet get = new HttpGet(MessageFormat.format("{0}{1}/", getUrl(), id));
		return get;
	}

}
