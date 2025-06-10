/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    Map<TreeNode, Integer> distanceM = new HashMap<>();
    List<Integer> res = new LinkedList<>();

    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        findDistance(root, target);
        traverse(root, k, distanceM.getOrDefault(root, -1));
        return res;
    }

    public int findDistance(TreeNode root, TreeNode target){
        //did not find the target in this subtree
        if (root == null) return -1;

        if (distanceM.containsKey(root)){
            return distanceM.get(root);
        }

        if (root.val == target.val){
            distanceM.put(root,0);
            return 0;
        }
        //the target can only be found in one of the subtrees
        int left = findDistance(root.left,target);
        int right = findDistance(root.right,target);
        if (left >=0){
            distanceM.put(root,left +1);
            return left +1;
        }
        if (right >= 0){
            distanceM.put(root,right +1);
            return right +1;
        }
        //didn't find it in this subtree
        return -1;
    }

    public void traverse(TreeNode root, int k, int currDistance){
        if (root == null) return;

        int distance = distanceM.getOrDefault(root, currDistance);
        if(distance == k) res.add(root.val);
        
        traverse(root.left, k,distance+1);
        traverse(root.right,k,distance+1);
    }


}