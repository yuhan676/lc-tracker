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

    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        Map<TreeNode,TreeNode> parent = new HashMap<>();
        markParents(root, parent);

        //now it's bfs search for k steps away from target, so we'll need a way to mark if a node is visited 
        Map<TreeNode, Boolean> visited = new HashMap<>();
        //bfs using queue
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(target);
        visited.put(target, true);
        while (k>0 && !q.isEmpty()){
            int sz = q.size();
            for (int i=0;i<sz;i++){
                TreeNode top = q.poll();
                if (top.left !=null && !visited.containsKey(top.left)){
                    q.offer(top.left);
                    visited.put(top.left, true);
                }
                if (top.right != null && !visited.containsKey(top.right)){
                    q.offer(top.right);
                    visited.put(top.right, true);
                }
                if (parent.containsKey(top) && !visited.containsKey(parent.get(top))){
                    q.offer(parent.get(top));
                    visited.put(parent.get(top), true);
                }
            }
            k--;
        }
        List<Integer> ans = new ArrayList<>();
        while (!q.isEmpty()){
            ans.add(q.poll().val);
        }
        return ans;

    }
    //mark the link between child - parent in a map, treat it as a graph
    public void markParents(TreeNode root, Map<TreeNode, TreeNode>parent){
        Queue<TreeNode> que = new LinkedList<>();
        que.offer(root);
        while (!que.isEmpty()){
            int sz = que.size();
            for (int i=0;i<sz;i++){
                TreeNode curr = que.poll();
                if (curr.left != null){
                    parent.put(curr.left, curr);
                    que.offer(curr.left);
                }
                if (curr.right != null){
                    parent.put(curr.right,curr);
                    que.offer(curr.right);
                }
            }
        }
    }
}