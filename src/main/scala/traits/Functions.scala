package ga.traits

trait Functions {
  val minimum: Double
  val maximum: Double

  val answer: Double

  def eval(g: Gene): Double
}
