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
    public int findBottomLeftValue(TreeNode root) {
        if(root==null) return 0;
        List<List<Integer>> tree = new ArrayList<>();
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()){
            int size = q.size();
            List<Integer> level = new ArrayList<>();
            while (size >0){
                TreeNode node = q.poll();
                level.add(node.val);
                if (node.left!=null) q.offer(node.left);
                if (node.right!=null)q.offer(node.right);
                //记得size--
                size --;
            }
            tree.add(level);
        }
        return tree.get(tree.size()-1).get(0);

    }
}