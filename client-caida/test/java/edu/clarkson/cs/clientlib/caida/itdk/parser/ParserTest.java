package edu.clarkson.cs.clientlib.caida.itdk.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.clarkson.cs.clientlib.caida.itdk.model.Node;

public class ParserTest {

	@Test
	public void testParseNode() {
		Parser parser = new Parser();
		Node node = parser.parseNode("node N13: 123.323.232.133 123.313.144.131");
		assertEquals(13,node.id());
		assertEquals(2, node.ips().length());
		assertEquals("123.323.232.133",node.ips().apply(0));
		assertEquals("123.313.144.131",node.ips().apply(1));
	}

}
