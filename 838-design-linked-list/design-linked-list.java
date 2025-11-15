class MyLinkedList {

    LinkedNode dummyHead;
    int size;

    public MyLinkedList() {
        this.size = 0;
        this.dummyHead = new LinkedNode(0);
    }
    
    public int get(int index) {
        if (index <0 || index >= size){
            return -1;
        }
        LinkedNode curr = dummyHead;
        for (int i = 0; i<= index; i++){
            curr = curr.next;
        }
        return curr.val;
    }
    
    public void addAtHead(int val) {
        LinkedNode newNode = new LinkedNode(val);
        LinkedNode originalHead = dummyHead.next;
        dummyHead.next = newNode;
        newNode.prev = dummyHead;
        if (originalHead != null){
            originalHead.prev = newNode;
            newNode.next = originalHead;
        }
        size ++;
    }
    
    public void addAtTail(int val) {
        LinkedNode newNode = new LinkedNode(val);
        LinkedNode curr = dummyHead;
        for (int i = 0; i < size; i++){
            curr = curr.next;
        }
        curr.next = newNode;
        newNode.prev = curr;
        size ++;
    }
    
    public void addAtIndex(int index, int val) {
        if (index <0 || index > size){
            return;
        }
        LinkedNode curr = dummyHead;
        LinkedNode newNode = new LinkedNode(val);
        for (int i = 0; i< index;i++){
            curr = curr.next;
        }
        LinkedNode originalNext = curr.next;
        if (originalNext!= null){
            originalNext.prev = newNode;
            newNode.next = originalNext;
        }
        curr.next = newNode;
        newNode.prev = curr;
        size ++;
    }
    
    public void deleteAtIndex(int index) {
        if (index <0 || index >= size){
            return;
        }
        LinkedNode curr = dummyHead;
        for (int i = 0; i< index; i++){
            curr = curr.next;
        }
        LinkedNode next2 = curr.next.next;

        if (next2!= null){
            next2.prev =curr;
        }
        curr.next = next2;
        size --;
    }
}

class LinkedNode {
    int val;
    LinkedNode prev;
    LinkedNode next;

    public LinkedNode(int val){
        this.val = val;
        this.prev = null;
        this.next = null;
    }
}
/**
 * Your MyLinkedList object will be instantiated and called as such:
 * MyLinkedList obj = new MyLinkedList();
 * int param_1 = obj.get(index);
 * obj.addAtHead(val);
 * obj.addAtTail(val);
 * obj.addAtIndex(index,val);
 * obj.deleteAtIndex(index);
 */