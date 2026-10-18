class Solution {
    public int numberOfSpecialChars(String word) {
        boolean[] lowercase = new boolean[26];
        boolean[] uppercase = new boolean[26];

        for (char c : word.toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                lowercase[c - 'a'] = true;
            } else {
                uppercase[c - 'A'] = true;
            }
        }

        int count = 0;
        for (int i = 0; i < 26; i++) {
            if (lowercase[i] && uppercase[i]) {
                count++;
            }
        }

        return count;
    }
}