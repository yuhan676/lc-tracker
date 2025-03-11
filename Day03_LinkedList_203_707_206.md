# Linked List Day 1
## Linked list theory
* List linked together using index
代码随想录文章：https://programmercarl.com/%E9%93%BE%E8%A1%A8%E7%90%86%E8%AE%BA%E5%9F%BA%E7%A1%80.html#%E9%93%BE%E8%A1%A8%E7%9A%84%E7%B1%BB%E5%9E%8B


### Singly linked list 
![Screenshot 2025-02-28 at 19 04 06](https://github.com/user-attachments/assets/009113a4-f652-4b49-9261-df01ef22544b)
* Each node consists of 2 parts:
1. Data
2. Pointer to next node
   
### Doubly linked list
* Each node hsa 1 value and 2 pointers: prev & next
![Screenshot 2025-02-28 at 19 07 06](https://github.com/user-attachments/assets/2a4f9df5-8e36-4f36-a759-3e6c0c8369d0)
* 可以向前或者向后查询

### Circular linked list
* the last element points to the first element
![Screenshot 2025-02-28 at 19 08 43](https://github.com/user-attachments/assets/81a83cd9-7b48-4fe5-a089-521d1257cc2d)

### Memory storage of linked list
* non-contiguous storage (as opposed to contiguous storage of arrays)
* memory allocation depends on OS's memory management![Screenshot 2025-02-28 at 19 09 47](https://github.com/user-attachments/assets/897f4d13-a9a0-48fc-b3ef-b10b5c0b00cd)

### 定义链表
这里记录一下gpt给出的python和java各自定义链表的方法

**PYTHON单链表**
```python
class ListNode:
  def __init__(self, val=0, next=None):
    self.val = val
    self.next = next

node1 = ListNode(1)
node2 = ListNode(2)
node1.next = node2
```
**JAVA单链表**
```java
class ListNode {
  int val; //初始化节点值
  ListNode next; //next的type是另一个ListNode的实例

  //initialiser
  ListNode(int val) {
  this.val = val;
  this.next = null;
}

public class Main {
  public static void main(String[] args) {// 注意其实创建链表并没有用到参数args，
    // String[]只是用来接受用户command line input，但是没有用上，它只是java 规定的程序入口方法的标准写法
    ListNode node1 = new Listnode(1);
    ListNode node2 = new Listnode(2);
    node1.next = node2;

    //遍历链表
    ListNode curr = node1;
    while (curr != null) {
      System.out.print(curr.val + "->");
      curr = curr.next;
    }
    System.out.println("null");
  }
}
```
* 此处复习一下static method 是什么
* 属于class不属于instance，不能访问instance variable 或者instance method
* 可以访问其他static方法和static变量

**PYTHON双链表**
```python
class ListNode{
  def __init__(self, val = 0, next=None, prev=None):
    self.val = val
    self.next = next
    self.prev = prev//这里需要额外建立attribute prev

node1 = ListNode(1)
node2 = ListNode(2)
node3 = ListNode(3)

node1.next = node2
node2.next = node3
node3.prev = node2
node2.prev = node1
```
**JAVA双链表**
```java
class ListNode{
  int val;
  ListNode next;
  ListNode prev;

  ListNode(int val){
    this.val = val;
    this.next = null;
    this.prev = null;
  }
}
public class Main{
  public static void main(String[] args){
    Listnode node1 = Listnode(1);
    Listnode node2 = Listnode(2);
    Listnode node3 = Listnode(3);

    node1.next = node2;
    node2.next = node3;
    node3.prev = node2;
    node2.prev = node1;

    //遍历双向链表
    ListNode curr = node1;
    while (curr != null){
      System.out.print(curr.val "<->");
      curr = curr.next;
    }
  }
}
```
![Screenshot 2025-02-28 at 19 40 49](https://github.com/user-attachments/assets/41b8332c-329c-42fc-8062-2b3193bb9dde)

**代码随想录给出的java链表定义方式**
* 有三种不同构造方式：无参数，有一个参数，有两个参数
```java
public class ListNode {
    // 结点的值
    int val;

    // 下一个结点
    ListNode next;

    // 节点的构造函数(无参)
    public ListNode() {
    }

    // 节点的构造函数(有一个参数)
    public ListNode(int val) {
        this.val = val;
    }

    // 节点的构造函数(有两个参数)
    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}
```
### 链表的操作：删除和添加节点
* 因为只需要更新pointer，所以增添和删除都不需要像数组一样移动其他的值（O(n))，时间复杂度为O(1)
* 但是查找需要O(n)，因为得一个一个遍历来查找(数组中查找能一下就找到，所以数组的查询时间是O(1))
* 总结： 数组查询快，插入/删除慢； 链表查询慢，插入/删除快

## 203. Remove Linked List Elements
* https://leetcode.com/problems/remove-linked-list-elements/description/
* 讲解： https://programmercarl.com/0203.%E7%A7%BB%E9%99%A4%E9%93%BE%E8%A1%A8%E5%85%83%E7%B4%A0.html#%E6%80%9D%E8%B7%AF
### 虚拟头节点解法
* 看到题目第一想法：先得熟悉一下java中初始化链表的方式
* 实现过程中遇到的困难： 对java语法还不够熟悉，但是还记得虚拟头节点的解法所以很快ac了
```java
class Solution {
    //注意lc里面已经有定义listNode的类，没必要重新自己定义
    public ListNode removeElements(ListNode head, int val) {
        ListNode newhead = new ListNode(0,head);//这里记得要加一个new因为我们在initiate a new instance
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
```
## 707. Design Linked List
* https://leetcode.com/problems/design-linked-list/description/
* 讲解： https://programmercarl.com/0707.%E8%AE%BE%E8%AE%A1%E9%93%BE%E8%A1%A8.html

### 代码实现
* 难点1：后面几个和index有关的方法记得看题：他们想要的是indexth，也就是说加了虚拟头节点以后第1个index就是第一个node（真正的头）,要注意处理curr.next = null的情况
* 难点2:什么时候用for，什么时候用while分清楚（while可以用在在结尾加入节点，for用于在链表中间添加节点）
* 难点3:if check可以留给极端条件。就不需要另外加入else block给正常情况下需要运行的代码了
* 难点4：经常忘记ListNode是camel case

```java
class MyLinkedList {

    //💡可以在class里再加一个class作为构造器
    class ListNode{
        int val;
        ListNode next;
        ListNode(int val){
            this.val = val;
            this.next = null;//可省略这行，next自动成为null
        }
    }

    private ListNode dummyhead;//使用一个虚拟头
    private int size;

    public MyLinkedList() {//初始化链表
        this.size = 0;
        this.dummyhead = new ListNode(0);//这里设计成虚拟头主要是为了方便设计之后的method。当我们有一个虚拟头的时候，size为0
    }
    
    public int get(int index) {
        ListNode curr = dummyhead;
        if (index < 0 || index >= size){
            return -1;
        }else{
            for(int i = 0; i <= index; i++){
                curr = curr.next; //比如我们想找第0个节点但是因为第0个是dummyhead，所以返回第1个
        }
        return curr.val;
    }
    }
    
    public void addAtHead(int val) {
        ListNode newhead = new ListNode(val);
        newhead.next = dummyhead.next;
        dummyhead.next = newhead;
        size ++; //不要忘记处理size
    }
    
    public void addAtTail(int val) {
        //一开始我想的是使用for loop，但是用while更加保险
        ListNode newNode = new ListNode(val);
        ListNode curr = dummyhead;
        while (curr.next != null){
            curr = curr.next;//💡这里注意，到倒数第二以后它会继续iterate到倒数第一然后停止
        }
        curr.next = newNode;
        size ++; //记得处理size
    }
    
    public void addAtIndex(int index, int val) {
        ListNode newNode = new ListNode(val);

        //先处理返回空的情况
        if( index < 0 || index > size){
            return;
        }

            ListNode curr = dummyhead;
            //这里注意读题index是indexth node,所以第一个node的index就是1
            for (int i = 0; i < index; i ++){
                curr = curr.next;
            }
            newNode.next = curr.next;
            curr.next = newNode;
            size ++;
    }
    
    public void deleteAtIndex(int index) {
        if( index < 0 || index >= size){
            return;
        }
        ListNode curr = dummyhead;
        for (int i = 0; i<index; i++){
            curr = curr.next;
        }
        curr.next = curr.next.next;

        size --;
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
```

## 206. Reverse Linked List
* https://leetcode.com/problems/reverse-linked-list/description/
* 视频讲解： https://www.bilibili.com/video/BV1nB4y1i7eL/?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5
* 文章讲解： https://programmercarl.com/0206.%E7%BF%BB%E8%BD%AC%E9%93%BE%E8%A1%A8.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 先看了一遍视频就不会被双指针绕晕
* 诀窍：设立一个prev指针和curr指针，用temp来保存指针的值，方便移动
* 先想好终止条件，再画图理解prev和curr如何移动
* 递归的写法是基于双指针解法的 （这里只记录双指针）

* ### Double pointer solution
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
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null){//这里的条件是因为
            ListNode temp = curr.next;//新建一个temp来储存下一个节点
            curr.next = prev;//先调转next的指向
            prev = curr;//再更新curr 和temp的值
            curr = temp;
        }
        return prev;
    }
}
```
