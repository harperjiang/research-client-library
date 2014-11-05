package edu.clarkson.cs.common.test.message;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.jms.JmsException;
import org.springframework.jms.UncategorizedJmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class DummyJmsTemplate extends JmsTemplate {

	private Map<String, Queue<Message>> storage = new HashMap<String, Queue<Message>>();

	private DummyJmsSession session = new DummyJmsSession();

	public DummyJmsTemplate() {

	}

	@Override
	public void send(String destinationName, MessageCreator messageCreator)
			throws JmsException {
		if (!storage.containsKey(destinationName)) {
			storage.put(destinationName, new ConcurrentLinkedQueue<Message>());
		}
		try {
			storage.get(destinationName).add(
					messageCreator.createMessage(session));
		} catch (JMSException e) {
			throw new UncategorizedJmsException(e);
		}
	}

	public Map<String, Queue<Message>> getStorage() {
		return storage;
	}

	@Override
	public void afterPropertiesSet() {
	}
}
