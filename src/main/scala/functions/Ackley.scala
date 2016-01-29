package ga.functions

import ga.traits._
import math._

case class Ackley() extends Functions {
  val minimum = -32.768
  val maximum = 32.768

  val answer = 0.0

  def eval(g: Gene) = {
    val chromos = g.chromosomes
    val n = chromos.size
    20 - 20*exp(-0.2 * sqrt(1/(n*chromos.map(x => x*x).sum))) + E - exp(1/(n*chromos.map(x => cos(2*Pi*x)).sum))
  }
}
