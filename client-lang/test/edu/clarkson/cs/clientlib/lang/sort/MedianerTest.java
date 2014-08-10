package edu.clarkson.cs.clientlib.lang.sort;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class MedianerTest {

	@Test
	public void testMedianListOfT() {
		List<BigDecimal> values = new ArrayList<BigDecimal>();

		values.add(new BigDecimal("123"));
		values.add(new BigDecimal("13"));
		values.add(new BigDecimal("13"));
		values.add(new BigDecimal("13"));
		values.add(new BigDecimal("32"));
		values.add(new BigDecimal("52"));
		values.add(new BigDecimal("24"));
		values.add(new BigDecimal("11"));
		values.add(new BigDecimal("0"));
		values.add(new BigDecimal("52"));
		assertEquals(new BigDecimal("13"),
				new Medianer<BigDecimal>().median(values));
	}

}
