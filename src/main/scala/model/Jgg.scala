package ga.model
import ga.traits._
import ga.util._
import scalaz._, Scalaz._

case class Jgg(genes: Array[Gene], nc: Int, np: Int, eval: Gene => Double, nth: Int = 0) extends Model {

  def next = {
    val parents  = Random.uniqueIdx(0, genes.length) take np
    val children = Seq.fill(nc)(genes(parents.head) * parents.tail.map(genes(_)))

    val newGeneration: Seq[Gene] =
      children map (idv => (eval(idv), idv)) sortWith(_._1 < _._1) take np map (_._2)

    parents zip newGeneration foreach { case (p, c) => genes(p) = c }

    Jgg(genes, nc, np, eval, nth = nth+1)
  }

}
