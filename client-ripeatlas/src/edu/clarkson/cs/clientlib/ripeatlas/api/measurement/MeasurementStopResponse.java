package edu.clarkson.cs.clientlib.ripeatlas.api.measurement;

import java.io.IOException;

import com.google.gson.JsonElement;

import edu.clarkson.cs.clientlib.ripeatlas.json.RipeAtlasResponse;

public class MeasurementStopResponse extends RipeAtlasResponse<Object> {

	protected MeasurementStopResponse() throws IOException {
		super();
	}

	@Override
	protected Object buildResult(JsonElement json) {
		return null;
	}

}
