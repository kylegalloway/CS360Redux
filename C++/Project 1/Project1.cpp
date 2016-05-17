#include <cstdlib>
#include <ctime>
#include <iostream>
#include <vector>
using namespace std;

typedef vector< vector <int> > Matrix;
int total_mults = 0;
int total_adds = 0;

void show_usage(string name)
{
    cerr << "Usage: " << name << " <option(s)> SOURCES"
              << "Options:\n"
              << "\t-n NUMBER\tSpecify the size of the matrix (n X n)."
              << endl;
} // end method show_usage

Matrix randomize_matrix(const Matrix& m)
{
    int n = m.size();
    
    Matrix c(n, vector<int> (n));
    for(int i = 0; i < n; ++i)
    {
        for(int j = 0; j < n; ++j)
        {
            c[i][j] = rand() % 10;
        }
    }
    return c;
} // end function randomize_matrix

Matrix add(const Matrix &m1, const Matrix &m2)
{
    int n = m1.size();
    Matrix m3(n, vector<int> (n));

    for (int i = 0; i < n; i++)
    {
        for (int j = 0; j < n; j++)
        {
            m3[i][j] = m1[i][j] + m2[i][j];
            total_adds++;
        }
    }
    return m3;
} // end function add

Matrix subtract(const Matrix &m1, const Matrix &m2)
{
    int n = m1.size();
    Matrix m3(n, vector<int> (n));

    for (int i = 0; i < n; i++)
    {
        for (int j = 0; j < n; j++)
        {
            m3[i][j] = m1[i][j] - m2[i][j];
            total_adds++;
        }
    }
    return m3;
} // end function subtract

Matrix strassen(Matrix &m1, Matrix &m2)
{
    int n = m1.size();
    
    Matrix m3(n, vector<int> (n));
    if (n < 2)
    {
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                for (int k = 0; k < n; k++)
                {
                    m3[i][k] += m1[i][j] * m2[j][k];
                    total_mults++;
                }
            }
        }
        return m3;
    }
    else
    {
        int newN = n / 2;

        Matrix a11(newN, vector<int> (newN));
        Matrix a12(newN, vector<int> (newN));
        Matrix a21(newN, vector<int> (newN));
        Matrix a22(newN, vector<int> (newN));
        
        Matrix b11(newN, vector<int> (newN));
        Matrix b12(newN, vector<int> (newN));
        Matrix b21(newN, vector<int> (newN));
        Matrix b22(newN, vector<int> (newN));

        Matrix c11(newN, vector<int> (newN));
        Matrix c12(newN, vector<int> (newN));
        Matrix c21(newN, vector<int> (newN));
        Matrix c22(newN, vector<int> (newN));
        
        // this divides the matrices into 4 sub-matrices, each.
        for (int i = 0; i < newN; i++)
        {
            for (int j = 0; j < newN; j++)
            {
                a11[i][j] = m1[i][j];
                a12[i][j] = m1[i][j + newN];
                a21[i][j] = m1[i + newN][j];
                a22[i][j] = m1[i + newN][j + newN];

                b11[i][j] = m2[i][j];
                b12[i][j] = m2[i][j + newN];
                b21[i][j] = m2[i + newN][j];
                b22[i][j] = m2[i + newN][j + newN];
            }
        }

        // s1 = b12 - b22
        Matrix s1 = subtract(b12,b22);
        // s2 = a11 + a12
        Matrix s2 = add(a11,a12);
        // s3 = a21 + a22
        Matrix s3 = add(a21,a22);
        // s4 = b21 - b11
        Matrix s4 = subtract(b21,b11);
        // s5 = a11 + a22
        Matrix s5 = add(a11,a22);
        // s6 = b11 + b22
        Matrix s6 = add(b11,b22);
        // s7 = a12 - a22
        Matrix s7 = subtract(a12,a22);
        // s8 = b21 + b22
        Matrix s8 = add(b21,b22);
        // s9 = a11 - a21
        Matrix s9 = subtract(a11,a21);
        // s10 = b11 + b12
        Matrix s10 = add(b11,b12);

        // p1 = (a11) * (b12 - b22)
        Matrix p1 = strassen(a11, s1);
        // p2 = (a11 + a12) * (b22)
        Matrix p2 = strassen(s2, b22);
        // p3 = (a21 + a22) * (b11)
        Matrix p3 = strassen(s3, b11);
        // p4 = (a22) * (b21 - b11)
        Matrix p4 = strassen(a22, s4);
        // p5 = (a11 + a22) * (b11 + b22)
        Matrix p5 = strassen(s5, s6);
        // p6 = (a12 - a22) * (b21 + b22)
        Matrix p6 = strassen(s7, s8);
        // p7 = (a11 - a21) * (b11 + b12)
        Matrix p7 = strassen(s9, s10);

        // c11 = p5 + p4 - p2 + p6
        c11 = add(subtract(add(p5, p4), p2), p6);
        // c12 = p1 + p2
        c12 = add(p1, p2);
        // c21 = p3 + p4
        c21 = add(p3, p4);
        // c22 = p5 + p1 - p3 - p7
        c22 = subtract(subtract(add(p5, p1), p3), p7);

        for (int i = 0; i < newN; i++)
        {
            for (int j = 0; j < newN; j++)
            {
                m3[i][j]               = c11[i][j];
                m3[i][j + newN]        = c12[i][j];
                m3[i + newN][j]        = c21[i][j];
                m3[i + newN][j + newN] = c22[i][j];
            }
        }

        return m3;
    }
} // end function strassen

void print_matrix(const Matrix& m)
{
    int nrows = m.size();
    int ncols = m[0].size();
    
    for(int i = 0; i < nrows; ++i)
    {
        for(int j = 0; j < ncols; ++j)
        {
            cout << m[i][j] << " ";
        }
        cout << endl;
    }
} // end method print_matrix

/* Matrix[] readfile(string fn)
{
    char ch;
    fstream fin(fn, fstream::in);
    while (fin >> noskipws >> ch) {
        cout << ch;
    }
    
    Matrix m1(size,vector<int>(size));
    Matrix m2(size,vector<int>(size));
    return [m1, m2];
}
*/
int main(int argc, char* argv[])
{
    srand(time(NULL));
    
    int size = 0;
    if(argc == 3)
    {
        string arg = argv[1];
        if(arg == "-n")
        {
            size = atoi(argv[2]);
        }
        else
        {
            show_usage(argv[0]);
            exit(1);
        }
    }
    else
    {
        // Matrix[] m = readfile();
        // Matrix m1 = m[0];
        // Matrix m2 = m[1];
        show_usage(argv[0]);
        exit(2);
    }
        
    Matrix m1(size,vector<int>(size));
    Matrix m2(size,vector<int>(size));
    Matrix m3(size,vector<int>(size));
    m1 = randomize_matrix(m1);
    m2 = randomize_matrix(m2);
    m3 = strassen(m1, m2);
    
    /* Output Section */
    cout << "N=" << size << endl;
    cout << "\nInput Matrix A" << endl;
    print_matrix(m1);
    cout << "\nInput Matrix B" << endl;
    print_matrix(m2);
    cout << "\nOutput Matrix C" << endl;
    print_matrix(m3);
    cout << "\nNumber of multiplications: " << total_mults << endl;
    cout << "\nNumber of additions: " << total_adds << endl;
} // end function main