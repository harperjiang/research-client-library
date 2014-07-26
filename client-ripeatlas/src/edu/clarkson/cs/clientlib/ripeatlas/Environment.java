package edu.clarkson.cs.clientlib.ripeatlas;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import edu.clarkson.cs.clientlib.common.json.BeanDeserializer;
import edu.clarkson.cs.clientlib.common.json.BeanSerializer;
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

public class Environment {

	private Environment() {
		super();
		// Create HTTP Client
		httpClient = HttpClients.createDefault();

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

		gson = builder.create();

		mcbs.setGson(gson);
		ttbs.setGson(gson);
		psbs.setGson(gson);

		reader = new JsonParser();

	}

	private static Environment instance;
	static {
		instance = new Environment();
	}

	public static Environment getEnvironment() {
		return instance;
	}

	private HttpClient httpClient;

	private Gson gson;

	private JsonParser reader;

	public HttpClient getHttpClient() {
		return httpClient;
	}

	public Gson getGson() {
		return gson;
	}

	public JsonParser getReader() {
		return reader;
	}
}
