/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> neighbors;
    public Node() {
        val = 0;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val) {
        val = _val;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val, ArrayList<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
}
*/

class Solution {
    public Node cloneGraph(Node node) {
        if (node==null) return null;
        if (node.neighbors.isEmpty()) return new Node(node.val);
        Map<Node, Node> originCopyMap = new HashMap<>();
        Queue<Node> q = new LinkedList<>();

        q.offer(node);
        originCopyMap.put(node, new Node(node.val));

        while(!q.isEmpty()){
            Node currnode = q.poll();
            for (Node neinode: currnode.neighbors){
                if (!originCopyMap.containsKey(neinode)){
                    q.offer(neinode);
                    originCopyMap.put(neinode, new Node(neinode.val));
                }
                originCopyMap.get(currnode).neighbors.add(originCopyMap.get(neinode));
            }
        }
        return originCopyMap.get(node);
    }
}