package edu.clarkson.cs.clientlib.caida.itdk;

import java.util.HashMap;
import java.util.Map;

import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class DummyJmsTemplate extends JmsTemplate {

	private Map<String, Integer> counter = new HashMap<String, Integer>();

	public DummyJmsTemplate() {

	}

	@Override
	public void send(String destinationName, MessageCreator messageCreator)
			throws JmsException {
		if (!counter.containsKey(destinationName)) {
			counter.put(destinationName, 0);
		}
		counter.put(destinationName, counter.get(destinationName) + 1);
	}

	public Map<String, Integer> getCounter() {
		return counter;
	}

	public void afterPropertiesSet() {

	}

}
