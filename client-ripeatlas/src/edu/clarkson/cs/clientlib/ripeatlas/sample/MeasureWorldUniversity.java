package edu.clarkson.cs.clientlib.ripeatlas.sample;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.clarkson.cs.clientlib.ripeatlas.api.MeasurementAccess;
import edu.clarkson.cs.clientlib.ripeatlas.model.MeasurementCreate;
import edu.clarkson.cs.clientlib.ripeatlas.model.ProbeSpec;
import edu.clarkson.cs.clientlib.ripeatlas.model.TracerouteTarget;

public class MeasureWorldUniversity {

	static Logger logger = LoggerFactory
			.getLogger(MeasureWorldUniversity.class);

	static MeasurementAccess service = new MeasurementAccess();

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(
						"src/gdc/network/ripeatlas/snippet/target")));
		String line = null;
		while ((line = br.readLine()) != null) {
			measureTarget(line.trim(), 10);
		}

		br.close();
	}

	private static void measureTarget(String target, int count)
			throws Exception {

		MeasurementCreate mc = new MeasurementCreate();

		TracerouteTarget tt = new TracerouteTarget();
		tt.setDescription(MessageFormat.format("MeasureWorld_ICMP_{0}", target));
		tt.setAf(4);
		tt.setType("traceroute");
		tt.setOneoff(true);
		tt.setTarget(target);
		tt.setProtocol("ICMP");

		mc.getTargets().add(tt);

		ProbeSpec probeSpec = new ProbeSpec();
		probeSpec.setRequested(count);
		probeSpec.setType("area");
		probeSpec.setValue("WW");
		mc.getProbes().add(probeSpec);

		service.create(mc).execute();
	}

}
