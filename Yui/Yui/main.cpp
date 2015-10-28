#include "GA.h"
#include "IO.h"
#include <cstdio>
#include <string>
#include <fstream>
#include <vector>
#include <unordered_map>
#include <numeric>
#include <functional>
#define _USE_MATH_DEFINES
#include <math.h>


#if defined(_WIN32) || defined(_WIN64)
  #define popen _popen
  #define pclose _pclose
#endif

int main(int argc, char* argv[])
{
  const int STEPS = 100;

  int param_count = 6;

  auto eval = [argv](yui::Gene g)-> int {
    FILE *exec = popen((std::string(argv[1]) + " " + g.show()).c_str(), "r");
    char buf[256];
    fgets(buf, 256, exec);
    pclose(exec);
    int a = std::stoi(buf);

    return a;
  };

  yui::IO io("result");
  std::vector<yui::Gene> v = io.pred_data(-5.12, 5.12);

  yui::GA *ga;
  if (v.empty())
    // GA(染色体の数n, 定数m(n*mが一世代の個体数であり,m=15~50推奨), 定数k(後述), パラメータの最小値, パラメータの最大値)
    ga = new yui::GA(param_count, 15, 1.0, -100, 100);
  else
    // GA(Geneのベクタ, 染色体の数n, 定数k((n * (k+1))が子の個体数になり,k=0.5~2.0推奨.もちろん子の個体数は整数))
    ga = new yui::GA(v, param_count, 1.0);


  puts("learning start.");

  for (int i = 0; i < STEPS; i++) {
    // step(評価関数)
    ga->step(eval);
    io.logging(ga->show());
    printf("%d\n", i);
  }

  puts("done.");
  int i = 0;
  for (auto g : ga->get_genes())
    printf("%d: %d\n", i, eval(g));

  delete ga;
}
