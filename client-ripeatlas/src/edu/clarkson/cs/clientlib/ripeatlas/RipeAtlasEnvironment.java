package edu.clarkson.cs.clientlib.ripeatlas;

import javax.persistence.EntityManager;

import org.apache.http.client.HttpClient;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

public class RipeAtlasEnvironment {

	public RipeAtlasEnvironment() {
		super();
	}

	private HttpClient httpClient;

	private Gson gson;

	private JsonParser reader;

	private EntityManager em;

	public HttpClient getHttpClient() {
		return httpClient;
	}

	public void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public Gson getGson() {
		return gson;
	}

	public void setGson(Gson gson) {
		this.gson = gson;
	}

	public JsonParser getReader() {
		return reader;
	}

	public void setReader(JsonParser reader) {
		this.reader = reader;
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

}
