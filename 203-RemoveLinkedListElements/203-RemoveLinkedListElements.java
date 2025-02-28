class Solution {
    public ListNode removeElements(ListNode head, int val) {
        ListNode newhead = new ListNode(0,head);
        ListNode curr = newhead;
        while (curr.next != null){
            if (curr.next.val == val){
                curr.next = curr.next.next;
            }else{
            curr = curr.next;
            }
        }
        return newhead.next;
    }
}