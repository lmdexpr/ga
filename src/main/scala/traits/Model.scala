package ga.traits

import reflect.ClassTag
import reflect.runtime.{universe => ru}

trait Model[T <: Gene] {
  def genes: Array[T]
  def nc:   Int
  def np:   Int
  def eval: T => Double

  def nth: Int

  def next: Model[T]
}

object ModelFactory {
  def create[T <: Gene: ClassTag, S <: Model[T]](dim: Int, npop: Int, nc: Int, np: Int, func: Target)(implicit g_tag: ru.TypeTag[T], m_tag: ru.TypeTag[S]): S = {
    val g_con = ru.typeTag[T].mirror.reflectClass(ru.typeOf[T].typeSymbol.asClass).reflectConstructor(g_tag.tpe.member(ru.termNames.CONSTRUCTOR).asMethod)
    val min   = func.minimum
    val max   = func.maximum
    val genes = Array.fill(npop)(g_con(Seq.fill(dim)(ga.util.Random.double(min)(max)), min, max).asInstanceOf[T])

    val m_con = ru.typeTag[S].mirror.reflectClass(ru.typeOf[S].typeSymbol.asClass).reflectConstructor(m_tag.tpe.member(ru.termNames.CONSTRUCTOR).asMethod)
    m_con(genes, nc, np, (x: T) => func.eval(x), 0).asInstanceOf[S]
  }
}
