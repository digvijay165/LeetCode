class Solution {
    public boolean canReach(String s, int minJump, int maxJump) {
        int n = s.length();
        boolean[] reachable = new boolean[n];
        reachable[0] = true;

        int reachableStarts = 0;

        for (int i = 1; i < n; i++) {
            int add = i - minJump;
            if (add >= 0 && reachable[add]) {
                reachableStarts++;
            }

            int remove = i - maxJump - 1;
            if (remove >= 0 && reachable[remove]) {
                reachableStarts--;
            }

            reachable[i] = s.charAt(i) == '0' && reachableStarts > 0;
        }

        return reachable[n - 1];
    }
}