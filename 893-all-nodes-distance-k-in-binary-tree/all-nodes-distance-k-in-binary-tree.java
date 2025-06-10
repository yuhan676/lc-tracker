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
    Map<TreeNode, TreeNode> parent = new HashMap<>();

    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        markParents(root);
        Map<TreeNode, Boolean> visited = new HashMap<>();
        Queue<TreeNode> que = new LinkedList<>();
        que.offer(target);
        visited.put(target, true);
        while(k>0 && !que.isEmpty()){
            int sz = que.size();
            for (int i = 0; i<sz;i++){
                TreeNode top = que.poll();
                if (top.left != null && !visited.containsKey(top.left)){
                    que.offer(top.left);
                    visited.put(top.left, true);
                }
                if (top.right != null && !visited.containsKey(top.right)){
                    que.offer(top.right);
                    visited.put(top.right, true);
                }
                if (parent.containsKey(top) && !visited.containsKey(parent.get(top))){
                    que.offer(parent.get(top));
                    visited.put(parent.get(top), true);
                }
            }
            k--;
        }
        List<Integer> res = new LinkedList<>();
        while(!que.isEmpty()){
            res.add(que.poll().val);
        }
        return res;
    }
    public void markParents(TreeNode root){
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()){
            int sz = q.size();
            for (int i = 0; i<sz;i++){
                TreeNode currN = q.poll();
                if (currN.left!=null){
                    parent.put(currN.left, currN);
                    q.offer(currN.left);
                }
                if (currN.right!=null){
                    parent.put(currN.right, currN);
                    q.offer(currN.right);
                }
            }
        }
    }
}