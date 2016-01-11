package ga.traits

trait Functions {
  val minimum: Double
  val maximum: Double

  def eval(g: Gene): Double
}
