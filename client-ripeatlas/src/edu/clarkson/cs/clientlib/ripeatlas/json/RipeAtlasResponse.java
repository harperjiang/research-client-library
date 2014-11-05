package edu.clarkson.cs.clientlib.ripeatlas.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.JsonObject;

import edu.clarkson.cs.httpjson.http.Response;
import edu.clarkson.cs.httpjson.http.ResponseError;

public abstract class RipeAtlasResponse<T> extends Response<T> {

	protected RipeAtlasResponse() throws IOException {
		super();
	}

	protected ResponseError buildError(int errorCode, InputStream content) {
		JsonObject json = getParser().parse(new InputStreamReader(content))
				.getAsJsonObject().get("error").getAsJsonObject();
		ResponseError error = new ResponseError();
		error.setCode(json.get("code").getAsInt());
		error.setMessage(json.get("message").getAsString());
		logger.warn("Error code:" + error.getCode());
		logger.warn("Error message:" + error.getMessage());
		return error;
	}
}
