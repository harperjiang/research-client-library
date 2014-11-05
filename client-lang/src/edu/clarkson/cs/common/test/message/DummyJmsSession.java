package edu.clarkson.cs.common.test.message;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQQueueSession;
import org.apache.activemq.command.ActiveMQTextMessage;

public class DummyJmsSession extends ActiveMQQueueSession {

	public DummyJmsSession() {
		super(null);
	}

	@Override
	public TextMessage createTextMessage(String text) throws JMSException {
		TextMessage tm = new ActiveMQTextMessage();
		tm.setText(text);
		return tm;
	}
}
