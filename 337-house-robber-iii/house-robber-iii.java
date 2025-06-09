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
    public int rob(TreeNode root) {
        int[] res = robTree(root);
        return Math.max(res[0],res[1]);
    }
    //returns an int[] where int[0] = maxVal to not rob node
    //int[1] = maxVal to rob node

    private int[] robTree(TreeNode node){
        int[] res = new int[2];
        //basecase：null了就只能返回0
        if (node == null) return res;
        int[] left = robTree(node.left);
        int[] right = robTree(node.right);
        //当前不rob，无所谓左右rob与否，所以看左右最大值，然后叠加
        res[0] = Math.max(left[0],left[1]) + Math.max(right[0], right[1]);
        //当前rob，左右必不可rob
        res[1] = node.val + left[0] + right[0];
        //左右后，处理完左右再往上返回
        return res;
    }
}