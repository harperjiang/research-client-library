package edu.clarkson.cs.clientlib.ripeatlas.api.measurement;

import java.text.MessageFormat;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import edu.clarkson.cs.clientlib.ripeatlas.api.Configuration;
import edu.clarkson.cs.httpjson.http.Request;

public class MeasurementGetRequest extends Request<MeasurementGetResponse> {

	public MeasurementGetRequest() {
		super(MessageFormat.format("{0}{1}", Configuration.BASE_URL,
				"api/v1/measurement/"));
	}

	@Override
	protected HttpUriRequest buildRequest() {
		String url = MessageFormat.format("{0}{1}/?key={2}", getUrl(),
				String.valueOf(measurementId), Configuration.KEY_GET_M);
		if (logger.isDebugEnabled()) {
			logger.debug("Getting measurement:" + url);
		}
		HttpGet get = new HttpGet(url);
		return get;
	}

	private int measurementId;

	public int getMeasurementId() {
		return measurementId;
	}

	public void setMeasurementId(int measurementId) {
		this.measurementId = measurementId;
	}

}
