#pragma once

#include <vector>
#include <random>
#include <string>
#include <limits>

namespace yui {
  using namespace std;

  // using BLX-alpha, no mutation
  class Gene
  {
  public:
    Gene(int, double, double);
    Gene(vector<double>, double, double);

    void initialize(int n);

    const Gene operator*(const Gene &) const;
    const Gene crossover(const Gene &) const;
    // Gene mutation();

    const string show();

    const vector<double>& get_chromosomes();

  private:
    Gene();

    vector<double> chromosomes;
    static random_device rnd;

    double chromo_min;
    double chromo_max;

    double ALPHA = 0.25;
  };
}
