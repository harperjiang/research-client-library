package edu.clarkson.cs.common.proc;

import java.util.ArrayList;
import java.util.List;

public class DefaultOutputHandler implements OutputHandler {

	List<String> content;
	
	public DefaultOutputHandler() {
		super();
		content = new ArrayList<String>();
	}
	
	@Override
	public void output(String input) {
		content.add(input);
	}
	
	public List<String> getContent() {
		return content;
	}

}
