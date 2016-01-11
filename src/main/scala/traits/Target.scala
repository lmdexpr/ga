package ga.traits

trait Target {
  val minimum: Double
  val maximum: Double

  def eval(g: Gene): Double
}
