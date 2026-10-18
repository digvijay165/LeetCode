import java.util.*;

class Solution {
    public int minSwaps(int[] nums) {
        Integer[] sorted = Arrays.stream(nums).boxed().toArray(Integer[]::new);

        Arrays.sort(sorted, (a, b) -> {
            int sumCompare = Integer.compare(digitSum(a), digitSum(b));
            return sumCompare != 0 ? sumCompare : Integer.compare(a, b);
        });

        Map<Integer, Integer> destination = new HashMap<>();
        for (int i = 0; i < sorted.length; i++) {
            destination.put(sorted[i], i);
        }

        boolean[] visited = new boolean[nums.length];
        int swaps = 0;

        for (int i = 0; i < nums.length; i++) {
            if (visited[i]) {
                continue;
            }

            int cycleLength = 0;
            int j = i;

            while (!visited[j]) {
                visited[j] = true;
                cycleLength++;
                j = destination.get(nums[j]);
            }

            swaps += Math.max(0, cycleLength - 1);
        }

        return swaps;
    }

    private int digitSum(int value) {
        int sum = 0;
        while (value > 0) {
            sum += value % 10;
            value /= 10;
        }
        return sum;
    }
}