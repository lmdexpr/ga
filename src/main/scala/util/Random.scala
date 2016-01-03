package ga.util
import ga.traits._

object Random {
  import scala.util.Random._

  def int(min: Int)(max: Int) = min + nextInt(max - min + 1)
  def nat = int(0)(_)
  def double(min: Double)(max: Double) = min + (nextDouble % (max - min + 1))
  def positive(max: Double) = double(0.0)(_)

  def uniqueIdx(min: Int, max: Int): Seq[Int] = shuffle(min to (max - 1))

  def pairInt(min:Int, max: Int): (Int,Int) = {
    val fst = int(min)(max)
    val snd = int(min)(max)

    if (fst != snd) (fst,snd) else pairInt(min, max)
  }
}
