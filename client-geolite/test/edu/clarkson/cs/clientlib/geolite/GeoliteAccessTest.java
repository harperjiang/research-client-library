package edu.clarkson.cs.clientlib.geolite;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.clarkson.cs.common.BeanContext;

public class GeoliteAccessTest {

	private GeoliteAccess access;

	@Before
	public void init() {
		if (access == null) {
			new GeoliteContextSet().apply();
			access = BeanContext.get().get("geoAccess");
		}
	}

	@Test
	public void testGetLocation() {
		String ip = "65.121.84.232";
		assertEquals("WA", access.getLocation(ip).getState());
		assertEquals("Vancouver", access.getLocation(ip).getCity());
	}

}
