package edu.clarkson.cs.clientlib.ripeatlas.api.measurement;

import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import edu.clarkson.cs.clientlib.common.http.Request;
import edu.clarkson.cs.clientlib.ripeatlas.api.Configuration;

public class MeasurementGetRequest extends Request<MeasurementGetResponse> {

	public MeasurementGetRequest() {
		super(MessageFormat.format("{0}{1}", Configuration.BASE_URL,
				"api/v1/measurement/"));
	}

	@Override
	protected HttpUriRequest buildRequest() {
		Validate.isTrue(!StringUtils.isEmpty(measurementId),
				"Measurement Id cannot be empty");
		String url = MessageFormat.format("{0}{1}/?key={2}", getUrl(),
				measurementId, Configuration.KEY_GET_M);
		if (logger.isDebugEnabled()) {
			logger.debug("Getting measurement:" + url);
		}
		HttpGet get = new HttpGet(url);
		return get;
	}

	private String measurementId;

	public String getMeasurementId() {
		return measurementId;
	}

	public void setMeasurementId(String measurementId) {
		this.measurementId = measurementId;
	}

}
