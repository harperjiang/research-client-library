package edu.clarkson.cs.clientlib.ipinfo;

import javax.persistence.Persistence;

import org.apache.http.impl.client.HttpClients;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import edu.clarkson.cs.clientlib.ipinfo.dao.JpaIPInfoDao;
import edu.clarkson.cs.clientlib.ipinfo.model.IPInfo;
import edu.clarkson.cs.common.BeanContext;
import edu.clarkson.cs.common.ContextSet;
import edu.clarkson.cs.httpjson.json.BeanDeserializer;

public class IPInfoContextSet implements ContextSet {

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

		IPInfoAccess ipinfoAccess = new IPInfoAccess();
		ipinfoAccess.setEnv(env);
		ipinfoAccess.setIpInfoDao(ipInfoDao);
		ipinfoAccess.afterPropertySet();

		BeanContext.get().put("ipinfoAccess", ipinfoAccess);
	}

}
