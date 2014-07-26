package edu.clarkson.cs.clientlib.ripeatlas.json;

import edu.clarkson.cs.clientlib.ripeatlas.model.ErrorOutput;
import edu.clarkson.cs.clientlib.ripeatlas.model.Output;
import edu.clarkson.cs.clientlib.ripeatlas.model.TracerouteOutput;
import edu.clarkson.cs.clientlib.ripeatlas.model.TracerouteOutput.TracerouteData;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class TracerouteDeserializer implements JsonDeserializer<Output> {

	public Output deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		JsonObject elemjson = json.getAsJsonObject();
		if (elemjson.get("error") != null) {
			ErrorOutput error = new ErrorOutput();
			error.setErrorMessage(elemjson.get("error").getAsString());
			return error;
		}

		TracerouteOutput tr = new TracerouteOutput();
		tr.setHop(elemjson.get("hop").getAsInt());
		for (JsonElement sr : elemjson.get("result").getAsJsonArray()) {
			JsonObject sro = sr.getAsJsonObject();
			if (sro.get("error") != null) {
				// Error output, just ignore

			} else if (sro.get("x") == null && sro.get("late") == null) {
				TracerouteData data = new TracerouteData();
				/*
				 * If nothing is returned, the result will be like "x":"*". Some
				 * strange result including "late"/"ittl" will also be ignored.
				 */
				data.setFrom(sro.get("from").getAsString());
				data.setRoundTripTime(sro.get("rtt").getAsBigDecimal());
				data.setSize(sro.get("size").getAsInt());
				data.setTimeToLive(sro.get("ttl").getAsInt());
				tr.getData().add(data);
			}
		}
		return tr;
	}
}
