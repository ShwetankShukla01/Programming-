/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    private int matchingNodeCount = 0;

    public int averageOfSubtree(TreeNode root) {
        calculateSumAndCount(root);
        return matchingNodeCount;
    }

    private int[] calculateSumAndCount(TreeNode node) {
        // Base case: An empty node contributes 0 to the sum and 0 to the count
        if (node == null) {
            return new int[]{0, 0};
        }

        // Postorder traversal: Get the data from the left and right subtrees
        int[] leftSubtree = calculateSumAndCount(node.left);
        int[] rightSubtree = calculateSumAndCount(node.right); // Fixed method name

        // Calculate metrics for the current subtree using array indices
        // index 0 = sum, index 1 = count
        int totalSum = node.val + leftSubtree[0] + rightSubtree[0];
        int totalCount = 1 + leftSubtree[1] + rightSubtree[1];

        // Integer division naturally rounds down as required by the problem
        if (totalSum / totalCount == node.val) {
            matchingNodeCount++;
        }

        // Return the accumulated sum and count back up to the parent node
        return new int[]{totalSum, totalCount};
    }
}
