package org.brandt.shapes

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.event.Logging
import com.typesafe.config.ConfigFactory
import org.brandt.shapes.Messages.{Exit, Finished, Response}

private object Start

object MainApplication {

  def main(args: Array[String]): Unit = {
    val system = ActorSystem("DrawingActor", ConfigFactory.load())
    val drawer = system.actorOf(Props(new DrawingActor),"drawingActor")
    val driver = system.actorOf(Props(new MainApplication(drawer)), "drawingService")
    driver ! Start
  }
}


class MainApplication(drawerActor: ActorRef) extends Actor{
  val log = Logging(context.system, this)

  override def receive: Receive = {
    case Start =>
      log.info(s"RECEIVED $Start")
      drawerActor ! Circle(Point(2.0,2.0),3.0)
      drawerActor ! Circle(Point(3.0,2.0),3.0)
      drawerActor ! Circle(Point(4.0,2.0),3.0)
      drawerActor ! Circle(Point(5.0,2.0),3.0)
      drawerActor ! Exit
    case Finished =>
      println("cleaning up...")
      context.system.terminate()
    case response: Response =>
      println(s"ActorResponse: $response")
    case unexpected =>
      println(s"ERROR: $unexpected")

  }
}
