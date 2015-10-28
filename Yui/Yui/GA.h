#pragma once

#include "Gene.h"
#include <vector>
#include <utility>
#include <string>
#include <functional>

namespace yui {
  using namespace std;

  // using REX, no mutation
  // using Just Generation Gap Model
  class GA
  {
  public:
    GA(int, int, double, double, double);
    GA(vector<Gene>, int, double);

    const int get_individuals();
    const vector<Gene> & get_genes();
    string show();

    void step(function<int(Gene)>);

  private:
    static random_device rnd;

    const int DIM;
    const int CROSSING_GENES;
    const int PARENTS;
    const int INDIVIDUALS;

    vector<Gene> genes;

    const vector<int> & copy_selection(int);
    Gene crossover(vector<Gene> &, const vector<double> &);
    void survival_selection(vector<int> &, vector<Gene> &, function<int(Gene)>);
  };
}
