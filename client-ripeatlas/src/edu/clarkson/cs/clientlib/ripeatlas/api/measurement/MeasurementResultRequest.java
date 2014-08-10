package edu.clarkson.cs.clientlib.ripeatlas.api.measurement;

import java.text.MessageFormat;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import edu.clarkson.cs.clientlib.common.http.Request;
import edu.clarkson.cs.clientlib.ripeatlas.api.Configuration;

public class MeasurementResultRequest extends
		Request<MeasurementResultResponse> {

	public MeasurementResultRequest() {
		super(MessageFormat.format("{0}{1}", Configuration.BASE_URL,
				"api/v1/measurement/{0}/result/?key={1}"));
	}

	@Override
	protected HttpUriRequest buildRequest() {
		String url = MessageFormat.format(getUrl(),
				String.valueOf(getMeasurementId()), Configuration.KEY_GET_M);
		if (logger.isDebugEnabled()) {
			logger.debug("Get Measurement Result: " + url);
		}
		return new HttpGet(url);
	}

	private int measurementId;

	public int getMeasurementId() {
		return measurementId;
	}

	public void setMeasurementId(int measurementId) {
		this.measurementId = measurementId;
	}
}
