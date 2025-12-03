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
    //in: left, root, right
    //post: left, right, root
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        if (inorder. length == 0){
            return null;
        }
        int rootVal = postorder[postorder.length - 1];
        int i = 0;
        for (; i< inorder.length;i++){
            if (inorder[i]==rootVal) break;
        }
        TreeNode root = new TreeNode(rootVal);
        int[] leftIn = Arrays.copyOfRange(inorder, 0, i);
        int[] rightIn = Arrays.copyOfRange(inorder, i+1, inorder.length);

        int rightSize = inorder.length - 1 - i;
        int[] leftPost = Arrays.copyOfRange(postorder, 0, i);
        int[] rightPost = Arrays.copyOfRange(postorder, i, i + rightSize);
        root.left = buildTree(leftIn, leftPost);
        root.right = buildTree(rightIn, rightPost);
        return root;
    }
}