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
    public List<List<Integer>> levelOrder(TreeNode root) {
        Queue<TreeNode> q = new LinkedList<>();
        List<List<Integer>> res = new ArrayList<>();
        q.offer(root);
        //remember the edge case
        if (root == null) return res;
        while (!q.isEmpty()){
            List<Integer> level = new ArrayList<>();
            int size = q.size();
            while (size >0){
                TreeNode curr = q.poll();
                level.add(curr.val);
                if (curr.left!= null)q.offer(curr.left);
                if (curr.right!=null)q.offer(curr.right);
                size --;
            }
            res.add(level);
        }
        return res;
    }
}