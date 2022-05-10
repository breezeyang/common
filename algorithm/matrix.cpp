#include <iostream>
#include <vector>

std::vector<std::vector<int> > general_matrix(int n) {
    std::vector<std::vector<int>> matrix(n, std::vector<int>(n, 0));
    std::vector<std::vector<int>> directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    int row = 0;
    int column = 0;
    int directionIdx = 0;
    int cur = 1;
    int max_num = n * n;
    while (cur <= max_num) {
        matrix[row][column] = cur;
        cur++;
        int next_row = row + directions[directionIdx][0];
        int next_column = column + directions[directionIdx][1];
        if (next_row < 0 || next_row >= n || next_column < 0 || next_column >= n || matrix[next_row][next_column] != 0) {
            directionIdx = (directionIdx + 1) % 4;
        }
        row = row + directions[directionIdx][0];
        column = column + directions[directionIdx][1];
    }
    return matrix;
}

int main()
{
    std::vector<std::vector<int>> result = general_matrix(3);
    std::for_each(result.begin(), result.end(), 
                  [] (std::vector<int>& vec) {
                      std::for_each(vec.begin(), vec.end(), [](int val) {std::cout << val << " ";});
                      std::cout << "" << std::endl;
                  });
    return 0;
}

