package ga.blxA_mgg

case class Gene(chromosomes: Seq[Double], minimum: Double, maximum: Double) {
  import ga.Utils._
  import scala.math._

  val ALPHA = 0.5

  def this(n: Int, min: Double, max: Double) = this(Seq.fill(n)(ga.Utils.Random.double(min)(max)), min, max)

  def crossover(g: Gene) = {
    def ad(l:Double, r:Double) = ALPHA * abs(l - r)
    def generate(seed: (Double, Double)) = {
      val (lhs, rhs) = seed
      val tmp = ad(lhs, rhs)
      Random.double(min(lhs, rhs) - tmp)(max(lhs, rhs) + tmp)
    }

    Gene(this.chromosomes zip g.chromosomes map generate, minimum, maximum)
  }

  def * = crossover(_)
}

object Gene {
  def apply(n: Int, min: Double, max: Double) = new Gene(n, min, max)
}
