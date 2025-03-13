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
        return getHeight(root);
    }
    public int getHeight(TreeNode node){
        if (node == null) return 0;
        int leftHeight = getHeight(node.left);
        int rightHeight = getHeight(node.right);
        int height = 1 + Math.max(leftHeight,rightHeight);

        return height;
    }
}