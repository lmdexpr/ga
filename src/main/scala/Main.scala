package ga

import math._

import scalaz._, Scalaz._

import ga.model._
import ga.gene._
import ga.traits._

object Main extends App {
  //val system = akka.actor.ActorSystem("ga")

  val dim = 2

  def eval(g: Gene) = 10 * dim + g.chromosomes.map(v => v * v - 10 * cos(2*Pi*v)).sum

  val answer  = 0.0
  val epsilon = 1.0

  // Model((次元数, パラメータの最小値, 最大値), 子供の数, 親の数, 評価関数) 
  val first = Jgg(Array.fill(100)(BlxA(dim, -5.12, 5.12)), 100, 2, eval(_))

  Iterator.iterate(first)(_.next) map {
    model => {
      val eval_table = model.genes map eval
      (model.nth, eval_table.min, eval_table.sum / eval_table.size)
    }
  } takeWhile {
    case (_, best, _) => abs(best) > (answer + epsilon)
  } foreach {
    case (nth, best, avg) => {
      println(nth)
      println("best score: " + best.toString)
      println("average score: " + avg.toString)
      println()
    }
  }

  //system shutdown
}
