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
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return buildTreeWithIndex(preorder,inorder);
    }

    private TreeNode buildTreeWithIndex(int[] preorder, int[] inorder){
        if (preorder.length==0) return null;
        TreeNode root = new TreeNode(preorder[0]);
        if (preorder.length==1) return root;
        int index = 0;
        for (; index<inorder.length;index++){
            if (inorder[index]==root.val){
                break;
            }
        }
        int[] leftinorder = Arrays.copyOfRange(inorder,0,index);
        int[] rightinorder = Arrays.copyOfRange(inorder,index+1,inorder.length);
        int[] leftpreorder = Arrays.copyOfRange(preorder,1,index+1);
        int[] rightpreorder = Arrays.copyOfRange(preorder,index+1,preorder.length);

        root.left = buildTreeWithIndex(leftpreorder,leftinorder);
        root.right = buildTreeWithIndex(rightpreorder,rightinorder);
        return root;
    }
}