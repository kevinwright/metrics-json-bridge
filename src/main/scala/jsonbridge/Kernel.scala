package jsonbridge

import akka.kernel.Bootable
import akka.actor.{Props, ActorSystem}


class Kernel extends Bootable {
  implicit val system = ActorSystem("jsonbridge")
  val lifecycle = new Lifecycle()

  def startup: Unit = lifecycle.startup()

  def shutdown: Unit = lifecycle.shutdown()
}
