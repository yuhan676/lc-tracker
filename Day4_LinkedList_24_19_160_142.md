# Linked List 2
## 24. Swap Nodes in Pairs
* https://leetcode.com/problems/swap-nodes-in-pairs/description/
* 视频讲解：https://www.bilibili.com/video/BV1YT411g7br/?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5
* 文章讲解： https://programmercarl.com/0019.%E5%88%A0%E9%99%A4%E9%93%BE%E8%A1%A8%E7%9A%84%E5%80%92%E6%95%B0%E7%AC%ACN%E4%B8%AA%E8%8A%82%E7%82%B9.html
* 首先画图理解： 只有pairs需要交换，如果最后剩下一个奇数节点，就不用对它进行交换操作了
* 使用虚拟头节点操作，如果不用递归，就得用3个temp来储存curr.next, curr.next.next, curr.next.next.next
<img width="773" alt="Screenshot 2025-03-03 at 15 35 19" src="https://github.com/user-attachments/assets/b50efb5c-b38d-4cfa-a34b-ca87ae029a85" />

### 3个temp，虚拟头节点的迭代解法
* while (curr.next != null && curr.next.next != null) 这个循环每次迭代处理 两个节点，所以它执行的次数是 n/2。
* 因此，整个算法的时间复杂度是：O(n)
* 额外空间（额外变量）：只使用了 dummyHead, curr, temp1, temp2, temp3 这些额外的指针变量，它们的数量是 固定的，与输入 n 无关

