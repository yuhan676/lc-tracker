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
    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (head == null || left == right) return head;
        int index = 0;
        ListNode dummyHead = new ListNode();
        dummyHead.next = head;
        ListNode curr = dummyHead;
        //curr = 0
        //index = 0
        //while index < 1-1
        while (index < left - 1){
            curr = curr.next;
            index ++;
        }
        //firstEnd = 0;
        ListNode firstEnd = curr;
        //while index <= 2;
        //curr = null;
        //index = 3;
        while (index <= right){
            curr = curr.next;
            index ++;
        }
        //thirdStart = null;
        ListNode thirdStart = curr;

        //prev = 3;
        ListNode prev = firstEnd.next;
        //curr = 5
        curr = prev.next;
        index = left + 1;
        //inex = 2;
        ListNode secondFirst = prev;
        //secondFirst = 3;
        //while index <= 2
        while (index <= right){
            ListNode temp = curr.next;
            //temp = null
            curr.next = prev;
            // 5 -> 3;

            prev = curr;
            //prev = 5;
            curr = temp;
            //curr = null;
            index ++;
            //index = 3;
        }
        //dummy->5
        firstEnd.next = prev;
        //3->null
        secondFirst.next = curr;
        return dummyHead.next;
        
    }
}