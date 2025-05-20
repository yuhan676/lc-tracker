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
    Map<Integer,Integer> inorderMap = new HashMap<>();
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder.length==0) return null;
        for (int i =0; i<inorder.length;i++){
            inorderMap.put(inorder[i],i);
        }
        return helper(preorder,0,preorder.length-1,inorder,0,inorder.length-1);

    }

    private TreeNode helper(int[] preorder, int preS, int preE, int[] inorder, int inS, int inE){
        if (preS > preE || inS >inE) return null;
        int rootVal = preorder[preS];
        int inorderRootIndex = inorderMap.get(rootVal);
        int leftSize = inorderRootIndex - inS;
        TreeNode root = new TreeNode(rootVal);
        root.left = helper(preorder, preS + 1, preS + leftSize, inorder, inS, inorderRootIndex-1);
        root.right = helper(preorder,preS + 1 + leftSize, preE, inorder, inS + 1 + leftSize, inE);
        return root;
    }
}