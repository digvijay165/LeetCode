class Solution {
public:
    int sumCounts(vector<int>& nums) {
        int total = 0;

        for (int start = 0; start < nums.size(); ++start) {
            unordered_set<int> seen;
            for (int end = start; end < nums.size(); ++end) {
                seen.insert(nums[end]);
                int count = seen.size();
                total += count * count;
            }
        }

        return total;
    }
};