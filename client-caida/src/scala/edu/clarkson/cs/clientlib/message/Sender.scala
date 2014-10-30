package edu.clarkson.cs.clientlib.message

import org.springframework.jms.core.JmsTemplate
import org.springframework.jms.core.MessageCreator
import javax.jms.Session
import javax.jms.Message
import edu.clarkson.cs.clientlib.caida.itdk.marshall.Marshaller

trait Sender {

  var jmsTemplate: JmsTemplate = null;

  def send(dest: String, message: Object)(implicit post: (Message) => Unit = (m => {})) = {
    jmsTemplate.send(dest, new MessageCreator() {
      def createMessage(session: Session): Message = {
        var tm = session.createTextMessage(Marshaller.marshall(message));
        post(tm);
        return tm;
      }
    })
  }
}
