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
    final String SEP = ",";
    final String NULL = "#";
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        String subTree = serialize(subRoot);
        String tree = serialize(root);
        if (tree.contains(subTree)) return true;
        return false;
        
    }

    String serialize(TreeNode root){
        StringBuilder sb = new StringBuilder();
        if (root==null) return sb.toString();
        _serialize(root,sb);
        return sb.toString();

    }

    void _serialize(TreeNode root, StringBuilder sb){
        if (root==null){
            sb.append(SEP).append(NULL);
            return;
        }
        sb.append(SEP).append(root.val);
        _serialize(root.left, sb);
        _serialize(root.right,sb);
    }
}