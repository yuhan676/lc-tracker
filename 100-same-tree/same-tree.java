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
    String NULL = "#";
    String SEP = ",";

    public boolean isSameTree(TreeNode p, TreeNode q) {
        return (inOrder(p).equals(inOrder(q)));
    }

    String inOrder(TreeNode root){
        if (root==null) return "";
        StringBuilder sb = new StringBuilder();
        _inOrder(root, sb);
        return sb.toString();
    }

    void _inOrder(TreeNode root, StringBuilder sb){
        if (root == null){
            sb.append(NULL).append(SEP);
            return;
        }
        sb.append(root.val).append(SEP);
        _inOrder(root.left, sb);
        _inOrder(root.right, sb);
        return;
    }
}