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
        Queue<TreeNode> que = new LinkedList<>();
        que.offer(root);
        while (!que.isEmpty()){
            int sz = que.size();
            for (int i=0; i<sz;i++){
                TreeNode parent = que.poll();
                if (parent.val == subRoot.val){
                    if (serialize(parent).equals(serialize(subRoot))) return true;
                }
                if (parent.left != null) que.offer(parent.left);
                if (parent.right != null) que.offer(parent.right);
            }
        }
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
            sb.append(NULL).append(SEP);
            return;
        }
        sb.append(root.val).append(SEP);
        _serialize(root.left, sb);
        _serialize(root.right,sb);
    }
}