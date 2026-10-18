class Solution {
    public int longestValidParentheses(String s) {
        int[] stack = new int[s.length() + 1];
        int top = 0;
        stack[0] = -1; // Base index before the current valid substring.

        int longest = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack[++top] = i;
            } else {
                top--;

                if (top < 0) {
                    // This ')' cannot be matched; reset the base index.
                    stack[++top] = i;
                } else {
                    longest = Math.max(longest, i - stack[top]);
                }
            }
        }

        return longest;
    }
}