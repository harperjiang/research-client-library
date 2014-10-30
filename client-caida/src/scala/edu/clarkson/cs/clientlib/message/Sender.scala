package edu.clarkson.cs.clientlib.message

import java.util.HashMap
import org.springframework.jms.core.JmsTemplate

trait Sender {

  var jmsTemplate: JmsTemplate = null;
}
