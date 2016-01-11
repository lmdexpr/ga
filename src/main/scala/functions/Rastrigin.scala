package ga.functions

import ga.traits._
import math._

case class Rastrigin(d: Int) extends Functions {
  val minimum = -5.12
  val maximum = 5.12

  def eval(g: Gene) = {
    val chromos = g.chromosomes
    val n       = chromos.size
    10*n + chromos.map(xi => (xi-d) * (xi-d) - 10 * cos(2*Pi*(xi-d))).sum
  }
}
