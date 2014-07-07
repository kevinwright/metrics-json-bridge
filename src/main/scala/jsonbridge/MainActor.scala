package jsonbridge

import akka.actor.{Props, Actor}
import akka.io.IO
import spray.can.Http

case object Start

class MainActor extends Actor {
  def receive = {
    case Start =>
      import context.system
      val handler = context.actorOf(Props[HttpHandler], name = "httphandler")
      IO(Http) ! Http.Bind(handler, interface = "localhost", port = 9876)
  }
}
