class Solution {
public:
    int maxSum(vector<int>& nums, int k) {
        const int MOD = 1'000'000'007;
        int bitCount[31] = {};

        for (int x : nums) {
            for (int bit = 0; bit <= 30; ++bit) {
                if (x & (1 << bit)) {
                    ++bitCount[bit];
                }
            }
        }

        long long answer = 0;

        for (int i = 0; i < k; ++i) {
            long long value = 0;

            for (int bit = 0; bit <= 30; ++bit) {
                if (bitCount[bit] > 0) {
                    value |= (1LL << bit);
                    --bitCount[bit];
                }
            }

            answer = (answer + value * value) % MOD;
        }

        return static_cast<int>(answer);
    }
};