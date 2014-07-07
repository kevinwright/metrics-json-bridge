package jsonbridge

import org.json4s._
import org.json4s.jackson.JsonMethods._


object Metrics{
  implicit val formats = DefaultFormats
  def from(str: String): Metrics = parse(str).extract[Metrics]
}

case class Metrics(
  version    : String,
  gauges     : Vector[Gauge],
  counters   : Vector[String],
  histograms : Vector[String],
  meters     : Vector[Meter],
  timers     : Vector[Timer]
)

case class Gauge(value: String)

case class Meter(
  count     : String,
  m15_rate  : String,
  m1_rate   : String,
  m5_rate   : String,
  mean_rate : String,
  units     : String
)

case class Timer(
  count          : String,
  max            : String,
  mean           : String,
  min            : String,
  p50            : String,
  p75            : String,
  p95            : String,
  p98            : String,
  p99            : String,
  p999           : String,
  stddev         : String,
  m15_rate       : String,
  m1_rate        : String,
  m5_rate        : String,
  mean_rate      : String,
  duration_units : String,
  rate_units     : String
)