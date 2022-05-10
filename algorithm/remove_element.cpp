#include <iostream>
#include <vector>
/*
 * 时间复杂度：O(n^2)
 * 空间复杂度：O(1)
 *
 */
int remove_element(std::vector<int>& nums, int target) {
    int size = nums.size();
    for (int i=0; i<size; ++i) {
        if (nums[i] == target) {
            for (int j=i+1; j < size; ++j) {
                nums[j-1] = nums[j];
            } 
            i--;
            size--;
        }
    }
    return size;
}

/**
 * 时间复杂度：O(n)
 * 空间复杂度: O(1)
 */
int remove_element2(std::vector<int>& nums, int target) {
    int size = nums.size();
    int slowidx = 0;
    for (int i=0; i<size; ++i) {
        if (nums[i] != target) {
            nums[slowidx] = nums[i];
            ++slowidx;
        }
    }
    return slowidx;
}

int main()
{
    std::vector<int> nums = {1, 2, 2, 3};
    int ret = remove_element2(nums, 3);
    std::cout << ret << std::endl;
    return 0;
}

