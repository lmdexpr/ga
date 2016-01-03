package ga.gene
import ga.traits._, Types._

// https://www.jstage.jst.go.jp/article/tjsai/16/1/16_1_147/_pdf
case class SPXe(chromosomes: Chromosome, minimum: Double, maximum: Double) extends Gene {
  import ga.util._
  import scala.math._

  def crossover(g: Seq[Gene]) = {
    /*
    val epsilon = sqrt( g.size + 2 )
    val parents = (this +: g) map (_.chromosomes)
    val gravity = parents.transpose map (ch => ch.sum / ch.size)

    val x =
      Seq.tabulate(parents.size)(k =>
        Seq.tabulate(gravity.size)(i =>
            gravity(i) + epsilon * (parents(k)(i) - gravity(i))))

    def helper(i: Int): Chromosome =
      if (i <= 0) Seq(0.0)
      else { (x(i-1), x(i), c(i-1)).zipped.toSeq map (t => pow(Random.double(0)(1), 1.0/i) * (t._1 - t._2 + t._3)) }

    val c = Seq.tabulate(parents.size)(helper)
    */

    SPXe(chromosomes /* TODO */, minimum, maximum)
  }
}

object SPXe extends GeneFactory {
  type Original = SPXe
  def apply(n: Int, min: Double, max: Double) = new SPXe(Seq.fill(n)(ga.util.Random.double(min)(max)), min, max)
}
