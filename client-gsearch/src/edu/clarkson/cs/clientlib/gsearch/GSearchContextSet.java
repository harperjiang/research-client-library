package edu.clarkson.cs.clientlib.gsearch;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import edu.clarkson.cs.clientlib.common.json.BeanDeserializer;
import edu.clarkson.cs.clientlib.gsearch.model.SearchCursor;
import edu.clarkson.cs.clientlib.gsearch.model.SearchResponse;
import edu.clarkson.cs.clientlib.gsearch.model.SearchResult;
import edu.clarkson.cs.clientlib.lang.BeanContext;
import edu.clarkson.cs.clientlib.lang.ContextSet;

public class GSearchContextSet implements ContextSet {

	@Override
	public void apply() {
		Environment env = new Environment();
		// Create HTTP Client
		RequestConfig config = RequestConfig.custom()
				.setSocketTimeout(30 * 1000).setConnectTimeout(30 * 1000)
				.build();
		HttpClient client = HttpClients.custom()
				.setDefaultRequestConfig(config).build();
		env.setHttpClient(client);

		// Create JSON Parser
		GsonBuilder builder = new GsonBuilder();

		// Serializers/Deserializers for GSearch
		builder.registerTypeAdapter(SearchResult.class,
				new BeanDeserializer<SearchResult>());
		builder.registerTypeAdapter(SearchResponse.class,
				new BeanDeserializer<SearchResponse>());
		builder.registerTypeAdapter(SearchCursor.class,
				new BeanDeserializer<SearchCursor>());

		env.setGson(builder.create());
		env.setReader(new JsonParser());

		GSearchAccess gsearchAccess = new GSearchAccess();
		gsearchAccess.setEnv(env);

		BeanContext.get().put("gsearchAccess", gsearchAccess);

	}

}
