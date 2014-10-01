package edu.clarkson.cs.clientlib.caida.itdk.parser;

import java.io.StringReader;

import edu.clarkson.cs.clientlib.caida.itdk.model.Node;

public class Parser {

	public Node parseNode(String input) {
		NodeLexer scanner = new NodeLexer(new StringReader(input));
		NodeParser parser = new NodeParser(scanner);
		try {
			return (Node)parser.parse().value;
		} catch (Exception e) {
			return null;
		}
	}
}
