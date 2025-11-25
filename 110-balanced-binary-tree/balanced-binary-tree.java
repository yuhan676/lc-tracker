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
    public boolean isBalanced(TreeNode root) {
        if (root == null) return true;

        int res = getHeight(root);
        if (res == -1) return false;
        else return true;


    }

    private int getHeight(TreeNode node){
        if (node==null) return 0;
        int left = getHeight(node.left);
        int right = getHeight(node.right);
        if (left == -1 || right == -1) return -1;
        if (Math.abs(left-right) >1){
            return -1;
        } else{
            return 1 + Math.max (left,right);
        }
    }
}