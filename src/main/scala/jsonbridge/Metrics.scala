package jsonbridge

import org.json4s._
import org.json4s.jackson.JsonMethods._
import java.util.Calendar
import collection.breakOut


object Metrics{
  implicit val formats = DefaultFormats
  def from(str: String): Metrics = {
    parse(str).extract[Metrics]
  }
  type EntryMap = Map[String,JValue]
  type GroupMap = Map[String,EntryMap]
}

import Metrics.{formats,EntryMap,GroupMap}

case class Metrics(
  version    : String,
  gauges     : GroupMap,
  counters   : GroupMap,
  histograms : GroupMap,
  meters     : GroupMap,
  timers     : GroupMap
) {
  val timestamp: Long = Calendar.getInstance().getTimeInMillis

  private def objGraphiteFormat(obj: JValue): String = obj match {
    case x: JString  => x.values
    case x: JArray   => x.extract[Array[String]].mkString("\"",",","\"")
    case x: JDouble  => x.values.toString
    case x: JDecimal => x.values.toString()
    case x: JInt     => x.values.toString()
    case x: JBool    => x.values.toString
    case x: JObject  => x.toString
  }


  private def entryMapGraphiteFormat(prefix: String, timestamp: Long, map: EntryMap): Vector[String] = {
    map.map{ case (k,v) => s"$prefix.$k ${objGraphiteFormat(v)} $timestamp" }(breakOut)
  }

  private def groupMapToGraphiteFormat(map: GroupMap): Vector[String] =
    map.flatMap{ case (k,v) => entryMapGraphiteFormat(k, timestamp, v) }(breakOut)

  def toGraphiteFormat: Vector[String] =
    groupMapToGraphiteFormat(gauges) ++
    groupMapToGraphiteFormat(counters) ++
    groupMapToGraphiteFormat(histograms) ++
    groupMapToGraphiteFormat(meters) ++
    groupMapToGraphiteFormat(timers)
}
