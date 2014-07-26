package edu.clarkson.cs.clientlib.ipinfo.api;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.clarkson.cs.clientlib.ipinfo.model.IPInfo;

public class IPInfoServiceTest {

	private IPInfoService service = new IPInfoService();
	
	@Test
	public void testQueryip() throws Exception {
		IPInfo info = service.queryip("128.153.23.185").execute().getResult();
		assertEquals("",info.getCity());
		assertEquals("",info.getContinentCode());
		assertEquals("",info.getCountryCode());
		assertEquals("",info.getCountryCode3());
		assertEquals("",info.getIp());
		assertEquals("",info.getIsp());
		assertEquals("",info.getLatitude().toString());
		assertEquals("",info.getLongitude().toString());
		assertEquals("",info.getOrganization());
	}

}
