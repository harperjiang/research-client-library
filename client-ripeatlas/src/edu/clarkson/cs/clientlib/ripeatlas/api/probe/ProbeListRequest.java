package edu.clarkson.cs.clientlib.ripeatlas.api.probe;

import java.text.MessageFormat;

import edu.clarkson.cs.clientlib.common.http.ListRequest;
import edu.clarkson.cs.clientlib.ripeatlas.api.Configuration;

public class ProbeListRequest extends ListRequest<ProbeListResponse> {

	public ProbeListRequest() {
		super(MessageFormat.format("{0}{1}", Configuration.BASE_URL,
				"api/v1/probe/"));
	}

}
