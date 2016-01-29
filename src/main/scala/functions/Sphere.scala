package ga.functions

import ga.traits._
import math._

case class Sphere(d: Int) extends Functions {
  val minimum = -5.12
  val maximum = 5.12

  val answer = 0.0

  def eval(g: Gene) = g.chromosomes.map(xi => (xi - d) * (xi - d)).sum
}
