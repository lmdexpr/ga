#include "Gene.h"
#include <algorithm>
#include <random>
#include <sstream>

namespace yui {
  random_device Gene::rnd;

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

  const vector<double> & Gene::get_chromosomes() {
    return chromosomes;
  }

  const string Gene::show() {
    stringstream ss;

    for (auto c : chromosomes)
      ss << c << ' ';

    return ss.str();
  }

  const double Gene::get_min() {
    return chromo_min;
  }

  const double Gene::get_max() {
    return chromo_max;
  }

  const int Gene::size() {
    return (int)chromosomes.size();
  }
}
