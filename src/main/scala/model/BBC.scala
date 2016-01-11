package ga.model

import ga.traits._
import ga.util._

import scalaz._, Scalaz._

case class BBC[T <: Gene](genes: Array[T], nc: Int, np: Int, eval: T => Double, nth: Int = 0) extends Model[T] {

  def next = {
    val parents  = Random.uniqueIdx(0, genes.length) take np
    val p1 = parents.head
    val p2 = parents.tail.head

    val children = Seq.fill(nc)(genes(p1) * parents.tail.map(genes(_))) map (_.asInstanceOf[T])

    val (best1 :: best2 :: _): Seq[T] =
      children :+ genes(p1) :+ genes(p2) map ((idv: T) => (eval(idv), idv)) sortWith(_._1 < _._1) map (_._2)

    genes(p1) = best1
    genes(p2) = best2

    BBC(genes, nc, np, eval, nth = nth+1)
  }

}
