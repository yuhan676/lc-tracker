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
    int index = 0;
    int res;

    public int kthSmallest(TreeNode root, int k) {
        inorder(root,k);
        return res;
    }

    void inorder(TreeNode root, int k){
        if (root==null) return;
        inorder(root.left,k);
        index ++;
        if (index==k){
            res = root.val;
        }
        inorder(root.right,k);
    }
}