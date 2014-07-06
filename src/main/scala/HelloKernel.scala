package sample.kernel.hello
 
import akka.actor.{ Actor, ActorSystem, Props }
import akka.kernel.Bootable
 
case object Start
 
class HelloActor extends Actor {
  val worldActor = context.actorOf(Props[WorldActor])
 
  def receive = {
    case Start => worldActor ! "Hello"
    case message: String =>
      println("Received message '%s'" format message)
  }
}
 
class WorldActor extends Actor {
  def receive = {
    case message: String => sender() ! (message.toUpperCase + " world!")
  }
}
 
class HelloKernel extends Bootable {
  val system = ActorSystem("hellokernel")
 
  def startup = {
    system.actorOf(Props[HelloActor]) ! Start
  }
 
  def shutdown = {
    system.shutdown()
  }
}

object MainRun extends scala.App {
  akka.kernel.Main.main(Array("sample.kernel.hello.HelloKernel"))
}