```java
class Solution {
    public ListNode swapPairs(ListNode head) {
        ListNode dummyHead = new ListNode();
        dummyHead.next = head;

        ListNode curr = dummyHead;
        ListNode temp1;
        ListNode temp2;
        ListNode temp3;

        while (curr.next != null && curr.next.next != null){
            temp1 = curr.next;
            temp2 = curr.next.next;
            temp3 = curr.next.next.next;//当curr是倒数第三个节点，temp3是null，但是没关系
            curr.next = temp2;
            curr.next.next = temp1;
            temp1.next = temp3;
            curr = temp1;//这里curr是1，因为排序已经是2,1,3,4，curr作为1可以马上处理3，4的反转
        }
        return dummyHead.next;


    }
}
```
### 递归解法（无虚拟头节点）
* 时间复杂度： O(n)，因为每个节点只访问一次。
空间复杂度： O(n)，因为递归调用栈的深度最多是 n/2（递归函数在每次调用时存储栈帧）。
* basecase：交换head和head next 以后返回head.next （交换以后处于队列前方）
* 终止：head == null || head.next == null
![Screenshot 2025-03-03 at 17 35 19](https://github.com/user-attachments/assets/3978dc02-6fdb-4737-8a52-d034adf334d9)

```java
class Solution {
    public ListNode swapPairs(ListNode head) {
        //base case: head本身或者下一个为null
        if (head == null || head.next == null) return head;
            ListNode next = head.next;
            //进行递归
            ListNode newNode = swapPairs(next.next);
            //进行交换
            next.next = head;//head.next(e.g.head为1时next为2)指向head
            head.next = newNode;//head指向head.next.next(e.g.3)

            return next;
    }
}
```
### 两种解法总结
* 栈溢出（Stack Overflow）是指 程序的调用栈（stack）使用的内存超过了系统分配给它的大小，从而导致程序崩溃或异常终止。
* ![Screenshot 2025-03-03 at 17 39 53](https://github.com/user-attachments/assets/996898b6-8d1e-4b9d-b1d4-12b247727f2e)

## 19. Remove Nth Node From End of List
* https://leetcode.com/problems/remove-nth-node-from-end-of-list/description/
* 文章讲解： https://programmercarl.com/0019.%E5%88%A0%E9%99%A4%E9%93%BE%E8%A1%A8%E7%9A%84%E5%80%92%E6%95%B0%E7%AC%ACN%E4%B8%AA%E8%8A%82%E7%82%B9.html
* 视频讲解： bilibili.com/video/BV1vW4y1U7Gf/
* 视频讲得很清晰：快指针先走n+1步再和慢指针同步走，快指针为null的时候慢指针正好指向要删除的node的前一个
* 一定要画图
* 记得不要用fast.next == null作为条件，因为无法处理size = 1的情况
* ⏳ 时间复杂度：O(N)
* fast 指针 先走 n+1 步，需要 O(n) 时间。
* fast 和 slow 再一起走剩下的 N-n 步，总共走了 O(N) 步。
* 由于整个过程只遍历了一遍链表，所以时间复杂度是 O(N)，其中 N 是链表的长度。
* 🗂 空间复杂度：O(1)
* 只使用了 dummyHead、slow、fast 三个额外指针，没有额外的空间开销。
* 没有使用递归，所以不会有额外的递归栈消耗。因此，空间复杂度是 O(1)。
```java
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
        ListNode dummyHead = new ListNode();
        dummyHead.next = head;
        ListNode slow = dummyHead;
        ListNode fast = dummyHead;
        for (int i = 0; i <= n; i++){//这里虽然条件是i<= n,但其实走了n+1步 （画图更好理解）
            fast = fast.next;
        }
        while (fast != null){//fast == null的时候循环结束，不要用fast.next == null，因为无法处理size为1的链表
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        //如果是size=1的链表，dummyHead.next指向null，返回空链表，就不用特定设定edge case了
        return dummyHead.next;
    }
}
```
## 160. Intersection of Two Linked Lists
* https://leetcode.com/problems/intersection-of-two-linked-lists/
* 文章讲解： https://programmercarl.com/0019.%E5%88%A0%E9%99%A4%E9%93%BE%E8%A1%A8%E7%9A%84%E5%80%92%E6%95%B0%E7%AC%ACN%E4%B8%AA%E8%8A%82%E7%82%B9.html#%E6%80%9D%E8%B7%AF
* 设链表 A 的长度为 m，链表 B 的长度为 n。
* 两个指针 a 和 b 分别走过 m + n 的长度。
* 每个节点最多访问两次，因此时间复杂度是 O(m + n)。
* 只使用了 两个额外的指针 a 和 b，空间复杂度是 O(1)（常数级）。
### 环形链表解法：追及问题
```java
public class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        //处理如果headA,headB为空链表的情况
        if (headA == null || headB == null) return null;

        ListNode a = headA;
        ListNode b = headB;
        
        //用环形链表来解
        while (a != b) { 
            a = (a == null) ? headB : a.next;  // 如果 a 走完 A，则走 B
            b = (b == null) ? headA : b.next;  // 如果 b 走完 B，则走 A
        }
        
        return a; // 相遇点 or null
    }
}
```
* 原理

![Screenshot 2025-03-03 at 21 55 47](https://github.com/user-attachments/assets/e72b5a09-9710-4620-9e7a-408e7e3496a9)
![Screenshot 2025-03-03 at 21 56 31](https://github.com/user-attachments/assets/77a383ec-77d0-4a03-bf97-836a422255bc)
![Screenshot 2025-03-03 at 21 56 47](https://github.com/user-attachments/assets/ca40588e-c537-4cff-a5ad-c7ebca22f578)
![Screenshot 2025-03-03 at 21 57 06](https://github.com/user-attachments/assets/b91fe1da-bfb8-4745-88a5-63852f98d819)

* 实例

![Screenshot 2025-03-03 at 21 57 38](https://github.com/user-attachments/assets/60cbe4e8-431c-4870-96ae-a8f97c88e3f2)
![Screenshot 2025-03-03 at 21 57 49](https://github.com/user-attachments/assets/7abab908-7eca-40f1-a443-e4e66c73bc6a)

### 代码随想录：双指针解法（对齐最后一个node后，一个一个迭代对比是否有相同的node）
* 第一阶段：遍历两个链表，计算长度，O(m + n)。
* 第二阶段：对齐长链表，最多移动 |m - n| 次，O(|m - n|)。
* 第三阶段：同步遍历找交点，O(min(m, n))。
* 综上，总时间复杂度仍然是 O(m + n)。
* 只使用了几个指针和整数变量，没有额外的数据结构，因此 O(1)（常数级）。

```java
public class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode curA = headA;
        ListNode curB = headB;
        int lenA = 0, lenB = 0;
        while (curA != null) { // 求链表A的长度
            lenA++;
            curA = curA.next;
        }
        while (curB != null) { // 求链表B的长度
            lenB++;
            curB = curB.next;
        }
        curA = headA;
        curB = headB;
        // 让curA为最长链表的头，lenA为其长度
        if (lenB > lenA) {
            //1. swap (lenA, lenB);
            int tmpLen = lenA;
            lenA = lenB;
            lenB = tmpLen;
            //2. swap (curA, curB);
            ListNode tmpNode = curA;
            curA = curB;
            curB = tmpNode;
        }
        // 求长度差
        int gap = lenA - lenB;
        // 让curA和curB在同一起点上（末尾位置对齐）
        while (gap-- > 0) {
            curA = curA.next;
        }
        // 遍历curA 和 curB，遇到相同则直接返回
        while (curA != null) {
            if (curA == curB) {
                return curA;
            }
            curA = curA.next;
            curB = curB.next;
        }
        return null;
    }

}
```
* 原理

![Screenshot 2025-03-03 at 22 01 37](https://github.com/user-attachments/assets/3f005f40-ac68-4039-8e73-7db03ce6ea50)

## 142. Linked List Cycle II
* https://leetcode.com/problems/linked-list-cycle-ii/
* 文章讲解： https://programmercarl.com/0142.%E7%8E%AF%E5%BD%A2%E9%93%BE%E8%A1%A8II.html
* 视频讲解： https://www.bilibili.com/video/BV1if4y1d7ob/
* 用快慢指针判断是否有环（如果相遇，就有）：操场问题
* 快指针每次走两个节点，慢指针每次走一个
* 相遇判断有环以后怎么知道环开始的位置在哪里呢？
* ![Screenshot 2025-03-03 at 22 23 57](https://github.com/user-attachments/assets/567b7c29-4114-44f3-9d03-80d7dcd75a22)
* 那么相遇时： slow指针走过的节点数为: x + y， fast指针走过的节点数：x + y + n (y + z)，n为fast指针在环内走了n圈才遇到slow指针， （y+z）为 一圈内节点的个数A。
* 因为fast指针是一步走两个节点，slow指针一步走一个节点， 所以 fast指针走过的节点数 = slow指针走过的节点数 * 2：
* (x + y) * 2 = x + y + n (y + z)
* 两边消掉一个（x+y）: x + y = n (y + z)
* 因为要找环形的入口，那么要求的是x，因为x表示 头结点到 环形入口节点的的距离。
* 所以要求x ，将x单独放在左面：x = n (y + z) - y ,
* 去掉负数的y
* 再从n(y+z)中提出一个 （y+z）来，整理公式之后为如下公式：x = (n - 1) (y + z) + z 注意这里n一定是大于等于1的，因为 fast指针至少要多走一圈才能相遇slow指针。
* 也就是说，两个指针一个从起点出发（a)，一个从相遇点出发(b)，b在绕了（n-1)圈以后再走z，一定会在环开始处与a相遇
```java
public class Solution {
    public ListNode detectCycle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null){//注意这里的条件
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow){//有环
                ListNode a = head;
                ListNode b = fast;
                while (a != b){
                    a = a.next;
                    b = b.next;
                }
                return a;
            }
        }
        return null;//如果没有环，fast.next 将 ==null
        
    }
}
```
### 总结
* 交叉链表和环形链表可以使用同样的解
* 一般涉及到 增删改操作，用虚拟头结点都会方便很多， 如果只能查的话，用不用虚拟头结点都差不多。 

