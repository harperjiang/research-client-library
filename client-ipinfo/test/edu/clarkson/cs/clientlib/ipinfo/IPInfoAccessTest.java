package edu.clarkson.cs.clientlib.ipinfo;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.clarkson.cs.clientlib.ipinfo.model.IPInfo;
import edu.clarkson.cs.common.BeanContext;

public class IPInfoAccessTest {

	private IPInfoAccess service;
	
	@Before
	public void init() {
		if(null == service) {
			new TestContextSet().apply();
		}
		service = BeanContext.get().get("ipInfoService");
	}
	
	@Test
	public void testQueryip() throws Exception {
		IPInfo info = service.queryip("128.153.23.185").execute().getResult();
		assertEquals("Potsdam",info.getCity());
		assertEquals("NA",info.getContinentCode());
		assertEquals("US",info.getCountryCode());
		assertEquals("USA",info.getCountryCode3());
		assertEquals("128.153.23.185",info.getIp());
		assertEquals("Clarkson University",info.getIsp());
		assertEquals("44.6609",info.getLatitude().toString());
		assertEquals("-74.9258",info.getLongitude().toString());
		assertEquals("Clarkson University",info.getOrganization());
	}

}
