package ga.traits

trait Gene {
  def chromosomes: Types.Chromosome
  def minimum: Double
  def maximum: Double

  def crossover(g: Seq[Gene]): Gene
  def * = crossover(_)
}
