package edu.clarkson.cs.clientlib.ripeatlas;

import edu.clarkson.cs.clientlib.ripeatlas.api.measurement.MeasurementCreateRequest;
import edu.clarkson.cs.clientlib.ripeatlas.api.measurement.MeasurementGetRequest;
import edu.clarkson.cs.clientlib.ripeatlas.api.measurement.MeasurementResultRequest;
import edu.clarkson.cs.clientlib.ripeatlas.model.MeasurementCreate;

public class MeasurementService {

	public MeasurementService() {
		super();
	}

	private Environment env;

	public MeasurementGetRequest get(String id) {
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

	public Environment getEnv() {
		return env;
	}

	public void setEnv(Environment env) {
		this.env = env;
	}

}