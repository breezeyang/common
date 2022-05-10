#include <iostream>
#include <vector>
#include <algorithm>

std::vector<int> sorted_sqr(std::vector<int>& nums) {
    for (int i=0; i<nums.size(); ++i) {
        nums[i] = nums[i] * nums[i];
    }
    std::sort(nums.begin(), nums.end(), [](int a, int b) {return a < b; });
    return nums;
}


std::vector<int> sorted_sqr2(std::vector<int>& nums) {
    int left = 0; 
    int right = nums.size() - 1;
    std::vector<int> result(nums.size(), 0);
    int k = 0;
    while (left <= right) {
        int sqr_left = nums[left] * nums[left];
        int sqr_right = nums[right] * nums[right];
        if (sqr_left > sqr_right) {
            result[k] = sqr_left;
            left++;
        } else {
            result[k] = sqr_right;
            right--;
        }
        k++;
    }
    return result;
}

void swap(int& a, int& b) {
    int c;
    c = a;
    a = b;
    b = c;
}

std::vector<int> sorted_sqr3(std::vector<int>& nums) {
    int left = 0;
    int right = nums.size() -1;
    while (left <= right) {
        int sqr_left = nums[left] * nums[left];
        int sqr_right = nums[right] * nums[right];
        if (sqr_left < sqr_right) {
            // 交换
            swap(nums[left], nums[right]);
        } 
        nums[left] = nums[left] * nums[left];
        left++;
    }
    return nums;
}

int main()
{
    std::vector<int> nums = {-2, 1, 0, 1, 2};
    std::vector<int> result = sorted_sqr3(nums);
    std::for_each(result.begin(), result.end(), [](int val) { std::cout << val << std::endl; });
    return 0;
}

