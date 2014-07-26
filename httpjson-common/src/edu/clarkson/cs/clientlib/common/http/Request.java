package edu.clarkson.cs.clientlib.common.http;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

public abstract class Request<T extends Response<?>> {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	private String url;

	private HttpClient httpClient;

	private Gson gson;

	private JsonParser parser;

	public Request(String url) {
		super();
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public T execute() throws ClientProtocolException, IOException {
		HttpUriRequest request = buildRequest();
		try {
			HttpResponse response = getHttpClient().execute(request);
			return buildResponse(response);
		} catch (ClientProtocolException e) {
			logger.error("Exception should be corrected", e);
			return null;
		}
	}

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

	public JsonParser getParser() {
		return parser;
	}

	public void setParser(JsonParser parser) {
		this.parser = parser;
	}

	protected abstract HttpUriRequest buildRequest();

	protected T buildResponse(HttpResponse response) throws IOException {
		try {
			T resp = getParamClass().newInstance();
			resp.setGson(getGson());
			resp.setParser(getParser());
			resp.process(response);

			return resp;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	protected Class<T> getParamClass() {
		return (Class<T>) (((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0]);
	}
}
