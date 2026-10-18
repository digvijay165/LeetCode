import java.util.*;

class Solution {
    private static final long MOD = 1_000_000_007L;
    private long[] factorial;
    private long[] invFactorial;

    public int numOfWays(int[] nums) {
        int n = nums.length;

        factorial = new long[n + 1];
        invFactorial = new long[n + 1];

        factorial[0] = 1;
        for (int i = 1; i <= n; i++) {
            factorial[i] = factorial[i - 1] * i % MOD;
        }

        invFactorial[n] = modPow(factorial[n], MOD - 2);
        for (int i = n; i > 0; i--) {
            invFactorial[i - 1] = invFactorial[i] * i % MOD;
        }

        List<Integer> values = new ArrayList<>();
        for (int num : nums) {
            values.add(num);
        }

        // Subtract the original ordering.
        return (int) ((countWays(values) - 1 + MOD) % MOD);
    }

    private long countWays(List<Integer> values) {
        if (values.size() <= 2) {
            return 1;
        }

        int root = values.get(0);
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        for (int i = 1; i < values.size(); i++) {
            int value = values.get(i);
            if (value < root) {
                left.add(value);
            } else {
                right.add(value);
            }
        }

        long waysToInterleave = combination(values.size() - 1, left.size());
        return waysToInterleave * countWays(left) % MOD * countWays(right) % MOD;
    }

    private long combination(int n, int k) {
        return factorial[n] * invFactorial[k] % MOD * invFactorial[n - k] % MOD;
    }

    private long modPow(long base, long exponent) {
        long result = 1;
        while (exponent > 0) {
            if ((exponent & 1) == 1) {
                result = result * base % MOD;
            }
            base = base * base % MOD;
            exponent >>= 1;
        }
        return result;
    }
}