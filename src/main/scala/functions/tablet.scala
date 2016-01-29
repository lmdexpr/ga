package ga.functions

import ga.traits._
import math._

case class Tablet() extends Functions {
  val minimum = -5.12
  val maximum = 5.12

  val answer = 0.0

  def eval(g: Gene) = {
    val k = g.chromosomes.size / 4
    val chromo = g.chromosomes.splitAt(k)
    chromo._1.map(xi => xi*xi).sum + chromo._2.map(xi => 10000 * xi*xi).sum
  }
}
