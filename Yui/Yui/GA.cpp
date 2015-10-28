#include "GA.h"
#include <algorithm>
#include <sstream>
#include <numeric>
#include <cassert>

namespace yui {
  random_device GA::rnd;

  GA::GA(int n, int m, int CG, double min, double max)
  : CROSSING_GENES(CG)
  {
    for (int i = 0; i < n; i++)
      genes.push_back(Gene(m, min, max));
  }

  GA::GA(vector<Gene> v, int CG)
  : genes(v)
  , CROSSING_GENES(CG)
  {}

  string GA::show() {
    stringstream ss;

    for (Gene g : genes)
      ss << g.show() << endl;

    return ss.str();
  }

  void GA::step(function<int(Gene)> eval) {
    pair<int, int> p = copy_selection();
    vector<Gene> children;

    for (int i = 0; i < CROSSING_GENES; i++)
      children.push_back(genes.at(p.first) * genes.at(p.second));

    survival_selection(p, children, eval);
  }

  const vector<Gene>& GA::get_genes() {
    return genes;
  }

  pair<int, int> GA::copy_selection() {
    mt19937 mt(rnd());
    int g1 = mt() % genes.size(), g2;

    // sampling without replacement
    while ((g2 = mt() % genes.size()) == g1)
      ;

    return make_pair(g1, g2);
  }

  int GA::roulette(vector<double>& eval_table) {
    mt19937 mt(rnd());
    vector<double> totals = { 1.0 / eval_table.at(0) };

    for (unsigned int i = 1; i < eval_table.size(); i++)
      totals.push_back(totals.at(i-1) + (1.0 / eval_table.at(i)));

    uniform_real_distribution<> urd(0.0, totals.at(eval_table.size() - 1));
    double selection = urd(mt);
    
    int index;
    for (index = 0; totals.at(index) < selection; index++)
      ;

    return index;
  }

  void GA::survival_selection(pair<int,int> parents, vector<Gene> children, function<double(Gene)> eval) {
    vector<double> eval_table;
    int p1 = parents.first, p2 = parents.second;

    children.push_back(genes.at(p1));
    children.push_back(genes.at(p2));

    for (unsigned int i = 0; i < children.size(); i++)
      eval_table.push_back(eval(children.at(i)));

    auto best_individual = min_element(eval_table.begin(), eval_table.end());
    int bi_idx = distance(eval_table.begin(), best_individual);

    genes.at(p1) = children.at(bi_idx);

    eval_table.erase(best_individual);
    children.erase(children.begin() + bi_idx);

    genes.at(p2) = children.at(roulette(eval_table));
  }
}