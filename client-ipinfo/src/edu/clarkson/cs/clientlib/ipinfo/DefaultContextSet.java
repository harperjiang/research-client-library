package edu.clarkson.cs.clientlib.ipinfo;

import javax.persistence.Persistence;

import org.apache.http.impl.client.HttpClients;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import edu.clarkson.cs.clientlib.common.json.BeanDeserializer;
import edu.clarkson.cs.clientlib.ipinfo.dao.JpaIPInfoDao;
import edu.clarkson.cs.clientlib.ipinfo.model.IPInfo;
import edu.clarkson.cs.clientlib.lang.BeanContext;
import edu.clarkson.cs.clientlib.lang.ContextSet;

public class DefaultContextSet implements ContextSet {

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

		// Prepare JPA environment
		env.setEm(Persistence.createEntityManagerFactory("ipinfo")
				.createEntityManager());

		JpaIPInfoDao ipInfoDao = new JpaIPInfoDao();
		ipInfoDao.setEntityManager(env.getEm());

		BeanContext.get().put("ipInfoDao", ipInfoDao);

		IPInfoService ipInfoService = new IPInfoService();
		ipInfoService.setEnv(env);
		ipInfoService.setIpInfoDao(ipInfoDao);
		ipInfoService.afterPropertySet();

		BeanContext.get().put("ipInfoService", ipInfoService);
	}

}
