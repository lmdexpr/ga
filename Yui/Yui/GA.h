#pragma once

#include "Gene.h"
#include <vector>
#include <utility>
#include <string>
#include <functional>

namespace yui {
  using namespace std;

  // using Minimal Generation Gap Model
  class GA
  {
  public:
    GA(int, int, int, double, double);
    GA(vector<Gene>, int);

    void step(function<int(Gene)>);
    string show();

    const vector<Gene>& get_genes();

  private:
    static random_device rnd;
    const int CROSSING_GENES;

    vector<Gene> genes;

    pair<int, int> copy_selection();
    int roulette(vector<double> &);
    void survival_selection(pair<int,int>, vector<Gene>, function<double(Gene)>);
  };
}