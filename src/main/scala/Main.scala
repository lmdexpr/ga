package ga

import math._
import language.postfixOps
import concurrent.{Future, Await}
import concurrent.duration._

import akka.actor._
import akka.pattern._
import akka.util.Timeout

import scalaz._, Scalaz._

import ga.model._
import ga.gene._
import ga.traits._
import ga.functions._
import ga.island._

object Main extends App {
  implicit val system  = ActorSystem("ga")
  implicit val timeout = Timeout(10 seconds)

  val nIsland  = 5
  val interval = 5

  val dim  = 10
  val np   = dim + 1
  val nc   = dim * 10
  val npop = np * 15 / nIsland
  val f    = Sphere(1)
  //val f    = Ackley()
  //val f    = Rastrigin(1)
  //val f    = Rosenbrock()
  //val f    = Ellipsoid()
  //val f    = Tablet()

  val epsilon = 1E-7
  val limit   = 1E6

  type XOP = SPXe
  type GM  = JGG[XOP]

  val creator = () => ModelFactory.create[XOP, GM](dim, npop, nc, np, f)

  // [Xover operator, Generation Model] nIsland, interval, dimension, population, children, parents, target function
  val foundation = Seq.tabulate(nIsland) { (i: Int) => system.actorOf(Props(classOf[Island[XOP, GM]], creator), name = s"island-$i") }

  (Iterator.iterate(1) { _ + 1 } map { i =>
    if (i % interval == 0) {
      val shuffled = scala.util.Random.shuffle(foundation)
      shuffled.head ! Island.Protocol.Prepare(shuffled)
    }

    foundation map { (a: ActorRef) => Await.result(a ? Island.Protocol.Next, Duration.Inf).asInstanceOf[GM] }
  } map {
    models => {
      val data = models map {
        model => {
          val eval_table = model.genes map (f eval _)
          (model.nth, eval_table.min, eval_table.sum / eval_table.size)
        }
      }

      val best = data.map(_._2).min
      val avg  = data.map(_._3).sum / data.size

      (data(0)._1, best, avg, data)
    }
  } takeWhile {
    case (nth: Int, best: Double, avg: Double, _) => (abs(best - f.answer) > epsilon) && (nth <= limit) && (abs(best - avg) > epsilon)
  }) foreach {
    case (nth, best, avg, _) => println(s"$nth $best $avg")
  }

  system terminate
}
