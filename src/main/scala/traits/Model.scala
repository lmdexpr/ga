package ga.traits

trait Model {
  def genes: Array[Gene]
  def nc: Int
  def np: Int
  def eval: Gene => Double
  def nth: Int

  def next: Model
}
