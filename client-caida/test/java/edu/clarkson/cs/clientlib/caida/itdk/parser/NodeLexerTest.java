package edu.clarkson.cs.clientlib.caida.itdk.parser;

import java.io.StringReader;

import java_cup.runtime.Symbol;

import org.junit.Test;

public class NodeLexerTest {

	@Test
	public void testNextTokenOnNode() throws Exception {
		Lexer lexer = new Lexer(new StringReader("node N13: 123.323.232.433 123.313.144.131"));
		while(true) {
			Symbol token = lexer.next_token();
			System.out.println(token.sym);
			if(token.sym == Syms.EOF)
				break;
			
		}
	}

	@Test
	public void testNextTokenOnLink() throws Exception {
		Lexer lexer = new Lexer(new StringReader("link L13: N123:123.323.232.433 N134:123.313.144.131 N22 N71"));
		while(true) {
			Symbol token = lexer.next_token();
			System.out.println(token.sym);
			if(token.sym == Syms.EOF)
				break;
			
		}
	}

}
