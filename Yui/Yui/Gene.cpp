#include "Gene.h"
#include <algorithm>
#include <random>
#include <sstream>

namespace yui {
  random_device Gene::rnd;

  Gene::Gene() {
    // have nothing to do
  }

  Gene::Gene(int n, double min, double max)
    : chromo_min(min)
    , chromo_max(max)
  {
    initialize(n);
  }

  Gene::Gene(vector<double> v, double min, double max)
    : chromosomes(v)
    , chromo_min(min)
    , chromo_max(max)
  {}

  void Gene::initialize(int n) {
    mt19937_64 mt(rnd());
    uniform_real_distribution<> urd(chromo_min, chromo_max);

    for (int i = 0; i < n; ++i)
      chromosomes.push_back(urd(mt));
  }

  const Gene Gene::operator*(const Gene &g) const {
    return crossover(g);
  }

  const Gene Gene::crossover(const Gene &g) const {
    int gene_size = g.chromosomes.size();
    vector<double> tmp;
    mt19937_64 mt(rnd());

    for (int i = 0; i < gene_size; ++i) {
      double lhs = chromosomes.at(i), rhs = g.chromosomes.at(i);
      double ad = ALPHA * abs(lhs - rhs);

      uniform_real_distribution<> urd(min(lhs, rhs) - ad, max(lhs, rhs) + ad);

      tmp.push_back(urd(mt));
    }

    return Gene(tmp, g.chromo_min, g.chromo_max);
  }

  const string Gene::show() {
    stringstream ss;

    for (double c : chromosomes)
      ss << c << ' ';

    return ss.str();
  }

  const vector<double>& Gene::get_chromosomes() {
    return chromosomes;
  }
}