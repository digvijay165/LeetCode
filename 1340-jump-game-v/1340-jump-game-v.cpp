class Solution {
    int dfs(int i, vector<int>& arr, int d, vector<int>& memo) {
        if (memo[i] != 0) return memo[i];

        memo[i] = 1;  // Count the current index.

        for (int direction : {-1, 1}) {
            for (int step = 1; step <= d; ++step) {
                int j = i + direction * step;

                if (j < 0 || j >= arr.size() || arr[j] >= arr[i]) {
                    break;
                }

                memo[i] = max(memo[i], 1 + dfs(j, arr, d, memo));
            }
        }

        return memo[i];
    }

public:
    int maxJumps(vector<int>& arr, int d) {
        vector<int> memo(arr.size(), 0);
        int answer = 1;

        for (int i = 0; i < arr.size(); ++i) {
            answer = max(answer, dfs(i, arr, d, memo));
        }

        return answer;
    }
};