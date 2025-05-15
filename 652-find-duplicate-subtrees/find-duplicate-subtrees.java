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
    Map<String, Integer> map = new HashMap<>();
    List<TreeNode> res = new LinkedList<>();

    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        constructString(root);
        return res;
    }

    private String constructString(TreeNode root){
        //如果节点是null，就用特殊符号代表
        if (root==null) return "#";
        StringBuilder sb = new StringBuilder();
        //后序遍历左右子树，然后本身，就可以得到当前节点的representation
        String left = constructString(root.left);
        String right = constructString(root.right);
        String myself = sb.append(left + "," + right + "," + root.val).toString();

        int frequency = map.getOrDefault(myself,0);
        if (frequency==1){
            res.add(root);
        }
        map.put(myself, frequency + 1);
        return myself;
    }
}