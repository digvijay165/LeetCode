class Solution {
    private static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        int bestIndex = -1;
    }

    public int[] stringIndices(String[] wordsContainer, String[] wordsQuery) {
        TrieNode root = new TrieNode();

        for (int i = 0; i < wordsContainer.length; i++) {
            String word = wordsContainer[i];
            TrieNode node = root;

            updateBest(node, i, wordsContainer);

            for (int j = word.length() - 1; j >= 0; j--) {
                int c = word.charAt(j) - 'a';

                if (node.children[c] == null) {
                    node.children[c] = new TrieNode();
                }

                node = node.children[c];
                updateBest(node, i, wordsContainer);
            }
        }

        int[] answer = new int[wordsQuery.length];

        for (int i = 0; i < wordsQuery.length; i++) {
            String query = wordsQuery[i];
            TrieNode node = root;
            int best = root.bestIndex;

            for (int j = query.length() - 1; j >= 0; j--) {
                int c = query.charAt(j) - 'a';

                if (node.children[c] == null) {
                    break;
                }

                node = node.children[c];
                best = node.bestIndex;
            }

            answer[i] = best;
        }

        return answer;
    }

    private void updateBest(TrieNode node, int index, String[] words) {
        int currentBest = node.bestIndex;

        if (currentBest == -1
                || words[index].length() < words[currentBest].length()
                || (words[index].length() == words[currentBest].length()
                    && index < currentBest)) {
            node.bestIndex = index;
        }
    }
}