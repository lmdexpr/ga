package ga.gene

import ga.traits._, Types._
import scalaz._, Scalaz._
import ga.util._
import scala.math._

case class BlxA(chromosomes: Chromosome, minimum: Double, maximum: Double) extends Gene {
  val alpha = 0.5

  def crossover(g: Gene) = {
    def generate(seed: (Double, Double)) = {
      val (lhs,rhs) = seed
      val extension = alpha * abs(lhs - rhs)
      Random.double(min(lhs, rhs) - extension)(max(lhs, rhs) + extension)
    }

    BlxA(this.chromosomes zip g.chromosomes map generate, minimum, maximum)
  }

  def crossover(g: Seq[Gene]): Gene = this crossover (scala.util.Random.shuffle(g).headOption | this)
}

object BlxA extends GeneFactory {
  type Original = BlxA
  def apply(n: Int, min: Double, max: Double) = new BlxA(Seq.fill(n)(ga.util.Random.double(min)(max)), min, max)
}
