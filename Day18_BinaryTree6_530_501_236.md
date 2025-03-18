## 530. Minimum Absolute Difference in BST
* https://leetcode.com/problems/minimum-absolute-difference-in-bst/description/
* 文章：https://programmercarl.com/0530.%E4%BA%8C%E5%8F%89%E6%90%9C%E7%B4%A2%E6%A0%91%E7%9A%84%E6%9C%80%E5%B0%8F%E7%BB%9D%E5%AF%B9%E5%B7%AE.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 视频：https://www.bilibili.com/video/BV1DD4y11779?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 我的设想
  * node.val一定大于0，所以我们不用处理二叉搜索树两边对比的情况
  * 使用中序遍历左中右排开树节点的值，就能得到一个升序数组
  * 比较每一个元素和前面的元素的差，记录最小差就可以了
### 我的解法
* 但是这样需要On的额外空间来储存arraylist
```java
class Solution {
    List<Integer> nodeVal = new ArrayList<>();
    int minD = Integer.MAX_VALUE;
    public int getMinimumDifference(TreeNode root) {
        getTreeArray(root);
        int i = 1;
        while (i<nodeVal.size()){
            minD = Math.min(minD, nodeVal.get(i)-nodeVal.get(i-1));
            //这里记得手动i++
            i++;
        }
        return minD;
    }
    //中序遍历
    private void getTreeArray(TreeNode node){
        if (node==null) return;
        getTreeArray(node.left);
        nodeVal.add(node.val);
        getTreeArray(node.right);
    }
}
```
### 代码随想录解法
* 不使用数组记录，而是用一个pre pointer，在中序遍历中记录上一个节点的位置，就可以对比curr和pre的值，搜索和记录最小差
* 我一开始写的错误代码
```java
class Solution {
    int minD = Integer.MAX_VALUE;
    TreeNode pre;
    public int getMinimumDifference(TreeNode root) {
        inOrder(root);
        return minD;
    }
    private void inOrder(TreeNode root){
        if (root==null) return;
        inOrder(root.left);
        //这里还没有更新minD就更新了pre，更新的时机不对
        //我们应该要处理pre==null的情况
        pre = root;
        inOrder(root.right);
        minD = Math.min(minD, root.val-pre.val);
    }
}
```
* 递归三部曲
  * 递归参数：root，返回值：void（因为只需要修改minD)
  * 终止条件：如果root==null，返回
  * 单层递归逻辑
    * 先往左遍历
    * 如果pre != null, 更新minD
    * 更新pre到当前值
    * 往右遍历
* 修改bug以后得到
```java
class Solution {
    int minD = Integer.MAX_VALUE;
    TreeNode pre = null;
    public int getMinimumDifference(TreeNode root) {
        inOrder(root);
        return minD;
    }
    private void inOrder(TreeNode root){
        if (root==null) return;
        inOrder(root.left);
        //注意这是一个ifcheck不是whilecheck
        if (pre!=null){
            minD = Math.min(minD, root.val-pre.val);
        }
        //更新完mind再来更新pre
        pre = root;
        inOrder(root.right);
    }
}
```
## 501. Find Mode in Binary Search Tree
* https://leetcode.com/problems/find-mode-in-binary-search-tree/description/
* 文章：https://programmercarl.com/0501.%E4%BA%8C%E5%8F%89%E6%90%9C%E7%B4%A2%E6%A0%91%E4%B8%AD%E7%9A%84%E4%BC%97%E6%95%B0.html
* 视频：https://www.bilibili.com/video/BV1fD4y117gp?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
*一般二叉搜索树中不允许重复的元素，但是这道题重新定义了
* 我的想法：
  * 先中序遍历输出树的值为一个数组，然后遍历这个数组，用map记录每一个元素的频率
  * 找到最大的频率，再找到相应的元素
  * 把符合的元素放在一个数组里输出
