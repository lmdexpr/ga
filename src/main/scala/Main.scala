package ga

import math._

import scalaz._, Scalaz._

import blxA_mgg._
//import rex_jgg._

object Main extends App {
  //val system = akka.actor.ActorSystem("ga")

  val generation_count = 150000 + 1
  val chromosome_count = 3

  def eval(g: Gene) = 10 * chromosome_count + g.chromosomes.map(v => v * v - 10 * cos(2*Pi*v)).sum

  // Model(1世代の遺伝子数, 染色体数, 子供の数, パラメータの最小値, 最大値, 評価関数) 
  val first: \/[String, Model] = Model(100, chromosome_count, 100, -5.12, 5.12, eval).right

  Iterator.iterate(first) {_ >>= (_.next)} take generation_count foreach {
    for (model <- _) {
      val eval_table = model.genes map eval
      val avg = eval_table.sum / eval_table.size
      println(model.n_th)
      println("best score: " + eval_table.min.toString)
      println("average score: " + avg.toString)
      println()
    }
  }

  //system shutdown
}
