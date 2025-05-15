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
    Map<Integer, Integer> inorderValIndexMap;

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        inorderValIndexMap = new HashMap<>();
        for (int i=0; i<inorder.length; i++){ 
            inorderValIndexMap.put(inorder[i],i);
        }
        return helper(inorder, 0, inorder.length-1, postorder, 0, postorder.length-1);
    }
    private TreeNode helper(int[] inorder, int inS, int inE, int[] postorder, int postS, int postE){
        if (inS > inE || postS > postE) return null;
        int rootval = postorder[postE];
        TreeNode root = new TreeNode(rootval);
        //获得inorder中root的指针
        int index = inorderValIndexMap.get(rootval);
        int leftSize = index - inS;

        root.left = helper(inorder,inS,index-1, postorder,postS, postS + leftSize -1);
        root.right = helper(inorder, index +1, inE, postorder, postS + leftSize, postE-1);
        return root;
    }
}