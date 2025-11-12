class LRUCache {

    private DoubLinkNode oldest;
    private DoubLinkNode newest;

    private Map<Integer, DoubLinkNode> cache;

    private int cap;

    public LRUCache(int capacity) {
        this.oldest = new DoubLinkNode(0,0);
        this.newest = new DoubLinkNode(0,0);
        this.cap = capacity;
        this.cache = new HashMap<>();
        this.oldest.next = this.newest;
        this.newest.prev = this.oldest;
    }
    //insert at the 'newest' end
    private void insert(DoubLinkNode node){
        DoubLinkNode prev = newest.prev;
        DoubLinkNode next = newest;
        prev.next = node;
        node.prev = prev;
        node.next = next;
        next.prev = node;
    }
    private void remove(DoubLinkNode node){
        DoubLinkNode prev = node.prev;
        DoubLinkNode next = node.next;
        prev.next = next;
        next.prev = prev;
    }
    
    public int get(int key) {
        if (cache.containsKey(key)){
            DoubLinkNode node = cache.get(key);
            remove(node);
            insert(node);
            return node.v;
        }
        return -1;
    }
    
    public void put(int key, int value) {
        if (cache.containsKey(key)){
            remove(cache.get(key));
        }
        DoubLinkNode newNode = new DoubLinkNode (key,value);
        cache.put(key,newNode);
        insert(newNode);

        if (cache.size() > cap){
            DoubLinkNode lru = oldest.next; 
            remove(lru);
            cache.remove(lru.k);
        }
    }
}

class DoubLinkNode{
    int k;
    int v;
    DoubLinkNode prev;
    DoubLinkNode next;

    public DoubLinkNode(int key, int val){
        this.k = key;
        this.v = val;
        this.prev = null;
        this.next = null;
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */