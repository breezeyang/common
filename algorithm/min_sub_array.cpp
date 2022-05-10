#include <iostream>
#include <vector>

int min_sub_array(std::vector<int>& nums, int target) {
    int result = INT32_MAX;
    int sub_len = 0;
    int sum = 0;
    for (int i=0; i<nums.size(); ++i) {
        sum = 0;
        for (int j=i; j<nums.size(); j++) {
            sum = sum + nums[j];
            if (sum >= target) {
                sub_len = j - i + 1;
                result = result < sub_len? result : sub_len; 
            }
        }
    }
    return result;
}

int min_sub_array2(std::vector<int>& nums, int target) {
    int result = INT32_MAX;
    int sub_len = 0;
    int k = 0;
    int sum = 0;
    for (int i=0; i < nums.size(); ++i) {
        sum += nums[i];
        while (sum >= target) {
            sub_len = i - k + 1;
            result = result < sub_len ? result : sub_len;
            sum -= nums[k];
            k++;
        }
    }
    return result == INT32_MAX ? 0 :result;
}

int main()
{
    std::vector<int> nums = {2, 3, 1, 2, 4, 3};
    int result = min_sub_array2(nums, 7);
    std::cout << result << std::endl;
    return 0;
}

