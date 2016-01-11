package ga.model

import ga.traits._
import ga.util._

import scalaz._, Scalaz._

case class MGG[T <: Gene](genes: Array[T], nc: Int, np: Int, eval: T => Double, nth: Int = 0) extends Model[T] {

  def roulette(seed: (Double, T), sorted_evaled: Seq[(Double, T)]): T = {
    val table = sorted_evaled.scanLeft(seed)((l, r) => (1.0 / l._1 + 1.0 / r._1, r._2))

    (table.find(_._1 < Random.double(seed._1)(table.last._1)) getOrElse sorted_evaled.last)._2
  }

  def next = {
    val parents  = Random.uniqueIdx(0, genes.length) take np
    val p1 = parents.head
    val p2 = parents.tail.head

    val children = Seq.fill(nc)(genes(parents.head) * parents.tail.map(genes(_))) map (_.asInstanceOf[T])

    val (best :: newGeneration): Seq[(Double, T)] =
      children :+ genes(p1) :+ genes(p2) map ((idv: T) => (eval(idv), idv)) sortWith(_._1 < _._1)

    genes(p1) = best._2
    genes(p2) = roulette(newGeneration.head, newGeneration.tail)

    MGG(genes, nc, np, eval, nth = nth+1)
  }

}
