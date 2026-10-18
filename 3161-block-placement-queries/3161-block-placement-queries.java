import java.util.*;

class Solution {
    public List<Boolean> getResults(int[][] queries) {
        // Compress every queried coordinate so the segment tree stays small.
        int[] coords = new int[queries.length + 1];
        coords[0] = 0;

        for (int i = 0; i < queries.length; i++) {
            coords[i + 1] = queries[i][1];
        }

        Arrays.sort(coords);

        int uniqueCount = 0;
        for (int x : coords) {
            if (uniqueCount == 0 || coords[uniqueCount - 1] != x) {
                coords[uniqueCount++] = x;
            }
        }
        coords = Arrays.copyOf(coords, uniqueCount);

        // Store each obstacle's gap from the previous obstacle.
        SegmentTree gaps = new SegmentTree(coords.length);
        TreeSet<Integer> obstacles = new TreeSet<>();
        obstacles.add(0);

        List<Boolean> results = new ArrayList<>();

        for (int[] query : queries) {
            int x = query[1];

            if (query[0] == 1) {
                int previous = obstacles.floor(x);
                Integer next = obstacles.higher(x);

                obstacles.add(x);
                gaps.update(Arrays.binarySearch(coords, x), x - previous);

                if (next != null) {
                    // Insertion splits the old gap ending at next.
                    gaps.update(Arrays.binarySearch(coords, next), next - x);
                }
            } else {
                int size = query[2];
                int previous = obstacles.floor(x);

                // Largest complete gap ending at or before x.
                int largestGap = gaps.query(0, Arrays.binarySearch(coords, x));

                // Free space from the last obstacle up to x.
                int trailingSpace = x - previous;

                results.add(Math.max(largestGap, trailingSpace) >= size);
            }
        }

        return results;
    }

    private static class SegmentTree {
        private final int size;
        private final int[] tree;

        SegmentTree(int n) {
            int s = 1;
            while (s < n) {
                s <<= 1;
            }
            size = s;
            tree = new int[size * 2];
        }

        void update(int index, int value) {
            int node = size + index;
            tree[node] = value;

            while (node > 1) {
                node >>= 1;
                tree[node] = Math.max(tree[node * 2], tree[node * 2 + 1]);
            }
        }

        // Returns the maximum over the inclusive range [left, right].
        int query(int left, int right) {
            int result = 0;
            int l = left + size;
            int r = right + size;

            while (l <= r) {
                if ((l & 1) == 1) {
                    result = Math.max(result, tree[l++]);
                }
                if ((r & 1) == 0) {
                    result = Math.max(result, tree[r--]);
                }
                l >>= 1;
                r >>= 1;
            }

            return result;
        }
    }
}