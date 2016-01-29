package ga.functions

import ga.traits._
import math._

case class Rosenbrock() extends Functions {
  val minimum = -2.048
  val maximum = 2.048

  val answer = 0.0

  def eval(g: Gene) = {
    val x1 = g.chromosomes.head
    val tl = g.chromosomes.tail
    tl.map(xi => 100 * (x1-xi) * (x1-xi) + (1-xi) * (1-xi)).sum
  }
}
