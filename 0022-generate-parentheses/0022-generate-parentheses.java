import java.util.*;

class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        build(result, new StringBuilder(), 0, 0, n);
        return result;
    }

    private void build(List<String> result, StringBuilder current,
                       int open, int close, int n) {
        if (current.length() == 2 * n) {
            result.add(current.toString());
            return;
        }

        if (open < n) {
            current.append('(');
            build(result, current, open + 1, close, n);
            current.deleteCharAt(current.length() - 1);
        }

        if (close < open) {
            current.append(')');
            build(result, current, open, close + 1, n);
            current.deleteCharAt(current.length() - 1);
        }
    }
}