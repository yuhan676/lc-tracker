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
    List<List<Integer>> res = new LinkedList<>();
    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) return res;
        Queue<TreeNode> que = new LinkedList<>();
        que.offer(root);
        while (!que.isEmpty()){
            int sz = que.size();
            List<Integer> levelNode = new LinkedList<>();
            for (int i =0; i<sz;i++){
                TreeNode parent = que.poll();
                levelNode.add(parent.val);
                if (parent.left !=null) que.add(parent.left);
                if (parent.right != null) que.add(parent.right);
            }
            res.add(levelNode);
        }
        return res;
    }
}