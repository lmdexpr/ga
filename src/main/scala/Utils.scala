package ga

object Utils {
  object Random {
    import scala.util.Random._

    def int(min: Int)(max: Int) = min + nextInt(max - min + 1)
    def nat = int(0)(_)
    def double(min: Double)(max: Double) = min + (nextDouble % (max - min + 1))
    def positive(max: Double) = double(0.0)(_)

    def pairInt(min:Int, max: Int) = {
      val fst = int(min)(max)
      val snd = int(min)(max)

      if (fst != snd) (fst,snd) else Pair(min, max)
    }
  }
}
