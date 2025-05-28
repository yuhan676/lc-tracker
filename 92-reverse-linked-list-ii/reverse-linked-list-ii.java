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
        // if empty List OR the node to swap is the same ,return
        if (head == null || left == right) return head;
        //use dummy head to make swapping simpler
        int index = 0;
        ListNode dummyHead = new ListNode();
        dummyHead.next = head;
        ListNode curr = dummyHead;
        //find the end of the first section (unswapped)
        while (index < left - 1){
            curr = curr.next;
            index ++;
        }
        ListNode firstEnd = curr;
        //find the beginning of the third section (unswapped)
        while (index <= right){
            curr = curr.next;
            index ++;
        }
        ListNode thirdStart = curr;
        //we start swapping from the node at position: left + 1 as curr, because we don't want nodes before left being swapped too 
        ListNode prev = firstEnd.next;
        curr = prev.next;
        //index indicate the curr node
        index = left + 1;
        //mark the beginning of the swapped section (will be the end by the end)
        ListNode secondFirst = prev;

        //swap until the current node is right, becuase we are dealing with swaping curr and prev
        while (index <= right){
            //swapping curr's pointer
            ListNode temp = curr.next;
            curr.next = prev;
            //incrementing prev and curr
            prev = curr;
            curr = temp;
            index ++;
        }
        // first section must point to the beginning of the swapped section now (originally, the last element in the second section)
        firstEnd.next = prev;
        // the end of the swapped second section points to beginning of the third section (actually it's not necessary to have the thirdStart marker, as we can just point it to curr);
        secondFirst.next = thirdStart;
        //return the new head (if changed)
        return dummyHead.next;
    }
}