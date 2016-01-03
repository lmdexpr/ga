package ga.gene
import ga.traits._, Types._

// https://www.jstage.jst.go.jp/article/tjsai/24/1/24_1_147/_pdf
case class Rex(chromosomes: Chromosome, minimum: Double, maximum: Double) extends Gene {
  import ga.util._
  import scala.math._

  def crossover(g: Seq[Gene]) = {
    // TODO

    Rex(this.chromosomes, minimum, maximum)
  }
}

object Rex extends GeneFactory {
  type Original = Rex
  def apply(n: Int, min: Double, max: Double) = new Rex(Seq.fill(n)(ga.util.Random.double(min)(max)), min, max)
}
