package jsonbridge

import org.scalatest._

class MetricsParsingSpec extends FlatSpec with Matchers {
  "Resource" should "load from cp" in {
    val cls = getClass.getResourceAsStream("/sample.json")
    assert(cls != null)
  }

  "Metrics" should "parse" in {
    val text = io.Source.fromInputStream(getClass.getResourceAsStream("/sample.json")).mkString
    assert(text != null)
    val metrics = Metrics.from(text)
    println(metrics.toGraphiteFormat.mkString("\n"))
  }

}
