package jsonbridge

import akka.actor.Actor

case object Start

class MainActor extends Actor {
  def receive = {
    case Start => println("Hello World!")
  }
}
