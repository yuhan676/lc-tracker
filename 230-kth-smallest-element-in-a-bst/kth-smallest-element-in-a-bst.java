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
    boolean found = false;

    public int kthSmallest(TreeNode root, int k) {
        inorder(root,k);
        return res;
    }

    void inorder(TreeNode root, int k){
        if (root==null || found) return;
        inorder(root.left,k);
        if (found) return;
        index ++;
        if (index==k){
            res = root.val;
            found = true;
            return;
        }
        inorder(root.right,k);
    }
}