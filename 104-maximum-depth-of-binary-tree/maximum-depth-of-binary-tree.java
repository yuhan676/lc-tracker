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
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return findMaxDepth(root, 1);

    }

    int findMaxDepth(TreeNode root, int currDepth){
        if (root==null) return 0;
        int left = 0;
        int right = 0;
        if (root.left != null){
            left = findMaxDepth(root.left, currDepth+1);
        }
        if (root.right!= null){
            right = findMaxDepth(root.right,currDepth +1);
        }
        return 1 + Math.max(left, right);
    }
}