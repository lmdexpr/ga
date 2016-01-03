package ga.gene
import ga.traits._, Types._

// https://www.jstage.jst.go.jp/article/sicetr1965/36/10/36_10_875/_pdf
case class UNDXe(chromosomes: Chromosome, minimum: Double, maximum: Double) extends Gene {
  import ga.util._
  import scala.math._

  val alpha = 1.0
  val beta  = 0.35

  def crossover(g: Seq[Gene]) = {

    // TODO

    UNDXe(this.chromosomes, minimum, maximum)
  }
}

object UNDXe extends GeneFactory {
  type Original = UNDXe
  def apply(n: Int, min: Double, max: Double) = new UNDXe(Seq.fill(n)(ga.util.Random.double(min)(max)), min, max)
}
