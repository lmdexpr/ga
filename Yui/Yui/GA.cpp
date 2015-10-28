#include "GA.h"
#include <algorithm>
#include <sstream>
#include <numeric>
#include <cassert>
#include <cstdio>

namespace yui {
  random_device GA::rnd;;

  GA::GA(int n, int m, double k, double min, double max)
  : CROSSING_GENES(m * (int)(k + 1))
  , PARENTS(CROSSING_GENES)
  , DIM(n)
  , INDIVIDUALS(m * DIM)
  {
    for (int i = 0; i < INDIVIDUALS; i++)
      genes.push_back(Gene(n, min, max));
  }

  GA::GA(vector<Gene> v, int n, double k)
  : genes(v)
  , DIM(n)
  , CROSSING_GENES(n * (int)(k + 1))
  , PARENTS(CROSSING_GENES)
  , INDIVIDUALS(n * DIM)
  {}

  const int GA::get_individuals() {
    return INDIVIDUALS;
  }

  const vector<Gene> & GA::get_genes() {
    return genes;
  }

  string GA::show() {
    stringstream ss;

    for (Gene g : genes)
      ss << g.show() << endl;

    return ss.str();
  }

  void GA::step(function<int(Gene)> eval) {
    mt19937 mt(rnd());
    vector<int> parents_idx = copy_selection(PARENTS);
    vector<Gene> parents, children;
    vector<double> gravity_centers(parents.size());

    for (auto i : parents_idx)
      parents.push_back(genes.at(i));

    for (int i = 0; i < DIM; i++) {
      double tmp = 0.0;
      for (int j = 0; j < PARENTS; j++)
        tmp += parents.at(j).get_chromosomes().at(i);

      gravity_centers.push_back(tmp / (double)PARENTS);
    }

    for (int i = 0; i < CROSSING_GENES; i++)
      children.push_back(crossover(parents, gravity_centers));

    survival_selection(parents_idx, children, eval);
  }

  const vector<int> & GA::copy_selection(int n) {
    mt19937 mt(rnd());
    static vector<int> g;

    g.clear(); g.shrink_to_fit();

    // Non-restoration extraction
    for (int i = 0; i < n; i++) {
      int pos = i + (mt() % (genes.size() - i));
      g.push_back(pos);
      swap(genes.at(i), genes.at(pos));
    }

    return g;
  }

  Gene GA::crossover(vector<Gene> &g, const vector<double> &gravity_centers) {
    vector<double> tmp;
    mt19937_64 mt(rnd());
    uniform_real_distribution<> urd(0.0, 1.0);

    for (int i = 0; i < DIM; ++i)
      tmp.push_back(gravity_centers.at(i));

    for (int i = 0; i < PARENTS; ++i)
      for (int j = 0; j < DIM; j++)
        tmp.at(j) += urd(mt) * sqrt(3.0 / (double)PARENTS) * (g.at(i).get_chromosomes().at(j) - gravity_centers.at(j));

    return Gene(tmp, g.at(0).get_min(), g.at(0).get_max());
  }

  void GA::survival_selection(vector<int> &p_idx, vector<Gene> &children, function<int(Gene)> eval) {
    vector< pair<int,int> > eval_table;

    for (int i = 0; i < PARENTS; ++i)
      eval_table.push_back(make_pair(eval(children.at(i)), i));

    sort(eval_table.begin(), eval_table.end(), [](pair<double,int> p1, pair<double,int> p2){ return p1.first < p2.first; });

    for (int i = 0; i < PARENTS; ++i)
      genes.at(p_idx.at(i)) = children.at(eval_table.at(i).second);
  }
}
