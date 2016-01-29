package ga.functions

import ga.traits._
import math._

case class Ellipsoid() extends Functions {
  val minimum = -5.12
  val maximum = 5.12

  val answer = 0.0

  def eval(g: Gene) = {
    val chromos = g.chromosomes
    val n       = chromos.size
    ((1 to n).toSeq zip chromos).map((x: (Int, Double)) => pow(pow(1000, (x._1 - 1)/(n - 1)) * x._2, 2.0)).sum
  }
}
