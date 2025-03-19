## 235. Lowest Common Ancestor of a Binary Search Tree
* https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/description/
* 文章：https://programmercarl.com/0235.%E4%BA%8C%E5%8F%89%E6%90%9C%E7%B4%A2%E6%A0%91%E7%9A%84%E6%9C%80%E8%BF%91%E5%85%AC%E5%85%B1%E7%A5%96%E5%85%88.html
* 视频：https://www.bilibili.com/video/BV1Zt4y1F7ww?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
### 迭代法
  * 我们知道肯定存在node，是pq的公共祖先
  * 二叉搜索树有序：如果node.val > p & node.val >q, 那就要向node的左子树搜索； 如果node.val比pq都要小，就要向node的右子树去搜索
  * 最后要么是p<node<q, 要么是q<node<p，这种情况之下node就是pq的最近公共祖先，返回node就可以了
```java
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        while (root!=null){
            if (root.val < p.val && root.val < q.val){
            root = root.right;
            }
            else if (root.val > p.val && root.val >q.val){
                root = root.left;
            }
            else return root;
        }
        return null;//因为我们一定会找到，所以应该不会走到这一步
    }
}
```
### 递归法
* 递归三部曲
  * 返回值和参数：返回treenode，参数：node, p, q (三个树节点）
  * 终止条件：if node==null, 返回node（我们一定会找到公共祖先所以其实不需要这个终止条件）
  * 单层递归逻辑
    * 三个情况
      * node >p & q, 查找node.left
      * node < p&q, 查找node.right
      * p<node<q || q<node<p， return node（找到了！）
    * 想好这三个情况，那么第一二种情况只需要检查left, right是否为空，不为空的话就是下层的正确答案返回上来了，我们只要接着把它传递向上层即可
```java
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root.val >p.val && root.val >q.val){
            TreeNode left = lowestCommonAncestor(root.left, p,q);
            if (left!=null) return left;
        }
        else if (root.val<p.val && root.val <q.val){
            TreeNode right = lowestCommonAncestor(root.right,p,q);
            if(right !=null) return right;
        }
        else return root;
        return null;
    }
    
}
```
## 701. Insert into a Binary Search Tree
* https://leetcode.com/problems/insert-into-a-binary-search-tree/description/
* 文章：https://programmercarl.com/0701.%E4%BA%8C%E5%8F%89%E6%90%9C%E7%B4%A2%E6%A0%91%E4%B8%AD%E7%9A%84%E6%8F%92%E5%85%A5%E6%93%8D%E4%BD%9C.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 视频：https://www.bilibili.com/video/BV1Et4y1c78Y?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 二叉搜索树插入任意节点都可以在叶子节点实行，所以比较简单
* 递归三部曲
  * 传入二叉树根节点+插入的数值，返回值：新的二叉树的根节点
  * 终止条件： 如果当前节点root==null，找到插入节点的位置了：新建节点newnode，传入val数值； return newnode向上返回newnode
  * 单层循环逻辑
    * 我们想把newnode和前面的父节点连接起来
    * 假设我们已经递归到1（叶子），要加入0，0比1小，所以1的左子节点就是newnode
    * if val<root.val, root.left = insert(root.left,val) ->就是说1的左子节点现在是newnode0了，现在返回1-0给上一层递归，1-0就是2的左子树内容了
    * 右边同理，如果val>root.val, root.right = insert(root.right,val)
    * 全部递归返回完毕以后，root就是新的二叉搜索树的root了，直接return root （这点我自己写的时候没有想明白）
![Screenshot 2025-03-19 at 22 16 58](https://github.com/user-attachments/assets/c99d8b5c-8686-49cc-af6a-f65896a1fb15)
### 递归法
```java
class Solution {
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root==null){
            TreeNode newn = new TreeNode(val);
            return newn;
        }
        if (root.val > val ) {
            root.left = insertIntoBST(root.left,val);
        }
        if (root.val < val){
            root.right = insertIntoBST(root.right,val);
        }
        return root;
    }
}
```
### 迭代法
* 使用parent和curr来保证我们可以把要插入的新节点和叶子节点连接起来
```java
class Solution {
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root==null){
            TreeNode node = new TreeNode(val);
            return node;
        }
        //用parent来记录要加入插入节点的位置
        TreeNode cur = root;
        TreeNode parent = null;
        while (cur!=null){
            //更新parent
            parent=cur;
            //更新cur
            //这里不要用两个if check，因为两个ifcheck会再cur更新以后继续检查，用if else比较合适
            if (val > cur.val) cur = cur.right;
            else cur = cur.left;
        }
        //走到尾，parent是叶子节点，cur是正确的应该填写val的空节点
        //新建这个要插入的节点
        TreeNode newnode = new TreeNode(val);
        //检查和parent之间的关系
        if (val < parent.val) parent.left = newnode;
        else  parent.right = newnode;
        
        return root;
    }
}
```
## 450. Delete Node in a BST
* https://leetcode.com/problems/delete-node-in-a-bst/description/
* 文章：https://programmercarl.com/0450.%E5%88%A0%E9%99%A4%E4%BA%8C%E5%8F%89%E6%90%9C%E7%B4%A2%E6%A0%91%E4%B8%AD%E7%9A%84%E8%8A%82%E7%82%B9.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 视频：https://www.bilibili.com/video/BV1tP41177us?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 二叉搜索树中删除节点有很多种情况，所以比插入节点要难
* 二叉搜索树删除：递归三部曲
  * 参数：root，key（需要删除的节点的值）
  * 终止条件：如果没有找到要删除的节点if root==null, return root;直接返回
  * 单层递归逻辑
    * 五种情况
1. 没找到删除的节点，遍历到空节点直接返回
2. 左右孩子都为空，直接删除节点，返回null空节点为根节点
3. 左孩子为空，右孩子不为空：删除节点，右孩子补位，返回右孩子为根节点
4. 左孩子不为空，右孩子为空：删除节点，左孩子补位，返回左孩子为根节点
5. 左右孩子都不为空，删除节点的左子树头节点（左孩子）放到删除节点的右子树的最左边节点的左孩子上，返回删除节点又孩子为新的根节点 （画图理解会更简单）
![Screenshot 2025-03-19 at 22 46 38](https://github.com/user-attachments/assets/9dea0505-5a01-45cb-9375-ffd4318fcfad)
![Screenshot 2025-03-19 at 22 52 21](https://github.com/user-attachments/assets/1629243a-c0f9-4341-815d-982381ed2207)
### 递归解法
```java
class Solution {
    public TreeNode deleteNode(TreeNode root, int key) {
        //第一种情况：没找到
        if (root==null) return root;
        //假如我们找到了要删除的node
        if (root.val == key){
            //包含了2，3，4种情况：单个为空或者双双为空
            if (root.left==null) return root.right;
            else if (root.right ==null) return root.left;
            //第五种情况：两个子树都不为空
            else{
                //记录删除节点的左子树
                TreeNode left = root.left;
                //右子树为根节点，往左遍历到最左边没有左子树的节点
                TreeNode cur = root.right;
                while (cur.left!=null){
                    cur = cur.left;
                }
                //找到那个节点以后，把要删除节点的左子树按到这个节点的左子树上去
                cur.left = left;
                //因为删除了目标节点，目标节点的右子树补位
                return root.right;
            }
            }
        //通过递归来向左然后向后遍历，走到root.left去检查root.left.val是不是==key
        if (root.val >key) root.left = deleteNode(root.left,key);
        if (root.val <key) root.right = deleteNode(root.right,key);
        return root;
    }
}
```
### 普通二叉树删除方式（**没学懂，这里作为参考**）
```java
class Solution {
public:
    TreeNode* deleteNode(TreeNode* root, int key) {
        if (root == nullptr) return root;
        if (root->val == key) {
            if (root->right == nullptr) { // 这里第二次操作目标值：最终删除的作用
                return root->left;
            }
            TreeNode *cur = root->right;
            while (cur->left) {
                cur = cur->left;
            }
            swap(root->val, cur->val); // 这里第一次操作目标值：交换目标值其右子树最左面节点。
        }
        root->left = deleteNode(root->left, key);
        root->right = deleteNode(root->right, key);
        return root;
    }
};
```
