package edu.clarkson.cs.clientlib.ripeatlas.api.measurement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;

import edu.clarkson.cs.clientlib.ripeatlas.json.RipeAtlasResponse;
import edu.clarkson.cs.clientlib.ripeatlas.model.MeasurementResult;

public class MeasurementResultResponse extends
		RipeAtlasResponse<List<MeasurementResult>> {

	public MeasurementResultResponse() throws IOException {
		super();
	}

	@Override
	protected List<MeasurementResult> buildResult(JsonElement json) {
		List<MeasurementResult> results = new ArrayList<MeasurementResult>();

		for (JsonElement element : json.getAsJsonArray()) {
			results.add(getGson().fromJson(element, MeasurementResult.class));
		}

		return results;
	}

}
