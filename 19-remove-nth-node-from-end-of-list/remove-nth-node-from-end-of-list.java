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
    public ListNode removeNthFromEnd(ListNode head, int n) {
        int fast = 0;
        ListNode dummyHead = new ListNode();
        dummyHead.next = head;
        ListNode curr = dummyHead;
        while (fast < n + 1){
            curr = curr.next;
            fast ++;
        }
        ListNode slowNode = dummyHead;
        while (curr!= null){
            curr = curr.next;
            slowNode = slowNode.next;
        }

        slowNode.next = slowNode.next.next;
        return dummyHead.next;

    }
}