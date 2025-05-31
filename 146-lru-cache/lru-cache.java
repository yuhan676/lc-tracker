class LRUCache {
    //double ended list to store the keys
    //弄成private，所以只有get和put才能改变它
    //这两个是虚拟头和尾巴节点
    private DoublyLinkedNode oldest;
    private DoublyLinkedNode newest;

    //map for quick search: search the node
    private Map<Integer, DoublyLinkedNode> cache;
    
    private int capacity;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.oldest = new DoublyLinkedNode(0,0);
        this.newest = new DoublyLinkedNode(0,0);
        this.oldest.next = this.newest;
        this.newest.prev = this.oldest;
    }

    private void remove(DoublyLinkedNode node){
        DoublyLinkedNode prev = node.prev;
        DoublyLinkedNode next = node.next;
        prev.next = next;
        next.prev = prev;
    }
    //在latest的前面的地方加入
    //latest是虚拟尾巴节点
    private void insert(DoublyLinkedNode node){
        DoublyLinkedNode prev = newest.prev;
        DoublyLinkedNode next = newest;
        prev.next = next.prev = node;
        node.next = next;
        node.prev = prev;
    }
    
    public int get(int key) {
        if (cache.containsKey(key)){
            DoublyLinkedNode node = cache.get(key);
            //访问了以后要插入到最前面
            remove(node);
            insert(node);
            //找到了，return node.val
            return node.val;
        }
        //找不到，return -1
        return -1;
    }
    
    public void put(int key, int value) {
        if (cache.containsKey(key)){
            remove(cache.get(key));
        }
        DoublyLinkedNode newNode = new DoublyLinkedNode(key, value);
        cache.put(key, newNode);
        insert(newNode);
        //如果满了，就把最后一个顶出去
        //cache.size()记录所有合法节点（不包括oldest，newest）
        if(cache.size() > capacity){
            //记住oldest是虚拟头节点
            DoublyLinkedNode lru = oldest.next;
            remove(lru);
            //在cachemap里删掉这个节点
            cache.remove(lru.key);
        }
    }
}

class DoublyLinkedNode{
    int key;
    int val;
    DoublyLinkedNode prev;
    DoublyLinkedNode next;

    public DoublyLinkedNode(int key, int val){
        this.key = key;
        this.val = val;
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