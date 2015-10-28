#pragma once

#include "Gene.h"
#include <string>
#include <vector>

namespace yui {
  using namespace std;

  class IO
  {
  public:
    IO(string);
    void logging(string);
    vector<Gene> pred_data(double, double);
    void remove();
    
  private:
    const string endpoint;
    const string current_file;
    string step_file;

    void parse_current_file(string);
    int add(int i);
    void succ();
  };
}
