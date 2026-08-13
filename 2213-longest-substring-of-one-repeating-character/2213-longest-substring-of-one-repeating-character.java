import java.util.*;

class SegmentTreeNode {
    int lo;
    int hi;

    char maxLetter;
    char prefixLetter;
    char suffixLetter;

    int maxLength;
    int prefixLength;
    int suffixLength;

    SegmentTreeNode left;
    SegmentTreeNode right;

    SegmentTreeNode(
            int lo,
            int hi,
            char maxLetter,
            char prefixLetter,
            char suffixLetter,
            int maxLength,
            int prefixLength,
            int suffixLength,
            SegmentTreeNode left,
            SegmentTreeNode right
    ) {
        this.lo = lo;
        this.hi = hi;
        this.maxLetter = maxLetter;
        this.prefixLetter = prefixLetter;
        this.suffixLetter = suffixLetter;

        this.maxLength = maxLength;
        this.prefixLength = prefixLength;
        this.suffixLength = suffixLength;

        this.left = left;
        this.right = right;
    }

    // Constructor for leaf nodes
    SegmentTreeNode(
            int lo,
            int hi,
            char maxLetter,
            char prefixLetter,
            char suffixLetter,
            int maxLength,
            int prefixLength,
            int suffixLength
    ) {
        this(
                lo, hi,
                maxLetter,
                prefixLetter,
                suffixLetter,
                maxLength,
                prefixLength,
                suffixLength,
                null,
                null
        );
    }
}


class SegmentTree {

    private SegmentTreeNode root;

    public SegmentTree(String s) {
        root = build(s, 0, s.length() - 1);
    }

    private SegmentTreeNode build(String s, int lo, int hi) {

        if (lo == hi) {
            return new SegmentTreeNode(
                    lo,
                    hi,
                    s.charAt(lo),
                    s.charAt(lo),
                    s.charAt(lo),
                    1,
                    1,
                    1
            );
        }

        int mid = (lo + hi) / 2;

        SegmentTreeNode left = build(s, lo, mid);
        SegmentTreeNode right = build(s, mid + 1, hi);

        return merge(left, right);
    }

    public void update(int i, char c) {
        root = update(root, i, c);
    }

    private SegmentTreeNode update(
            SegmentTreeNode root,
            int i,
            char c
    ) {

        // Leaf node
        if (root.lo == i && root.hi == i) {
            root.maxLetter = c;
            root.prefixLetter = c;
            root.suffixLetter = c;

            return root;
        }

        int mid = (root.lo + root.hi) / 2;

        if (i <= mid) {

            SegmentTreeNode updatedLeft =
                    update(root.left, i, c);

            return merge(updatedLeft, root.right);

        } else {

            SegmentTreeNode updatedRight =
                    update(root.right, i, c);

            return merge(root.left, updatedRight);
        }
    }

    private SegmentTreeNode merge(
            SegmentTreeNode left,
            SegmentTreeNode right
    ) {

        // -----------------------------------
        // Get maxLetter and maxLength
        // -----------------------------------

        char maxLetter = ' ';
        int maxLength = 0;

        if (left.maxLength > right.maxLength) {

            maxLetter = left.maxLetter;
            maxLength = left.maxLength;

        } else {

            maxLetter = right.maxLetter;
            maxLength = right.maxLength;
        }

        // Check if maximum sequence crosses the middle
        if (left.suffixLetter == right.prefixLetter
                && left.suffixLength + right.prefixLength > maxLength) {

            maxLetter = left.suffixLetter;

            maxLength =
                    left.suffixLength + right.prefixLength;
        }

        // -----------------------------------
        // Get prefixLetter and prefixLength
        // -----------------------------------

        char prefixLetter = left.prefixLetter;
        int prefixLength = left.prefixLength;

        if (left.lo + prefixLength == right.lo
                && left.prefixLetter == right.prefixLetter) {

            prefixLength += right.prefixLength;
        }

        // -----------------------------------
        // Get suffixLetter and suffixLength
        // -----------------------------------

        char suffixLetter = right.suffixLetter;
        int suffixLength = right.suffixLength;

        if (right.hi - suffixLength == left.hi
                && right.suffixLetter == left.suffixLetter) {

            suffixLength += left.suffixLength;
        }

        return new SegmentTreeNode(
                left.lo,
                right.hi,
                maxLetter,
                prefixLetter,
                suffixLetter,
                maxLength,
                prefixLength,
                suffixLength,
                left,
                right
        );
    }

    public int getMaxLength() {
        return root.maxLength;
    }
}


class Solution {

    public int[] longestRepeating(
            String s,
            String queryCharacters,
            int[] queryIndices
    ) {

        int[] ans = new int[queryIndices.length];

        SegmentTree tree = new SegmentTree(s);

        for (int i = 0; i < queryIndices.length; i++) {

            tree.update(
                    queryIndices[i],
                    queryCharacters.charAt(i)
            );

            ans[i] = tree.getMaxLength();
        }

        return ans;
    }
}