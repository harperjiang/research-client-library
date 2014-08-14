package edu.clarkson.cs.clientlib.ripeatlas.json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import edu.clarkson.cs.clientlib.ripeatlas.model.ErrorOutput;
import edu.clarkson.cs.clientlib.ripeatlas.model.Output;
import edu.clarkson.cs.clientlib.ripeatlas.model.PingOutput;

public class PingDeserializer implements JsonDeserializer<Output> {

	public Output deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		JsonObject elemjson = json.getAsJsonObject();

		if (elemjson.get("x") == null && elemjson.get("late") == null) {
			PingOutput po = new PingOutput();
			po.setRtt(elemjson.get("rtt").getAsBigDecimal());
			return po;
		} else {
			return new ErrorOutput();
		}
	}
}
