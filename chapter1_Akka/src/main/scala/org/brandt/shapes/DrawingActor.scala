package org.brandt.shapes

object Messages{
  object Exit
  object Finished
  case class Response(message: String)
}

import akka.actor.Actor
import org.brandt.shapes.Messages.Finished


class DrawingActor extends Actor{
  import org.brandt.shapes.Messages.{Exit, Response}

  override def receive: Receive = {
    case s: Shape =>
      s.draw(println)
      sender ! Response(s"drawn: $s")

    case Exit =>
      println(s"exiting...")
      sender ! Finished

    case unexpected =>
      val response = Response(s"ERROR: unknown message $unexpected")
      println(s"ShapesDrawingActor: $response")
      sender ! response
  }
}