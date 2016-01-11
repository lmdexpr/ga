package ga.gene
import ga.traits._, Types._

// https://www.jstage.jst.go.jp/article/tjsai/24/1/24_1_147/_pdf
case class Rex(chromosomes: Chromosome, minimum: Double, maximum: Double) extends Gene {
  import ga.util._
  import scala.math._

  def crossover(g: Seq[Gene]) = {
    /*require(g.size >= chromosomes.size, "REX required (n+k) parents")
    val parents = (this +: g) map (_.chromosomes)
    val gravity = parents.transpose map (x => x.sum/x.size)

    lazy val x =
      Seq.tabulate(parents.size)(k =>
        Seq.tabulate(gravity.size)(i =>
          gravity(i) + epsilon * (parents(k)(i) - gravity(i))))

    def c(i: Int): Chromosome =
      if (i == 0) Seq.fill(gravity.size)(0.0)
      else { (x(i-1), x(i), c(i-1)).zipped.toSeq map (t => pow(Random.double(0)(1), 1.0/i) * (t._1 - t._2 + t._3)) }

    Rex(x(gravity.size) zip c(gravity.size) map {case (xi, ci) => xi + ci}, minimum, maximum)
    */
    Rex(chromosomes, minimum, maximum)
  }
}
