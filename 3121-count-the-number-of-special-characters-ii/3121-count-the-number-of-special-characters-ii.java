class Solution {
    public int numberOfSpecialChars(String word) {
        int[] lastLower = new int[26];
        int[] firstUpper = new int[26];

        java.util.Arrays.fill(lastLower, -1);
        java.util.Arrays.fill(firstUpper, word.length());

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);

            if (c >= 'a' && c <= 'z') {
                lastLower[c - 'a'] = i;
            } else {
                firstUpper[c - 'A'] = Math.min(firstUpper[c - 'A'], i);
            }
        }

        int count = 0;
        for (int i = 0; i < 26; i++) {
            if (lastLower[i] != -1 && firstUpper[i] < word.length()
                    && lastLower[i] < firstUpper[i]) {
                count++;
            }
        }

        return count;
    }
}