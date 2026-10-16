class Solution {
    static constexpr long long MOD = 1'000'000'007LL;

    struct Node {
        long long sum = 0;
        long long sumSquares = 0;
        long long lazy = 0;
    };

    vector<Node> tree;

    void apply(int node, int left, int right, long long add) {
        long long length = right - left + 1;
        long long oldSum = tree[node].sum;

        tree[node].sumSquares =
            (tree[node].sumSquares
             + 2 * add * oldSum
             + length * add * add) % MOD;

        tree[node].sum = (oldSum + length * add) % MOD;
        tree[node].lazy += add;
    }

    void push(int node, int left, int right) {
        if (tree[node].lazy == 0 || left == right) return;

        int mid = left + (right - left) / 2;
        apply(node * 2, left, mid, tree[node].lazy);
        apply(node * 2 + 1, mid + 1, right, tree[node].lazy);
        tree[node].lazy = 0;
    }

    void update(int node, int left, int right,
                int queryLeft, int queryRight) {
        if (queryLeft <= left && right <= queryRight) {
            apply(node, left, right, 1);
            return;
        }

        push(node, left, right);
        int mid = left + (right - left) / 2;

        if (queryLeft <= mid) {
            update(node * 2, left, mid, queryLeft, queryRight);
        }
        if (queryRight > mid) {
            update(node * 2 + 1, mid + 1, right, queryLeft, queryRight);
        }

        tree[node].sum =
            (tree[node * 2].sum + tree[node * 2 + 1].sum) % MOD;
        tree[node].sumSquares =
            (tree[node * 2].sumSquares + tree[node * 2 + 1].sumSquares) % MOD;
    }

public:
    int sumCounts(vector<int>& nums) {
        int n = nums.size();
        tree.assign(4 * n, Node{});

        vector<int> last(100001, -1);
        long long answer = 0;

        for (int i = 0; i < n; ++i) {
            int left = last[nums[i]] + 1;
            update(1, 0, n - 1, left, i);

            answer = (answer + tree[1].sumSquares) % MOD;
            last[nums[i]] = i;
        }

        return static_cast<int>(answer);
    }
};