package edu.clarkson.cs.clientlib.caida.itdk.parser;

import java.io.StringReader;

import java_cup.runtime.Symbol;

import org.junit.Test;

public class NodeLexerTest {

	@Test
	public void testNext_token() throws Exception {
		NodeLexer lexer = new NodeLexer(new StringReader("node N13: 123.323.232.433 123.313.144.131"));
		while(true) {
			Symbol token = lexer.next_token();
			System.out.println(token.sym);
			if(token.sym == Syms.EOF)
				break;
			
		}
	}

}
