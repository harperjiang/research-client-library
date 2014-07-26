package edu.clarkson.cs.clientlib.ripeatlas.api.measurement;

import java.io.IOException;

import com.google.gson.JsonElement;

import edu.clarkson.cs.clientlib.ripeatlas.json.RipeAtlasResponse;

public class MeasurementCreateResponse extends RipeAtlasResponse<Integer> {

	protected MeasurementCreateResponse() throws IOException {
		super();
	}

	@Override
	protected Integer buildResult(JsonElement json) {
		return json.getAsJsonObject().get("measurements").getAsJsonArray()
				.get(0).getAsInt();
	}

}
