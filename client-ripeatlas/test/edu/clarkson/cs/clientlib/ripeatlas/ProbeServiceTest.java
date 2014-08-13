package edu.clarkson.cs.clientlib.ripeatlas;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.clarkson.cs.clientlib.lang.BeanContext;
import edu.clarkson.cs.clientlib.ripeatlas.model.Probe;

public class ProbeServiceTest {

	ProbeService probeService;

	@BeforeClass
	public static void init() {
		new RipeAtlasContextSet().apply();
	}

	@Before
	public void begin() {
		probeService = BeanContext.get().get("probeService");
	}

	@Test
	public void testGetProbe() {
		Probe probe = probeService.getProbe(4);
		assertEquals("83.163.50.165", probe.getAddressV4());
	}

	@Test
	public void testFindProbeString() {
		List<Probe> probes = probeService.findProbe("US");
		assertEquals(609, probes.size());
	}

	@Test
	public void testFindProbeIntIntIntInt() {
		List<Probe> probes = probeService.findProbe(
				new BigDecimal("41.437368"), new BigDecimal("42.147924"),
				new BigDecimal("-88.193144"), new BigDecimal("-87.179655"));
		assertEquals(14, probes.size());
	}

}
