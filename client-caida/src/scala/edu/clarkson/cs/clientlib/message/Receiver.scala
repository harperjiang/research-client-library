package edu.clarkson.cs.clientlib.message

import java.util.EventListener
import edu.clarkson.cs.clientlib.lang.EventListenerSupport

trait Receiver {

  def bind(port: InPort, method: String);
}

trait InPort extends EventListenerSupport[PortListener] {

}

trait PortListener extends EventListener {

  def messageReceived(message: Object);
}

