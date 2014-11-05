package edu.clarkson.cs.clientlib.ripeatlas.api.measurement;

import java.text.MessageFormat;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpUriRequest;

import edu.clarkson.cs.clientlib.ripeatlas.api.Configuration;
import edu.clarkson.cs.httpjson.QueryUtils;
import edu.clarkson.cs.httpjson.http.Request;

public class MeasurementStopRequest extends Request<MeasurementStopResponse> {

	public MeasurementStopRequest() {
		super(MessageFormat.format("{0}{1}", Configuration.BASE_URL,
				"api/v1/measurement/"));
	}

	@Override
	protected HttpUriRequest buildRequest() {
		HttpDelete delete = new HttpDelete(MessageFormat.format("{0}?{1}",
				getUrl(),
				QueryUtils.queryString("key", Configuration.KEY_STOP_M)));

		return delete;
	}

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
