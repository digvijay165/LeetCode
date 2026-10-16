class Solution {
public:
    vector<int> twoSum(vector<int>& nums, int target) {
        unordered_map<int, int> indexOf;

        for (int i = 0; i < nums.size(); ++i) {
            int complement = target - nums[i];

            if (indexOf.count(complement)) {
                return {indexOf[complement], i};
            }

            indexOf[nums[i]] = i;
        }

        return {};
    }
};