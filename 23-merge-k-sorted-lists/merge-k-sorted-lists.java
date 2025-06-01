/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        //这里记住PriorityQueue有ue，但是Deque没有
        //PriorityQueue是自己的一个class，不能用linkedlist
        PriorityQueue<ListNode> pq = new PriorityQueue<>((a,b) -> a.val - b.val);
        for (ListNode node: lists){
            //这里注意空链表之后poll会报错，所以需要检查
            if (node !=null){
                pq.add(node);
            }
        }
        ListNode dummyHead = new ListNode();
        ListNode curr = dummyHead;
        while (!pq.isEmpty()){
            ListNode nextNode = pq.poll();
            curr.next = nextNode;
            curr = curr.next;
            //// ⚠️ 把 nextNode 的下一个也加入堆
            //一开始我只把头节点入堆，忘记后面的了
            if (nextNode.next !=null){
                pq.add(nextNode.next);
            }
        }
        return dummyHead.next;
    }
}