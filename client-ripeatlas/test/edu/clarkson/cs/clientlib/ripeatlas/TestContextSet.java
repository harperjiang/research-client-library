package edu.clarkson.cs.clientlib.ripeatlas;

import org.apache.http.impl.client.HttpClients;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import edu.clarkson.cs.clientlib.ripeatlas.RipeAtlasEnvironment;
import edu.clarkson.cs.clientlib.ripeatlas.api.MeasurementAccess;
import edu.clarkson.cs.clientlib.ripeatlas.api.ProbeAccess;
import edu.clarkson.cs.clientlib.ripeatlas.json.MeasurementDeserializer;
import edu.clarkson.cs.clientlib.ripeatlas.json.MeasurementResultDeserializer;
import edu.clarkson.cs.clientlib.ripeatlas.json.TracerouteDeserializer;
import edu.clarkson.cs.clientlib.ripeatlas.model.Measurement;
import edu.clarkson.cs.clientlib.ripeatlas.model.MeasurementCreate;
import edu.clarkson.cs.clientlib.ripeatlas.model.MeasurementResult;
import edu.clarkson.cs.clientlib.ripeatlas.model.Probe;
import edu.clarkson.cs.clientlib.ripeatlas.model.ProbeSpec;
import edu.clarkson.cs.clientlib.ripeatlas.model.TracerouteOutput;
import edu.clarkson.cs.clientlib.ripeatlas.model.TracerouteTarget;
import edu.clarkson.cs.common.BeanContext;
import edu.clarkson.cs.common.ContextSet;
import edu.clarkson.cs.httpjson.json.BeanDeserializer;
import edu.clarkson.cs.httpjson.json.BeanSerializer;

public class TestContextSet implements ContextSet {

	@Override
	public void apply() {
		RipeAtlasEnvironment env = new RipeAtlasEnvironment();
		// Create HTTP Client
		env.setHttpClient(HttpClients.createDefault());

		// Create JSON Parser
		GsonBuilder builder = new GsonBuilder();

		// Serializer/Deserializers for RipeAtlas
		builder.registerTypeAdapter(MeasurementResult.class,
				new MeasurementResultDeserializer());
		builder.registerTypeAdapter(TracerouteOutput.class,
				new TracerouteDeserializer());
		builder.registerTypeAdapter(Measurement.class,
				new MeasurementDeserializer());
		builder.registerTypeAdapter(Probe.class, new BeanDeserializer<Probe>());

		BeanSerializer<MeasurementCreate> mcbs = new BeanSerializer<MeasurementCreate>();
		builder.registerTypeAdapter(MeasurementCreate.class, mcbs);
		BeanSerializer<TracerouteTarget> ttbs = new BeanSerializer<TracerouteTarget>();
		builder.registerTypeAdapter(TracerouteTarget.class, ttbs);
		BeanSerializer<ProbeSpec> psbs = new BeanSerializer<ProbeSpec>();
		builder.registerTypeAdapter(ProbeSpec.class, psbs);

		Gson gson = builder.create();
		env.setGson(gson);

		env.setReader(new JsonParser());
		
		
		// Beans
		MeasurementAccess ms = new MeasurementAccess();
		ms.setEnv(env);
		BeanContext.get().put("measurementService", ms);
		
		ProbeAccess ps = new ProbeAccess();
		ps.setEnv(env);
		BeanContext.get().put("probeService", ps);
		
	}

}
