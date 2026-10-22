class Solution {
    public int minElement(int[] nums)
    {
        int minimum = Integer.MAX_VALUE;

        for (int num : nums)
            {
            int digitSum = 0;

            while (num > 0)
                {
                digitSum += num % 10;
                num /= 10;
            }

            minimum = Math.min(minimum, digitSum);
        }

        return minimum;
    }
}
