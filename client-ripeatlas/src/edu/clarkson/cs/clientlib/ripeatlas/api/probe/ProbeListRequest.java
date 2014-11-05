package edu.clarkson.cs.clientlib.ripeatlas.api.probe;

import java.text.MessageFormat;

import edu.clarkson.cs.clientlib.ripeatlas.api.Configuration;
import edu.clarkson.cs.httpjson.http.ListRequest;

public class ProbeListRequest extends ListRequest<ProbeListResponse> {

	public ProbeListRequest() {
		super(MessageFormat.format("{0}{1}", Configuration.BASE_URL,
				"api/v1/probe/"));
	}

}
