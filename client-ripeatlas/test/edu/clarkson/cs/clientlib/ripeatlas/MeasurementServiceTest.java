package edu.clarkson.cs.clientlib.ripeatlas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.clarkson.cs.clientlib.lang.BeanContext;
import edu.clarkson.cs.clientlib.ripeatlas.MeasurementService;
import edu.clarkson.cs.clientlib.ripeatlas.model.Measurement;
import edu.clarkson.cs.clientlib.ripeatlas.model.MeasurementCreate;
import edu.clarkson.cs.clientlib.ripeatlas.model.MeasurementResult;
import edu.clarkson.cs.clientlib.ripeatlas.model.ProbeSpec;
import edu.clarkson.cs.clientlib.ripeatlas.model.TracerouteOutput;
import edu.clarkson.cs.clientlib.ripeatlas.model.TracerouteOutput.TracerouteData;
import edu.clarkson.cs.clientlib.ripeatlas.model.TracerouteTarget;

public class MeasurementServiceTest {

	private MeasurementService service;

	@Before
	public void init() {
		if (null == service) {
			new TestContextSet().apply();
		}
		service = BeanContext.get().get("measurementService");
	}

	@Test
	public void testGet() throws Exception {
		Measurement measurement = service.get(1033381).execute().getResult();
		assertEquals("Test measurement", measurement.getDescription());
		assertEquals(4, measurement.getAf());
		assertEquals(1381982713000l, measurement.getCreationTime().getTime());
		assertEquals("198.45.56.12", measurement.getDstAddr());
		assertEquals(2906, measurement.getDstAsn());
		assertEquals("netflix01.ring.nlnog.net", measurement.getDstName());
		assertEquals(1033381, measurement.getId());
		assertEquals(60, measurement.getInterval());
		assertEquals(1381982713000l, measurement.getStartTime().getTime());
		assertEquals(4, measurement.getStatus());
		assertEquals(1381983300000l, measurement.getStopTime().getTime());
		assertEquals("traceroute", measurement.getType());
		assertNull(measurement.getProbeSources());
	}

	@Test
	public void testResult() throws Exception {
		List<MeasurementResult> results = service.result("1033381").execute()
				.getResult();
		assertEquals(6, results.size());
		MeasurementResult result = results.get(5);

		assertEquals(TracerouteOutput.class,
				result.getOutputs().get(result.getOutputs().size() - 1)
						.getClass());

		TracerouteOutput output = (TracerouteOutput) result.getOutputs().get(
				result.getOutputs().size() - 1);
		assertEquals(3, output.getData().size());
		TracerouteData data = output.getData().get(0);
		assertEquals("198.45.56.12", data.getFrom());
		assertEquals("79.808999999999997", data.getRoundTripTime().toString());
		assertEquals(60, data.getSize());
		assertEquals(55, data.getTimeToLive());
	}

	@Test
	public void testCreate() throws Exception {
		MeasurementCreate mc = new MeasurementCreate();

		TracerouteTarget tt = new TracerouteTarget();
		tt.setOneoff(true);
		tt.setAf(4);
		tt.setTarget("www.clarkson.edu");
		tt.setDescription("Test Measurement Create");
		tt.setProtocol("TCP");
		tt.setResolveOnProbe(true);
		tt.setType("traceroute");

		mc.getTargets().add(tt);

		ProbeSpec ps = new ProbeSpec();
		ps.setRequested(3);
		ps.setType("probes");

		StringBuilder sb = new StringBuilder();
		sb.append(6018).append(",");
		sb.append(6019).append(",");
		sb.append(17);
		ps.setValue(sb.toString());

		mc.getProbes().add(ps);
		// TODO We don't want to ruin the online environment with this test
		// service.create(mc).execute();
	}

}
