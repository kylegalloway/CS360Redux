#include <iostream>
#include <fstream>
#include <string>
using namespace std;

int main () {
    string line;
    ifstream input ("TestFile.txt");
    if (input.is_open()) {
        while ( getline (input,line) ) {
            cout << line << '\n';
        }
        input.close();
    }

    else cout << "Unable to open file";

    return 0;
}