#pragma once

#include <vector>
#include <random>
#include <string>
#include <limits>

namespace yui {
  using namespace std;

  class Gene
  {
  public:
    Gene(int, double, double);
    Gene(vector<double>, double, double);

    void initialize(int n);
    const string show();
    const vector<double> & get_chromosomes();
    const double get_min();
    const double get_max();
    const int size();

  private:
    static random_device rnd;

    vector<double> chromosomes;

    double chromo_min;
    double chromo_max;
  };
}
