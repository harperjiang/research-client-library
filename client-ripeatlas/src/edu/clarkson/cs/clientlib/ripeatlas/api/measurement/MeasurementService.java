package edu.clarkson.cs.clientlib.ripeatlas.api.measurement;

import edu.clarkson.cs.clientlib.ripeatlas.Environment;
import edu.clarkson.cs.clientlib.ripeatlas.model.MeasurementCreate;

public class MeasurementService {

	public MeasurementService() {
		super();
	}

	public MeasurementGetRequest get(String id) {
		MeasurementGetRequest req = new MeasurementGetRequest();
		req.setMeasurementId(id);

		req.setGson(Environment.getEnvironment().getGson());
		req.setParser(Environment.getEnvironment().getReader());
		req.setHttpClient(Environment.getEnvironment().getHttpClient());

		return req;
	}

	public MeasurementResultRequest result(String id) {
		MeasurementResultRequest mrr = new MeasurementResultRequest();
		mrr.setMeasurementId(id);

		mrr.setGson(Environment.getEnvironment().getGson());
		mrr.setParser(Environment.getEnvironment().getReader());
		mrr.setHttpClient(Environment.getEnvironment().getHttpClient());

		return mrr;
	}

	public MeasurementCreateRequest create(MeasurementCreate m) {
		MeasurementCreateRequest mcr = new MeasurementCreateRequest();
		mcr.setMeasurement(m);
		
		mcr.setGson(Environment.getEnvironment().getGson());
		mcr.setParser(Environment.getEnvironment().getReader());
		mcr.setHttpClient(Environment.getEnvironment().getHttpClient());
		
		return mcr;
	}
}
