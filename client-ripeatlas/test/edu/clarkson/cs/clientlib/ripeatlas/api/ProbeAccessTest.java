package edu.clarkson.cs.clientlib.ripeatlas.api;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.clarkson.cs.clientlib.ripeatlas.TestContextSet;
import edu.clarkson.cs.clientlib.ripeatlas.api.ProbeAccess;
import edu.clarkson.cs.clientlib.ripeatlas.model.Probe;
import edu.clarkson.cs.common.BeanContext;

public class ProbeAccessTest {

	private ProbeAccess service;

	@Before
	public void init() {
		if (null == service) {
			new TestContextSet().apply();
		}
		service = BeanContext.get().get("probeService");
	}

	@Test
	public void testGet() throws Exception {
		Probe probe = service.get(12698).execute().getResult();
		assertEquals("128.153.18.184", probe.getAddressV4());
		assertEquals(null, probe.getAddressV6());
		assertEquals(92, probe.getAsnV4());
		assertEquals(0, probe.getAsnV6());
		assertEquals("US", probe.getCountryCode());
		assertEquals(12698, probe.getId().intValue());
		assertEquals("44.6615", probe.getLatitude().toString());
		assertEquals("-74.9985", probe.getLongitude().toString());
		assertEquals("128.153.0.0/16", probe.getPrefixV4());
		assertEquals(null, probe.getPrefixV6());
		assertEquals(null, probe.getResourceUri());
		assertEquals(1, probe.getStatus());

		probe = service.get(6066).execute().getResult();
		assertEquals(33.7705d, probe.getLatitude().doubleValue(), 0.001);
		assertEquals(-84.4195d, probe.getLongitude().doubleValue(), 0.001);
	}

	@Test
	public void testList() throws Exception {
		List<Probe> probes = service.list(0, 100, "country_code", "us")
				.execute().getResult();
		assertEquals(100, probes.size());
	}

}
