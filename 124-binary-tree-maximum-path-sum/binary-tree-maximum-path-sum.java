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

    int maxPath = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        if (root==null) return 0;
        helper(root);

        return maxPath;
    }

    int helper(TreeNode root){
        if (root==null) return 0;

        int left = Math.max(0,helper(root.left));
        int right = Math.max(0,helper(root.right));
        int pathSumThroughRoot = root.val + left + right;
        if (pathSumThroughRoot > maxPath) maxPath = pathSumThroughRoot;
        return Math.max(root.val + left, root.val + right);
    }
}