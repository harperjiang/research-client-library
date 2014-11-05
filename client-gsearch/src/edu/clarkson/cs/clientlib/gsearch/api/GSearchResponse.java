package edu.clarkson.cs.clientlib.gsearch.api;

import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import edu.clarkson.cs.clientlib.gsearch.model.SearchResponse;
import edu.clarkson.cs.httpjson.http.Response;
import edu.clarkson.cs.httpjson.http.ResponseError;

public class GSearchResponse extends Response<SearchResponse> {

	@Override
	protected SearchResponse buildResult(JsonElement json) {
		JsonObject object = json.getAsJsonObject();
		int queryStatus = object.get("responseStatus").getAsInt();
		if (200 == queryStatus) {
			JsonElement responseData = object.get("responseData");
			SearchResponse response = getGson().fromJson(responseData,
					SearchResponse.class);
			return response;
		} else {
			error = new ResponseError();
			error.setCode(queryStatus);
			error.setMessage(object.get("responseDetails").getAsString());
			logger.warn("Error code:" + error.getCode());
			logger.warn("Error message:" + error.getMessage());
			return null;
		}
	}

	@Override
	protected ResponseError buildError(int errorCode, InputStream content) {
		JsonObject json = getParser().parse(new InputStreamReader(content))
				.getAsJsonObject();
		ResponseError error = new ResponseError();
		error.setCode(errorCode);
		error.setMessage(json.get("responseDetails").getAsString());
		logger.warn("Error code:" + error.getCode());
		logger.warn("Error message:" + error.getMessage());
		return error;
	}

}
