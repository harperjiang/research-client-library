package edu.clarkson.cs.clientlib.ripeatlas.api.probe;

import java.io.IOException;

import com.google.gson.JsonElement;

import edu.clarkson.cs.clientlib.ripeatlas.json.RipeAtlasResponse;
import edu.clarkson.cs.clientlib.ripeatlas.model.Probe;

public class ProbeGetResponse extends RipeAtlasResponse<Probe> {

	public ProbeGetResponse() throws IOException {
		super();
	}

	@Override
	protected Probe buildResult(JsonElement json) {
		return getGson().fromJson(json, Probe.class);
	}

}
