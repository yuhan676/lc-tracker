## 198. House Robber打家劫舍
* https://leetcode.com/problems/house-robber/description/
* 文章：https://programmercarl.com/0198.%E6%89%93%E5%AE%B6%E5%8A%AB%E8%88%8D.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 视频：https://www.bilibili.com/video/BV1Te411N7SX?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 和爬楼梯比较像：当前房间能不能偷取决于前一个和前两个房间有没有被偷
1. dp数组的含义：下标i，所能偷的（包含下标i和之前的房间）最大的金币为dp[i]； dp.size = nums.size
2. 递推公式：偷i和不偷i取最大值。偷的话就加上当前的物品价值和到前前个物品能偷的最大价值（dp[i]=nums[i] + dp[i-2]),不偷i就(dp[i] = dp[i-1]),两者取最大值
3. 数组初始化： dp[0]：考虑物品下标0能偷的最大的，肯定就是一定得偷物品0； dp[1] =考虑物品0和物品1，取最大值就好：max(nuns[0], nums[1])。非0和非1下标初始成0就好，反正之后会被覆盖
4. 遍历顺序：需要用到i-1, i-2,所以从左到右遍历
5. 推导dp数组：![image](https://github.com/user-attachments/assets/af522d8e-cfaa-484c-bdba-f98c1ab8626e)
```java
class Solution {
    public int rob(int[] nums) {
        //大小就是nums size，因为我们要考虑到偷到最后一家的最大值是多少
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        if (nums.length ==1) return dp[0];
        dp[1] = Math.max(nums[0],nums[1]);
        for (int i =2; i<nums.length; i++){
            dp[i] = Math.max(nums[i]+dp[i-2], dp[i-1]);
        }
        return dp[nums.length-1];
    }
}
```
## 213. House Robber II
* https://leetcode.com/problems/house-robber-ii/description/
* 文章：https://programmercarl.com/0213.%E6%89%93%E5%AE%B6%E5%8A%AB%E8%88%8DII.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 视频：https://www.bilibili.com/video/BV1oM411B7xq?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 和打家劫舍1不一样的地方：nums连成环了，首尾可能相邻
* 一开始我以为只有nums.length==奇数我才需要处理成环问题，但是其实如果看一看这个反例：nums = [100, 1, 1, 100]，不考虑成环问题，会选择两个100， 就会导致相邻的两个房子被偷
* 3种情况解决环的问题
1. 把首尾都不考虑，就考虑中间的所有数字，就能当成一个一维数组看待,和打家劫舍1一样处理
```
1 6 1 9 1
```
2. 考虑所有元素，除了尾元素
3. 考虑所有元素，除了首元素
* 仔细看看，其实情况2和3都包含了情况1（因为没说一定要选择首或者尾元素！只是说会考虑它们）
* 这两种情况分别开一个dp，看哪个的最后一位能给出最大值就行

```java
class Solution {
    public int rob(int[] nums) {
        //特殊判断
        if (nums.length==1) return nums[0];
        if (nums.length==2) return Math.max(nums[0],nums[1]);
        //不考虑尾元素
        //注意分辨两种情况中dp的最后一个数是哪个
        int dp1[] = new int[nums.length-1];
        dp1[0] = nums[0];
        dp1[1] = Math.max(nums[0],nums[1]);
        for (int i = 2; i<nums.length-1;i++){
            dp1[i] = Math.max(nums[i]+dp1[i-2], dp1[i-1]);
        }
        //不考虑头元素
        int dp2[] = new int[nums.length];
        dp2[0] = 0;
        dp2[1] = nums[1];
        dp2[2] = Math.max(nums[1],nums[2]);
        for (int i =3; i<nums.length;i++){
            dp2[i] = Math.max(nums[i]+dp2[i-2], dp2[i-1]);
        }

        return Math.max(dp1[nums.length-2], dp2[nums.length-1]);
    }
}
```
## 337. House Robber III 二叉树打家劫舍
* https://leetcode.com/problems/house-robber-iii/description/
* 文章：https://programmercarl.com/0337.%E6%89%93%E5%AE%B6%E5%8A%AB%E8%88%8DIII.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 视频：https://www.bilibili.com/video/BV1H24y1Q7sY?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 递归三部曲+动态规划五部曲
1. dp数组的定义：每个节点只能有两个状态：偷所获得的最大金额dp[1]/不偷所获得的最大金额dp[0] （递归每一层会保存每一层的状态，所以不用每层去初始化），这样就能保存每个节点，偷和不偷的最大结果
* 使用后序遍历左右后：因为我们要从底向上：最后根节点：偷与不偷取一个最大值
递归三部曲
1. 参数：treenode，返回值：一维数组dp result（当前节点偷与不偷的状态）（第0个是不偷的最大值，第1个是偷的最大值）
2. 终止条件：如果当前是空间点，return int[] {0,0} 偷与不偷都是0
3. 递归遍历：先找到左孩子偷与不偷的最大值，右孩子偷与不偷的最大值
4. 再计算：偷当前节点的价值value1：当前节点价值+不偷左孩子的价值+不偷右孩子的价值 （记得我们是后序遍历，所以是左右后）；不偷当前的话，就得看左右孩子偷还是不偷能给出最大的值，再选择偷不偷左右孩子：value2 = max(leftdp[0], leftdp[1])+max(rightdp[0],rightdp[1])
5. return {value 2, value 1}
* 这题还有暴力递归和记忆化递推的方法，但我目前只学了递归三部曲+动态规划版本，比较好理解
### 代码随想录动态规划解法
```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public int rob(TreeNode root) {
        int[] res = robTree(root);
        return Math.max(res[0], res[1]);
    }
    private int[] robTree(TreeNode root){
        int[] res = new int[2];
        if (root==null){
            return res;
        }
        //后序遍历左右后
        int[] leftdp = robTree(root.left);
        int[] rightdp = robTree(root.right);
        //不偷当前节点，左右节点偷不偷取决于究竟偷还是不偷会给最大成果
        res[0] = Math.max(leftdp[0],leftdp[1]) + Math.max(rightdp[0],rightdp[1]);
        //偷当前节点：当前节点价值+左右孩子都不偷的价值
        res[1] = root.val + leftdp[0] + rightdp[0];
        return res;
    }
}
```
