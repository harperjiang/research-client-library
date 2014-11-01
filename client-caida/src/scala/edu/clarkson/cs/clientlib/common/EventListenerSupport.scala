package edu.clarkson.cs.clientlib.common

import edu.clarkson.cs.clientlib.caida.itdk.scheduler.SchedulerListener
import scala.collection.mutable.ArrayBuffer
import java.util.EventListener

trait EventListenerSupport[T <: EventListener] {

  /**
   * Support to scheduler listeners
   */
  protected var listeners = new ArrayBuffer[T]();

  def addListener(l: T) = {
    listeners += l;
  }

  def removeListener(l: T) = {
    listeners -= l;
  }
}