package ga.blxA_mgg

case class Model(genes: Array[Gene], n_th: Int, crossing_genes: Int, eval: Gene => Double) {
  import ga.Utils._
  import scalaz._, Scalaz._

  val min = genes.head.minimum
  val max = genes.head.maximum

  def this(n: Int, m: Int, cg: Int, min: Double, max: Double, eval: Gene => Double) =
    this(Array.fill(n)(Gene(m, min, max)), 0, cg, eval)

  def roulette(seed: (Double, Gene), sorted_evaled: Seq[(Double, Gene)]): Gene = {
    val table = sorted_evaled.scanLeft(seed)((l, r) => (1.0 / l._1 + 1.0 / r._1, r._2))

    (table.find(_._1 < Random.double(seed._1)(table.last._1)) getOrElse sorted_evaled.last)._2
  }

  def next(): \/[String, Model] = {
    val parents  = Random.pairInt(0, genes.length - 1)
    val children = Seq.fill(crossing_genes)(genes(parents._1) * genes(parents._2))

    val newGeneration: Seq[(Double, Gene)] =
      children :+ genes(parents._1) :+ genes(parents._2) map (idv => (eval(idv), idv)) sortWith(_._1 < _._1)

    newGeneration match {
      case (best :: hd :: tl) =>
        genes(parents._1) = best._2
        genes(parents._2) = roulette(hd, tl)
        Model(genes, n_th + 1, crossing_genes, eval).right
      case _ =>
        "!!!failed!!! Too few CROSSING_GENES parameter.".left
    }
  }
}

object Model {
  def apply(n: Int, m: Int, cg: Int, min: Double, max: Double, eval: Gene => Double) =
    new Model(n, m, cg, min, max, eval)
}
