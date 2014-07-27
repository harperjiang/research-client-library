package edu.clarkson.cs.clientlib.ripeatlas;

import edu.clarkson.cs.clientlib.ripeatlas.api.measurement.MeasurementCreateRequest;
import edu.clarkson.cs.clientlib.ripeatlas.api.measurement.MeasurementGetRequest;
import edu.clarkson.cs.clientlib.ripeatlas.api.measurement.MeasurementResultRequest;
import edu.clarkson.cs.clientlib.ripeatlas.model.MeasurementCreate;

public class MeasurementAccess {

	public MeasurementAccess() {
		super();
	}

	private RipeAtlasEnvironment env;

	public MeasurementGetRequest get(int id) {
		MeasurementGetRequest req = new MeasurementGetRequest();
		req.setMeasurementId(id);

		req.setGson(env.getGson());
		req.setParser(env.getReader());
		req.setHttpClient(env.getHttpClient());

		return req;
	}

	public MeasurementResultRequest result(String id) {
		MeasurementResultRequest mrr = new MeasurementResultRequest();
		mrr.setMeasurementId(id);

		mrr.setGson(env.getGson());
		mrr.setParser(env.getReader());
		mrr.setHttpClient(env.getHttpClient());

		return mrr;
	}

	public MeasurementCreateRequest create(MeasurementCreate m) {
		MeasurementCreateRequest mcr = new MeasurementCreateRequest();
		mcr.setMeasurement(m);

		mcr.setGson(env.getGson());
		mcr.setParser(env.getReader());
		mcr.setHttpClient(env.getHttpClient());

		return mcr;
	}

	public RipeAtlasEnvironment getEnv() {
		return env;
	}

	public void setEnv(RipeAtlasEnvironment env) {
		this.env = env;
	}

}
