package edu.clarkson.cs.clientlib.ripeatlas.json;

import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import edu.clarkson.cs.clientlib.common.json.DeserializerUtils;
import edu.clarkson.cs.clientlib.ripeatlas.model.MeasurementResult;
import edu.clarkson.cs.clientlib.ripeatlas.model.Output;
import edu.clarkson.cs.clientlib.ripeatlas.model.PingOutput;
import edu.clarkson.cs.clientlib.ripeatlas.model.TracerouteOutput;

public class MeasurementResultDeserializer implements
		JsonDeserializer<MeasurementResult> {

	public MeasurementResult deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		JsonObject jsonObject = (JsonObject) json;

		MeasurementResult mresult = new MeasurementResult();

		mresult.setAf(jsonObject.get("af").getAsInt());
		mresult.setDstAddr(DeserializerUtils.getString("dst_addr", jsonObject));
		// mresult.setDstAddr(jsonObject.get("dst_addr").getAsString());
		mresult.setDstName(jsonObject.get("dst_name").getAsString());
		if (jsonObject.get("endtime") != null)
			mresult.setEndTime(new Date(jsonObject.get("endtime").getAsLong()));
		mresult.setFrom(jsonObject.get("from").getAsString());
		mresult.setFw(jsonObject.get("fw").getAsInt());
		mresult.setMeasurementId(jsonObject.get("msm_id").getAsInt());
		mresult.setProbeId(jsonObject.get("prb_id").getAsInt());
		mresult.setProtocol(jsonObject.get("proto").getAsString());
		mresult.setSize(jsonObject.get("size").getAsInt());
		mresult.setTimestamp(new Date(
				jsonObject.get("timestamp").getAsLong() * 1000));

		String type = jsonObject.get("type").getAsString();
		mresult.setType(type);

		MeasurementType mt = MeasurementType.valueOf(type);
		JsonElement result = jsonObject.get("result");

		for (JsonElement elem : result.getAsJsonArray()) {
			switch (mt) {
			case traceroute:
				mresult.getOutputs().add(
						(Output) context.deserialize(elem,
								TracerouteOutput.class));
				break;
			case ping:
				mresult.getOutputs().add(
						(Output) context.deserialize(elem, PingOutput.class));
				break;
			default:
				break;
			}
		}
		return mresult;
	}
}
