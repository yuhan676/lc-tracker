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

    Map<Integer,Integer> inorderMap;
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        inorderMap = new HashMap<>();
        //记录inordermap为了快速查询rootvalue在inorder数组中的位置（左中右的中）
        for (int i =0; i<inorder.length;i++){
            inorderMap.put(inorder[i],i);
        }

        return buildTreeWithIndex(preorder,0,preorder.length-1,inorder,0,inorder.length-1);
    }

    private TreeNode buildTreeWithIndex(int[] preorder, int preS, int preE, int[] inorder, int inS, int inE){
        if (preS > preE || inS > inE) return null;
        int rootval = preorder[preS];
        TreeNode root = new TreeNode(rootval);
        int index = inorderMap.get(rootval);
        //得到左子树数组的大小
        int leftSize = index - inS;
        root.left = buildTreeWithIndex(preorder, preS+1, preS + leftSize, inorder, inS, index-1);
        root.right = buildTreeWithIndex(preorder, preS + leftSize + 1, preE, inorder, index + 1, inE);

        return root;
    }
}