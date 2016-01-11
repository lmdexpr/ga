package ga.model

import ga.traits._
import ga.util._

import scalaz._, Scalaz._

case class JGG[T <: Gene](genes: Array[T], nc: Int, np: Int, eval: T => Double, nth: Int = 0) extends Model[T] {

  def next = {
    val parents  = Random.uniqueIdx(0, genes.length) take np
    val children = Seq.fill(nc)(genes(parents.head) * parents.tail.map(genes(_))) map (_.asInstanceOf[T])

    val newGeneration: Seq[T] =
      children map ((idv: T) => (eval(idv), idv)) sortWith(_._1 < _._1) take np map (_._2)

    parents zip newGeneration foreach { case (p, c) => genes(p) = c }

    JGG(genes, nc, np, eval, nth = nth+1)
  }

}
