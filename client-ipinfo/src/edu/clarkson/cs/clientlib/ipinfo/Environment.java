package edu.clarkson.cs.clientlib.ipinfo;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import edu.clarkson.cs.clientlib.common.json.BeanDeserializer;
import edu.clarkson.cs.clientlib.ipinfo.model.IPInfo;

public class Environment {

	private Environment() {
		super();
		// Create HTTP Client
		httpClient = HttpClients.createDefault();

		// Create JSON Parser
		GsonBuilder builder = new GsonBuilder();

		// Serializers/Deserializers for IPAddress
		builder.registerTypeAdapter(IPInfo.class,
				new BeanDeserializer<IPInfo>());

		gson = builder.create();
		reader = new JsonParser();

		// Prepare JPA environment
		em = Persistence.createEntityManagerFactory("ipinfo")
				.createEntityManager();
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

	private EntityManager em;

	public HttpClient getHttpClient() {
		return httpClient;
	}

	public Gson getGson() {
		return gson;
	}

	public JsonParser getReader() {
		return reader;
	}

	public EntityManager getEntityManager() {
		return em;
	}
}
