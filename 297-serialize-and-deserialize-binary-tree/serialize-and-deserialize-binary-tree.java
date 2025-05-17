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
    String SEP = ",";
    String NULL = "#";
    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        if (root==null) return sb.toString();
        _serialize(root,sb);
        return sb.toString();
    }

    void _serialize(TreeNode root, StringBuilder sb){
        if (root == null){
            sb.append(NULL);
            sb.append(SEP);
            return;
        }
        sb.append(root.val);
        sb.append(SEP);
        _serialize(root.left,sb);
        _serialize(root.right,sb);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data.length()==0) return null;
        List<String> datalist = new ArrayList<>();
        for (String node: data.split(SEP)){
            datalist.add(node);
        }
        return _deserialize(datalist);
    }

    TreeNode _deserialize(List<String> datalist){
        String rootval = datalist.removeFirst();
        if (rootval.equals(NULL)) return null;
        TreeNode rootnode = new TreeNode(Integer.parseInt(rootval));
        rootnode.left = _deserialize(datalist);
        rootnode.right = _deserialize(datalist);
        return rootnode;
    }
}

// Your Codec object will be instantiated and called as such:
// Codec ser = new Codec();
// Codec deser = new Codec();
// TreeNode ans = deser.deserialize(ser.serialize(root));