package edu.clarkson.cs.clientlib.ripeatlas;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.apache.http.impl.client.HttpClients;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import edu.clarkson.cs.clientlib.common.json.BeanDeserializer;
import edu.clarkson.cs.clientlib.common.json.BeanSerializer;
import edu.clarkson.cs.clientlib.lang.BeanContext;
import edu.clarkson.cs.clientlib.lang.ContextSet;
import edu.clarkson.cs.clientlib.ripeatlas.api.MeasurementAccess;
import edu.clarkson.cs.clientlib.ripeatlas.api.ProbeAccess;
import edu.clarkson.cs.clientlib.ripeatlas.dao.JpaProbeDao;
import edu.clarkson.cs.clientlib.ripeatlas.json.MeasurementDeserializer;
import edu.clarkson.cs.clientlib.ripeatlas.json.MeasurementResultDeserializer;
import edu.clarkson.cs.clientlib.ripeatlas.json.TracerouteDeserializer;
import edu.clarkson.cs.clientlib.ripeatlas.model.Measurement;
import edu.clarkson.cs.clientlib.ripeatlas.model.MeasurementCreate;
import edu.clarkson.cs.clientlib.ripeatlas.model.MeasurementResult;
import edu.clarkson.cs.clientlib.ripeatlas.model.PingOutput;
import edu.clarkson.cs.clientlib.ripeatlas.model.Probe;
import edu.clarkson.cs.clientlib.ripeatlas.model.ProbeSpec;
import edu.clarkson.cs.clientlib.ripeatlas.model.TracerouteOutput;
import edu.clarkson.cs.clientlib.ripeatlas.model.TracerouteTarget;
import edu.clarkson.cs.persistence.dv.JpaDataVersionDao;

public class RipeAtlasContextSet implements ContextSet {

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
		builder.registerTypeAdapter(PingOutput.class,
				new BeanDeserializer<PingOutput>());
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

		mcbs.setGson(gson);
		ttbs.setGson(gson);
		psbs.setGson(gson);

		env.setReader(new JsonParser());

		BeanContext.get().put("environment", env);

		// Beans
		MeasurementAccess ms = new MeasurementAccess();
		ms.setEnv(env);
		BeanContext.get().put("measurementAccess", ms);

		ProbeAccess ps = new ProbeAccess();
		ps.setEnv(env);
		BeanContext.get().put("probeAccess", ps);

		EntityManager em = Persistence.createEntityManagerFactory("ripeatlas")
				.createEntityManager();
		env.setEm(em);

		JpaProbeDao probeDao = new JpaProbeDao();
		probeDao.setEntityManager(em);
		BeanContext.get().put("probeDao", probeDao);
		
		JpaDataVersionDao dvDao = new JpaDataVersionDao();
		dvDao.setEntityManager(em);
		BeanContext.get().put("dataVersionDao", dvDao);

		ProbeService probeService = new ProbeService();
		probeService.setProbeAccess(ps);
		probeService.setProbeDao(probeDao);
		probeService.setDvDao(dvDao);
		BeanContext.get().put("probeService", probeService);

	}

}
