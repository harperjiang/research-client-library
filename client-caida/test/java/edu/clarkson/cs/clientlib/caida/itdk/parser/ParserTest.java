package edu.clarkson.cs.clientlib.caida.itdk.parser;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.clarkson.cs.clientlib.caida.itdk.model.Link;
import edu.clarkson.cs.clientlib.caida.itdk.model.Node;
import edu.clarkson.cs.clientlib.caida.itdk.model.NodeLink;

public class ParserTest {

	@Test
	public void testParseNode() {
		Parser parser = new Parser();
		Node node = parser.parse("node N13: 123.323.232.133 123.313.144.131");
		assertEquals(13, node.id());
		assertEquals(2, node.ips().size());
		assertTrue(node.ips().contains("123.323.232.133"));
		assertTrue(node.ips().contains("123.313.144.131"));
	}

	@Test
	public void testParseLink() {
		Parser parser = new Parser();
		Link link = parser
				.parse("link L174: N13:13.42.23.23 N25:323.223.24.14 N16 N71");
		assertEquals(174, link.id());
		assertEquals(4, link.nodeSize());
		assertEquals(13, link.namedNodes().get("13.42.23.23").get());
		assertEquals(25, link.namedNodes().get("323.223.24.14").get());
		assertTrue(Integer.valueOf(16).equals(link.anonymousNodes().apply(0))
				|| Integer.valueOf(71).equals(link.anonymousNodes().apply(0)));
	}

	@Test
	public void testParseNodeLink() {
		Parser parser = new Parser();
		NodeLink nl = parser.parse("nodelink N134:32.23.12.44 : L123");
		assertEquals(123, nl.link());
		assertEquals(134, nl.node());
		assertEquals("32.23.12.44", nl.ip());

		nl = parser.parse("nodelink N12 : L22");
		assertEquals(12, nl.node());
		assertEquals(22, nl.link());
	}
}
