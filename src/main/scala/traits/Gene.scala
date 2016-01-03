package ga.traits

trait Gene { self =>
  def chromosomes: Types.Chromosome
  def minimum: Double
  def maximum: Double

  //def crossover(g: Seq[self.type]): self.type
  def crossover(g: Seq[Gene]): Gene
  def * = crossover(_)
}

trait GeneFactory {
  type Original
  def apply(n: Int, min: Double, max: Double): Original
}
