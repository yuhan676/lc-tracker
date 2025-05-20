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
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        return helper(root,subRoot);
    }

    private boolean helper(TreeNode p, TreeNode q){
        if (p==null) return false;
        if (isSameTree(p,q)) return true;
        //if the root is not the same as subroot, check if the subtrees of root are the same as subtree
        //even if one of the root's subtree is the same as the given subtree, return true;
        return (helper(p.left, q) || helper(p.right,q));
    }
    private boolean isSameTree(TreeNode p, TreeNode q){
        //checks if two trees are the same
        if (p==null && q==null) return true;
        if (p==null || q == null) return false;
        if (p.val != q.val) return false;
        //if root nodes are the same, check if the left and right subtrees are the same
        return (isSameTree(p.left, q.left) && isSameTree(p.right,q.right));
    }
}