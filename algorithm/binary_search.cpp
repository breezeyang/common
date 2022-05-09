#include <iostream>
#include <vector>

int binary_search(std::vector<int> nums, int target) {
    int left = 0;
    int right = nums.size();
    while (left < right) {
        int middle = left + ((right - left) >> 2);
        if (nums[middle] > target) {
            right = middle;
        } else if (nums[middle] < target) {
            left = middle + 1;
        } else {
            return middle;
        }
    }
    return -1;
}

int main()
{
    std::vector<int> nums = {1, 2, 3 ,5};
    int ret = binary_search(nums, 3);
    std::cout << ret << std::endl;
    return 0;
}

