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
    int res = 0;
    public int sumOfLeftLeaves(TreeNode root) {
        traverse(root);
        return res;
    }
        
    
    private void traverse(TreeNode root){
        if (root == null) return;
        sumOfLeftLeaves(root.left);
        sumOfLeftLeaves(root.right);
        if (root.left != null && root.left.left == null && root.left.right == null){
            res += root.left.val;
            return;
        }
        return;
    }
    
}