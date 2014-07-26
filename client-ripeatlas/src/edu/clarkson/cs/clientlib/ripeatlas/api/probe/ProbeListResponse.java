package edu.clarkson.cs.clientlib.ripeatlas.api.probe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;

import edu.clarkson.cs.clientlib.ripeatlas.json.ListResponse;
import edu.clarkson.cs.clientlib.ripeatlas.model.Probe;

public class ProbeListResponse extends ListResponse<List<Probe>> {

	public ProbeListResponse() throws IOException {
		super();
	}

	@Override
	protected List<Probe> buildResult(JsonElement json) {
		List<Probe> probes = new ArrayList<Probe>();

		metainfo(json);
		for (JsonElement elem : json.getAsJsonObject().get("objects")
				.getAsJsonArray()) {
			Probe probe = getGson().fromJson(elem, Probe.class);
			probes.add(probe);
		}

		return probes;
	}

}
