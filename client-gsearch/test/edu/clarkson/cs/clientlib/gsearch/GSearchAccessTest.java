package edu.clarkson.cs.clientlib.gsearch;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.clarkson.cs.clientlib.gsearch.model.SearchResponse;
import edu.clarkson.cs.clientlib.lang.BeanContext;

public class GSearchAccessTest {

	private GSearchAccess access;

	@BeforeClass
	public static void init() {
		new GSearchContextSet().apply();
	}

	@Before
	public void setup() {
		access = BeanContext.get().get("gsearchAccess");
	}

	@Test
	public void testSearch() throws ClientProtocolException, IOException {
		SearchResponse resp = access.search("good").execute().getResult();
		assertNotNull(resp);
		assertTrue(resp.getResults().size() > 1);
	}

}
