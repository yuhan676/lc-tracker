## 669. Trim a Binary Search Tree
* https://leetcode.com/problems/trim-a-binary-search-tree/
* 文章： https://programmercarl.com/0669.%E4%BF%AE%E5%89%AA%E4%BA%8C%E5%8F%89%E6%90%9C%E7%B4%A2%E6%A0%91.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 视频：https://www.bilibili.com/video/BV17P41177ud?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 给定一个数值区间，修建二叉树直到所有node的值都在这个区间内（左闭右闭）
* 但是如果一个node不符合，它的子树却有可能符合，所以不能直接if node > high or node < low return null
![Screenshot 2025-03-20 at 13 27 43](https://github.com/user-attachments/assets/4b467da7-68ab-4081-bdfa-c3f6224e8295)
* 将不符合的节点的孩子直接赋给符合要求的父节点，作为孩子节点
![Screenshot 2025-03-20 at 14 02 49](https://github.com/user-attachments/assets/39a24e72-2395-4dc1-93bc-fe9cfaf18c9f)
* 递归三部曲
  * 参数和返回值：
    * 参数：root，low，high
    * 返回值：treenode（不用返回值也可以完成修剪，但是有返回值可以更方便地通过返回值来移除节点）
  * 确定终止条件： 遇到空节点返回（修建的操作并不是在终止条件上进行）
  * 单层递归逻辑
    * 如果root<low, 递归root.right, 返回右子树符合条件的头节点
    * 如果root>high, 递归root.left, 返回左子树符合条件的头节点
    * 将下一层处理完左子树的结果赋值给root.left, 处理右子树的结果赋值给root.right (把下层返回的符合区间的node接住）
    * return root
    * （这种情况下如果node符合区间，会直接return root）
* **之后自己试着独立写一下，逻辑有点绕**
### 递归解法
```java
class Solution {
    public TreeNode trimBST(TreeNode root, int low, int high) {
        //递归到空了就返回空
        if (root==null) return null;
        if (root.val < low){
            TreeNode right = trimBST(root.right,low,high);
            return right;
        }
        //如果root大于high，就往root的左子树找，找到符合的节点就返回
        if (root.val>high){
            TreeNode left = trimBST(root.left,low,high);
            return left;
        }
        // root在[low,high]范围内，分别递归左右子树，确保它们也在范围内
        root.left = trimBST(root.left,low,high);
        root.right = trimBST(root.right,low,high);
        //在范围内的root直接返回
        return root;
    }
}
```
## 108. Convert Sorted Array to Binary Search Tree
* https://leetcode.com/problems/convert-sorted-array-to-binary-search-tree/description/
* 文章：https://programmercarl.com/0108.%E5%B0%86%E6%9C%89%E5%BA%8F%E6%95%B0%E7%BB%84%E8%BD%AC%E6%8D%A2%E4%B8%BA%E4%BA%8C%E5%8F%89%E6%90%9C%E7%B4%A2%E6%A0%91.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 视频：https://www.bilibili.com/video/BV1uR4y1X7qL?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
### 我的解法：复制数组
* 寻找升序数组的中间点，作为头节点，并且切割nums
* 递归左半边nums作为左边节点，右半边nums作为右边节点
* 可以提升的地方：
  * Arrays.copyOfRange会不停地复制数组，每次递归都会创建一个新的数组，导致 O(N log N) 的额外空间开销！
  * 如果 nums 很大，会导致 超时（TLE time limit exceeded） 或 内存溢出（MLE memory limit exceeded）。
* 时间，空间复杂度都是	O(N log N) 因为要复制数组
```java
class Solution {
    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums.length == 0) return null;
        int mid = nums.length/2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = sortedArrayToBST(Arrays.copyOfRange(nums,0,mid));
        root.right = sortedArrayToBST(Arrays.copyOfRange(nums,mid+1, nums.length));
        return root;
    }
}
```
### 优化以后的方法：不复制数组，直接找mid的位置
* 时间复杂度O(n)，因为每一个节点都会被遍历到
* 空间复杂度O(log n)
![Screenshot 2025-03-20 at 14 57 46](https://github.com/user-attachments/assets/890a9f9b-9e0f-44c1-8600-8095a30c2996)

```java
class Solution {
    public TreeNode sortedArrayToBST(int[] nums) {
        return buildBST(nums, 0, nums.length-1);
    }
    //这里的left， right属于左闭右闭，因为left==right的时候我们可以取左也可以取右
    private TreeNode buildBST(int[] nums,int left, int right){
        if (left > right) return null;
        int mid = left + (right-left)/2;
        TreeNode root = new TreeNode(nums[mid]);
        //因为是左闭右闭，所以left right递归的时候不要碰到mid
        root.left = buildBST(nums, left, mid-1);
        root.right = buildBST(nums,mid+1,right);
        return root;
    }
}
```
## 538. Convert BST to Greater Tree
* https://leetcode.com/problems/convert-bst-to-greater-tree/description/
* 文章：https://programmercarl.com/0538.%E6%8A%8A%E4%BA%8C%E5%8F%89%E6%90%9C%E7%B4%A2%E6%A0%91%E8%BD%AC%E6%8D%A2%E4%B8%BA%E7%B4%AF%E5%8A%A0%E6%A0%91.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 视频：https://www.bilibili.com/video/BV1d44y1f7wP?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
### 我最开始的想法
* 先中序遍历计算所有节点之和，存入global var sum
* 再中序遍历一次，每次到达新节点就更新accumulative。然后sum - accumulative，更新node的值
* 但是我在写的时候没搞清楚怎么维护accumulative,而且其实应该另起一个inorderconvert 返回void的递归函数来改变值
```java
class Solution {
    int sum = 0;
    int accum = 0;
    public TreeNode convertBST(TreeNode root) {
        inOrder(root);

        if (root==null) return null;
        convertBST(root.left);
        accum += root.val; 
        int res = sum - accum + root.val;
        root.val = res;

        convertBST(root.right);

        return root;
    }
    private void inOrder(TreeNode node){
        if (node==null) return;
        inOrder(node.left);
        sum += node.val;
        inOrder(node.right);
    }
}
```
* 改bug以后
```java
class Solution {
    int sum = 0;
    int accum = 0;
    public TreeNode convertBST(TreeNode root) {
        inOrderSum(root);
        inOrderConvert(root);
        return root;
    }
    private void inOrderSum(TreeNode node){
        if (node==null) return;
        inOrderSum(node.left);
        sum += node.val;
        inOrderSum(node.right);
    }
    private void inOrderConvert(TreeNode node){
        if (node == null) return;
        inOrderConvert(node.left);
        //先更新accum
        accum += node.val;
        //再计算需要更新的数值（记得计算res的值的时候需要包含nodeval
        int res = sum - (accum-node.val);
        node.val = res;
        inOrderConvert(node.right);
    }
}
```
### reverse中序遍历
<img width="509" alt="Screenshot 2025-03-20 at 16 12 45" src="https://github.com/user-attachments/assets/ca1bc30d-6313-451e-9a6f-098bfae339ee" />
* 观察着一张图不难看出，从大到小遍历（reverse中序遍历右中左（就只是一个sum += node.val的过程
* 可以直接赋值，不用遍历两遍
```java
class Solution {
    int sum=0;
    public TreeNode convertBST(TreeNode root) {
        reverseInorder(root);
        return root;
    }

    private void reverseInorder(TreeNode node){
        if (node==null) return;
        reverseInorder(node.right);
        sum += node.val;
        node.val = sum;
        reverseInorder(node.left);
    }
}
```
# 二叉树总结
* 文章： https://programmercarl.com/%E4%BA%8C%E5%8F%89%E6%A0%91%E6%80%BB%E7%BB%93%E7%AF%87.html#%E6%B1%82%E4%BA%8C%E5%8F%89%E6%A0%91%E7%9A%84%E5%B1%9E%E6%80%A7
* 总结了各个不同的题的套路，递归顺序
