#include "GA.h"
#include "IO.h"
#include <cstdio>
#include <string>
#include <fstream>
#include <vector>
#include <unordered_map>

#if defined(_WIN32) || defined(_WIN64)
  #define popen _popen
  #define pclose _pclose
#endif

int main(int argc, char* argv[])
{
  const int STEPS = 2500;

  if (argc < 3) {
    fprintf(stderr, "usage: me <exec_path> <param_count>");
    exit(-1);
  }

  auto eval = [argv](yui::Gene g)-> int {
    static std::unordered_map<std::string, int> hm;
    std::string gs = g.show();

    if (hm.find(gs) == hm.end()) {
      FILE *exec = popen((std::string(argv[1]) + " " + g.show()).c_str(), "r");
      char buf[256];
      fgets(buf, 256, exec);
      hm[gs] = std::stoi(buf);
      pclose(exec);
    }

    return hm[gs];
  };

  int param_count = std::stoi(argv[2]);

  yui::IO io("result");
  std::vector<yui::Gene> v = io.pred_data(0.0, 100.0);

  yui::GA *ga;
  if (v.empty())
    // GA(１世代の遺伝子の数, 染色体の数, 生成される子供の数, パラメータの最小値, パラメータの最大値)
    ga = new yui::GA(100, param_count, 50, 0.0, 1.0);
  else
    ga = new yui::GA(v, param_count);

  puts("learning start.");

  for (int i = 0; i < STEPS; i++) {
    ga->step(eval);
    io.logging(ga->show());
    printf("%d:\n", i);
    for (int j = 0; j < 100; j++)
      printf("\t%d: %d\n", j, eval(ga->get_genes().at(j)));
    puts("");
  }

  puts("done.");
  getchar();

  delete ga;
}
