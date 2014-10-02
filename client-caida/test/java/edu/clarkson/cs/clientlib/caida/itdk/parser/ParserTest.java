package edu.clarkson.cs.clientlib.caida.itdk.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.clarkson.cs.clientlib.caida.itdk.model.Link;
import edu.clarkson.cs.clientlib.caida.itdk.model.Node;
import edu.clarkson.cs.clientlib.caida.itdk.model.NodeLink;

public class ParserTest {

	@Test
	public void testParseNode() {
		Parser parser = new Parser();
		Node node = parser.parse("node N13: 123.323.232.133 123.313.144.131");
		assertEquals(13,node.id());
		assertEquals(2, node.ips().length());
		assertEquals("123.323.232.133",node.ips().apply(0));
		assertEquals("123.313.144.131",node.ips().apply(1));
	}

	@Test
	public void testParseLink() {
		Parser parser = new Parser();
		Link link = parser.parse("link L174: N13:13.42.23.23 N25:323.223.24.14 N16 N71");
		assertEquals(174,link.id());
		assertEquals(4,link.nodes().length());
		assertEquals(13,link.nodes().apply(0).node());
		assertEquals("13.42.23.23",link.nodes().apply(0).ip().get());
		assertEquals(25,link.nodes().apply(1).node());
		assertEquals("323.223.24.14",link.nodes().apply(1).ip().get());
		assertEquals(16,link.nodes().apply(2).node());
		assertEquals(71,link.nodes().apply(3).node());
	}
	
	@Test
	public void testParseNodeLink() {
		Parser parser = new Parser();
		NodeLink nl = parser.parse("nodelink N134:32.23.12.44 : L123");
		assertEquals(123,nl.link());
		assertEquals(134,nl.nodeRef().node());
		assertEquals("32.23.12.44",nl.nodeRef().ip().get());
		
		nl = parser.parse("nodelink N12 : L22");
		assertEquals(12,nl.nodeRef().node());
		assertEquals(22,nl.link());
	}
}
