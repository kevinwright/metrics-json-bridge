package jsonbridge

import akka.actor.{Props, ActorSystem}

object Main extends App {
  implicit val system = ActorSystem()
  val lifecycle = new Lifecycle()
  lifecycle.startup()
}

class Lifecycle(implicit system: ActorSystem) {
  def startup(): Unit = {
    system.actorOf(Props[MainActor]) ! Start
  }

  def shutdown(): Unit = {
    system.shutdown()
  }
}
