package ga.model
import ga.traits._
import ga.util._
import scalaz._, Scalaz._

case class Mgg(genes: Array[Gene], nc: Int, np: Int, eval: Gene => Double, nth: Int = 0) extends Model {

  def roulette(seed: (Double, Gene), sorted_evaled: Seq[(Double, Gene)]): Gene = {
    val table = sorted_evaled.scanLeft(seed)((l, r) => (1.0 / l._1 + 1.0 / r._1, r._2))

    (table.find(_._1 < Random.double(seed._1)(table.last._1)) getOrElse sorted_evaled.last)._2
  }

  def next = {
    val parents  = Random.uniqueIdx(0, genes.length) take np
    val p1 = parents.head
    val p2 = parents.tail.head

    val children = Seq.fill(nc)(genes(p1) * parents.tail.map(genes(_)))

    val (best :: newGeneration): Seq[(Double, Gene)] =
      children :+ genes(p1) :+ genes(p2) map (idv => (eval(idv), idv)) sortWith(_._1 < _._1)

    genes(p1) = best._2
    genes(p2) = roulette(newGeneration.head, newGeneration.tail)

    Mgg(genes, nc, np, eval, nth = nth+1)
  }

}
