package edu.clarkson.cs.clientlib.ripeatlas.api.measurement;

import java.io.IOException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import edu.clarkson.cs.clientlib.ripeatlas.json.RipeAtlasResponse;

public class MeasurementCreateResponse extends RipeAtlasResponse<Integer[]> {

	public MeasurementCreateResponse() throws IOException {
		super();
	}

	@Override
	protected Integer[] buildResult(JsonElement json) {
		JsonArray idArray = json.getAsJsonObject().get("measurements")
				.getAsJsonArray();
		Integer[] res = new Integer[idArray.size()];
		for (int i = 0; i < idArray.size(); i++) {
			res[i] = idArray.get(i).getAsInt();
		}
		return res;
	}

}
