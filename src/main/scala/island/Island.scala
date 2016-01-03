package ga.island

import ga.traits._
import akka.actor.Actor
import scalaz._, Scalaz._

/*
 * TODO

sealed trait Message
case class Next() extends Message
case class Migration(islands: Seq[Island]) extends Message
case class Syn(g:Gene) extends Message
case class Ack(g:Gene) extends Message
case class Fin() extends Message

case class Island(var model: \/[String, Model[Gene]], rate: Double, interval: Int) extends Actor {
  import ga.util._
  import scala.util.Random.shuffle

  def recieve = {
    case Next => {
      model.next match {
        case \/-(m) => model = m
      }
    }

    case Migration(islands) => {
      val i      = Random.nat(first.genes.size - 1)
      val abroad = shuffle(islands).head
      val ack    = abroad ? Syn(model.genes(i))
      ack.onSuccess {
        case reply => {
          model.genes(i) = reply
          abroad ! Fin
        }
      }
    }

    case Syn(g) => {
      val i   = Random.nat(model.genes.size - 1)
      val fin = sender ? model.genes(i)

      fin.onSuccess {
        case Fin => model.genes(i) = g
      }
    }
  }
}
*/
