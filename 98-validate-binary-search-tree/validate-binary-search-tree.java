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
    List<Integer> inorderList = new LinkedList<>();
    public boolean isValidBST(TreeNode root) {
        if (root==null) return true;
        if (root.left == null && root.right == null) return true;
        inorderTraversal(root);

        for(int i=1;i<inorderList.size();i++){
            if (inorderList.get(i)<= inorderList.get(i-1)){return false;}
        }
        return true;


    }

    private void inorderTraversal(TreeNode root){
        if (root==null) return;
        inorderTraversal(root.left);
        inorderList.add(root.val);
        inorderTraversal(root.right);
    }
}