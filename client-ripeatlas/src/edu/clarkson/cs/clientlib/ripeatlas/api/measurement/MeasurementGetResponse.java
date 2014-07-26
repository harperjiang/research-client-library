package edu.clarkson.cs.clientlib.ripeatlas.api.measurement;

import java.io.IOException;

import com.google.gson.JsonElement;

import edu.clarkson.cs.clientlib.ripeatlas.json.RipeAtlasResponse;
import edu.clarkson.cs.clientlib.ripeatlas.model.Measurement;

public class MeasurementGetResponse extends RipeAtlasResponse<Measurement> {

	public MeasurementGetResponse() throws IOException {
		super();
	}

	@Override
	protected Measurement buildResult(JsonElement json) {
		return getGson().fromJson(json, Measurement.class);
	}
}
