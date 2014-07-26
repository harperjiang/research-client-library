package edu.clarkson.cs.clientlib.ipinfo;

import org.apache.http.impl.client.HttpClients;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import edu.clarkson.cs.clientlib.common.json.BeanDeserializer;
import edu.clarkson.cs.clientlib.ipinfo.dao.IPInfoDao;
import edu.clarkson.cs.clientlib.ipinfo.model.IPInfo;
import edu.clarkson.cs.clientlib.lang.BeanContext;
import edu.clarkson.cs.clientlib.lang.ContextSet;

public class TestContextSet implements ContextSet {

	@Override
	public void apply() {
		Environment env = new Environment();
		// Create HTTP Client
		env.setHttpClient(HttpClients.createDefault());

		// Create JSON Parser
		GsonBuilder builder = new GsonBuilder();

		// Serializers/Deserializers for IPAddress
		builder.registerTypeAdapter(IPInfo.class,
				new BeanDeserializer<IPInfo>());

		env.setGson(builder.create());
		env.setReader(new JsonParser());

		IPInfoDao ipInfoDao = new DummyIPInfoDao();

		BeanContext.get().put("ipInfoDao", ipInfoDao);

		IPInfoAccess ipInfoService = new IPInfoAccess();
		ipInfoService.setIpInfoDao(ipInfoDao);
		ipInfoService.setEnv(env);
		ipInfoService.afterPropertySet();

		BeanContext.get().put("ipInfoService", ipInfoService);
	}

}