### 我的解法
* 这个解法在任何一个二叉树都可以用
* 但是我们也可以用二叉搜索树的特性给，写出代码随想录的写法
```java
class Solution {
    List<Integer> treeList = new ArrayList<>();
    public int[] findMode(TreeNode root) {
        inOrder(root);
        Map<Integer,Integer> map = new HashMap<>();
        int i = 0;
        while (i < treeList.size()){
            int element = treeList.get(i);
            map.put(element, map.getOrDefault(element,0)+1);
            i++;
        }
        //找到最大频率
        //学习点：用Collections.max
        int maxFreq = Collections.max(map.values());

        //构建一个list来储存modes
        List<Integer> modes = new ArrayList<>();
        //学习：如何遍历map entry
        for (Map.Entry<Integer,Integer> entry: map.entrySet()){
            if (entry.getValue() == maxFreq){
                modes.add(entry.getKey());
            }
        }
        //学习：list转array
        return modes.stream().mapToInt(x->x).toArray();


    }

    private void inOrder(TreeNode node){
        if(node==null) return;
        inOrder(node.left);
        treeList.add(node.val);
        inOrder(node.right);
    }
}
```
### 代码随想录双指针解法
* 因为是二叉搜索树，说明用中序遍历出来的数组一定是从小到大，且数值相同的元素是排在一起的
* 这样的话只要
  * 初始化count和maxcount
  * 在pre不等于null的情况下，检查curr和pre的数值相不相等，如果相等则count++，如果不相等则count=1
  * 检查count是否比maxcount大，如果大，清空reslist，把value放进去
  * 如果相等，那就把value直接加入reslist
* 递归三部曲
  * 参数：root，返回值：void（只需要修改count和maxcount
  * 终止条件：root==null, return
  * 单层逻辑
    * 左子树遍历
    * 检查pre是否为null，node的值是否==pre的值
    * 如果不相等，set count = 1
    * 如果相等，count ++
    * 检查count>maxcount
      * 如果是的，清空reslist，添加node.val, 更新maxcount
    * 检查count==maxcount
      * res添加node val
    * 更新 pre=root
    * 右子树遍历
```java
class Solution {
    List<Integer> resList;
    int maxCount;
    int count;
    TreeNode pre;

    public int[] findMode(TreeNode root) {
        resList = new ArrayList<>();
        maxCount = 0;
        count = 0;
        pre = null;

        inOrder(root);
        //另一种写法
        //return resList.stream().mapToInt(i->i).toArray();
        int[] res = new int[resList.size()];
        for (int i=0; i<resList.size(); i++){
            res[i] = resList.get(i);
        }
        return res;
        
    }

    private void inOrder(TreeNode node){
        if (node==null) return;
        inOrder(node.left);
        if (pre!=null && node.val != pre.val){
            count = 1;
        }else{
            count++;
        }

        if (count>maxCount){
            resList.clear();
            resList.add(node.val);
            maxCount = count;
        }else if (count==maxCount){
            resList.add(node.val);
        }

        pre = node;

        inOrder(node.right);
    }
}
```
## 236. Lowest Common Ancestor of a Binary Tree
* https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/description/
* 文章：https://programmercarl.com/0236.%E4%BA%8C%E5%8F%89%E6%A0%91%E7%9A%84%E6%9C%80%E8%BF%91%E5%85%AC%E5%85%B1%E7%A5%96%E5%85%88.html#%E6%80%9D%E8%B7%AF
* 视频：https://www.bilibili.com/video/BV1jd4y1B7E2?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 递归思路：
  * 后序遍历左右后：向左向右遍历，找到p就返回p，找到q就返回q，没找到就返回null
  * 第一种情况（p，q不是最近公共祖先）：如果左子树和右子树都返回非null，说明节点就是最近公共祖先
  * 第二种情况（p或者q可能是最近公共祖先）： 可以让代码把两种情况都处理到:如果我们遍历到了q但是p还在下面，还没有找到，直接返回q
* 递归三部曲
  * 参数：root，p，q； 返回值：node最近公共祖先
  * 终止条件
    * 翻到头root==null没找到：返回null
    * 找到p，返回p
    * 找到q返回q
  * 单层循环逻辑
    * 后序遍历：先检查左，再检查右，检查分别的返回值
    * 如果左边null，右边null，返回null
    * 左边非null，右边null，返回左边
    * 右边非null，左边null，返回右边
    * 左右都非null（找到了！），返回node（返回到上一层以后，左一定为null，右边就是非null的找到的节点，一层一层返回到顶层）
```java
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root==null || root==p || root == q) return root;

        TreeNode left = lowestCommonAncestor(root.left, p,q);
        TreeNode right = lowestCommonAncestor(root.right,p,q);

        if (left == null && right == null) {return null;}
        else if (left != null && right == null) {return left;}
        else if (left ==null && right != null) {return right;}
        else {return root;}
    }
}
```
 


 


