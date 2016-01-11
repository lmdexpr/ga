package ga

import math._

import scalaz._, Scalaz._

import ga.model._
import ga.gene._
import ga.traits._
import ga.target._

object Main extends App {
  //val system = akka.actor.ActorSystem("ga")

  val dim  = 10
  val npop = dim * 15
  val np   = dim + 1
  val nc   = dim * 10
  val f    = Sphere(1)
  val epsilon = 1E-7
  val limit   = 1E6

  // Model((集団サイズ)Xop(次元数, パラメータの最小値, 最大値), 子供の数, 親の数, 評価関数) 
  val first = ModelFactory.create[BlxA, MGG[BlxA]](dim, npop, nc, np, f)

  Iterator.iterate(first)(_.next) map {
    model => {
      val eval_table = model.genes map (f eval _)
      (model, eval_table.min, eval_table.sum / eval_table.size)
    }
  } takeWhile {
    case (model, best, avg) => (abs(best) > epsilon) && (model.nth <= limit)
  } foreach {
    case (model, best, avg) => {
      //println(model.nth.toString + " " + best.toString + " " + avg.toString)
      println(model.nth)
      println("best score: " + best.toString)
      println("average score: " + avg.toString)
      println()
    }
  }

  //system shutdown
}
