package ga.island

import util.Random.shuffle

import ga.traits._
import ga.util._

import scalaz._, Scalaz._

import akka.actor._
import scalaz._, Scalaz._

class Island[XOP <: Gene, GM <: Model[XOP]](creator: () => GM) extends Actor with Stash {
  import Island.Protocol._

  var population = creator ()

  def migrate(i: Int, gene: XOP, actors: Seq[ActorRef]) = {
    population.genes(i) = gene
    Migration(population.genes(Random.nat(population.genes.size - 1)), actors)
  }

  def receive = {
    case Next => {
      population = population.next.asInstanceOf[GM]
      sender ! population
    }

    case Prepare(actors: Seq[ActorRef]) => {
      val i = Random.nat(population.genes.size - 1)
      val self  = actors.head
      val queue = actors.tail
      (queue.headOption | self) ! Migration(population.genes(i), (queue.isEmpty? queue | queue.tail) :+ self)
      context become waitMigration(sender, i)
    }

    case Migration(gene: XOP, actors: Seq[ActorRef]) => {
      val i = population.genes.zipWithIndex.map((x: (XOP, Int)) => (population.eval(x._1), x._2)).max._2
      actors.head ! migrate(i, gene, actors.tail)
    }
  }

  def waitMigration(master: ActorRef, i: Int): Receive = {
    case Migration(gene: XOP, _) => {
      population.genes(i) = gene

      context.unbecome()
      unstashAll()
    }

    case _ => stash ()
  }
}

object Island {
  object Protocol {
    sealed trait Message
    case object Next extends Message
    case class Prepare(actors: Seq[ActorRef]) extends Message
    case class Migration[XOP](gene: XOP, actors: Seq[ActorRef]) extends Message
  }
}
