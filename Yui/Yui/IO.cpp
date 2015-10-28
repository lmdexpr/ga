#include "IO.h"

#include <fstream>
#include <sstream>

#if defined(_WIN32) || defined(_WIN64)
	#include <direct.h>
#else
	#include <sys/stat.h>
#endif

#if defined(_WIN32) || defined(_WIN64)
  #define mkdir _mkdir
#endif

namespace yui {
  IO::IO(string fname)
  : endpoint(fname + "/")
  , current_file(endpoint + ".current")
  {
    ifstream ifs(current_file);
    string buf;

#if defined(_WIN32) || defined(_WIN64)
    mkdir(endpoint.c_str());
#else
    mkdir(endpoint.c_str(), 0775);
#endif

    if (ifs && getline(ifs, buf))
      parse_current_file(buf);
    else
      parse_current_file("0");
  }

  void IO::logging(string s) {
    ofstream ofs(endpoint + step_file);
    ofstream current(current_file);

    ofs << s;
    succ();

    current << step_file;
  }

  vector<Gene> IO::pred_data(double min, double max) {
    vector<Gene> v;
    string buf;
    stringstream ss;
    ss << add(-1);
    ifstream ifs(endpoint + ss.str());

    while (ifs && getline(ifs, buf)) {
      vector<double> chromosomes;

      istringstream iss(buf); string s;
      while (getline(iss, s, ' '))
        chromosomes.push_back(stod(s));

      v.push_back(Gene(chromosomes, min, max));
    }

    return v;
  }

  void IO::remove() {
    std::remove(current_file.c_str());
  }

  void IO::parse_current_file(string target) {
    istringstream iss(target);

    iss >> step_file;
  }

  int IO::add(int i) {
    return stoi(step_file) + i;
  }

  void IO::succ() {
    stringstream ss;
    ss << add(1);
    step_file = ss.str();
  }
}
