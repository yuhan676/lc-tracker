/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Codec {
    String NULL = "#";
    String SEP = ",";
    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        if (root==null) return sb.toString();
        Queue<TreeNode> que = new LinkedList<>();
        que.offer(root);
        while (!que.isEmpty()){
            int qsize = que.size();
            for (int i =0; i<qsize;i++){
                TreeNode currnode = que.poll();
                if (currnode == null){
                    sb.append(NULL).append(SEP);
                    continue;
                }
                sb.append(currnode.val).append(SEP);
                que.offer(currnode.left);
                que.offer(currnode.right);
            }
        }
        return sb.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if(data.length()==0) return null;
        String[] nodelist = data.split(SEP);
        TreeNode root = new TreeNode(Integer.parseInt(nodelist[0]));
        Queue<TreeNode> que = new LinkedList<>();
        que.offer(root);
        int index = 1;

        while (!que.isEmpty()){
            int sz = que.size();
            for (int i=0; i<sz;i++){
                TreeNode parent = que.poll();
                String left = nodelist[index];
                index ++;
                if (!left.equals(NULL)){
                    TreeNode leftNode = new TreeNode(Integer.parseInt(left));
                    parent.left = leftNode;
                    que.offer(leftNode);
                }
                String right = nodelist[index];
                index ++;
                if (!right.equals(NULL)){
                    TreeNode rightNode = new TreeNode(Integer.parseInt(right));
                    parent.right = rightNode;
                    que.offer(rightNode);
                }
            }
        }
        return root;
    }
}

// Your Codec object will be instantiated and called as such:
// Codec ser = new Codec();
// Codec deser = new Codec();
// TreeNode ans = deser.deserialize(ser.serialize(root));