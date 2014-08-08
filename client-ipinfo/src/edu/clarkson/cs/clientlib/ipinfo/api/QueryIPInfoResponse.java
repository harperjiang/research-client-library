package edu.clarkson.cs.clientlib.ipinfo.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import edu.clarkson.cs.clientlib.common.http.Response;
import edu.clarkson.cs.clientlib.common.http.ResponseError;
import edu.clarkson.cs.clientlib.ipinfo.model.IPInfo;

public class QueryIPInfoResponse extends Response<IPInfo> {

	public QueryIPInfoResponse() throws IOException {
		super();
	}

	@Override
	protected IPInfo buildResult(JsonElement json) {
		JsonObject object = json.getAsJsonObject();
		JsonObject queryStatus = object.get("query_status").getAsJsonObject();
		if ("OK".equals(queryStatus.get("query_status_code").getAsString())) {
			JsonElement geoData = object.get("geolocation_data");
			IPInfo info = getGson().fromJson(geoData, IPInfo.class);
			info.setIp(object.get("ip_address").getAsString());
			return info;
		} else {
			error = new ResponseError();
			error.setCode(queryStatus.get("query_status_code").getAsString());
			error.setMessage(queryStatus.get("query_status_description")
					.getAsString());
			logger.warn("Error code:" + error.getCode());
			logger.warn("Error message:" + error.getMessage());
			return null;
		}
	}

	@Override
	protected ResponseError buildError(int errorCode, InputStream content) {
		JsonObject json = getParser().parse(new InputStreamReader(content))
				.getAsJsonObject().get("query_status").getAsJsonObject();
		ResponseError error = new ResponseError();
		error.setCode(errorCode);
		error.setMessage(json.get("query_status_description").getAsString());
		logger.warn("Error code:" + error.getCode());
		logger.warn("Error message:" + error.getMessage());
		return error;
	}

}
