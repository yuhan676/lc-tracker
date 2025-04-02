# 动态规划 Dynamic Programming
## 理论基础
* 文章： https://programmercarl.com/%E5%8A%A8%E6%80%81%E8%A7%84%E5%88%92%E7%90%86%E8%AE%BA%E5%9F%BA%E7%A1%80.html#%E4%BB%80%E4%B9%88%E6%98%AF%E5%8A%A8%E6%80%81%E8%A7%84%E5%88%92
* 视频：https://www.bilibili.com/video/BV13Q4y197Wg?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
### 和贪心的区别
* 如果某一问题有很多重叠子问题，使用动态规划是最有效的。
* 所以动态规划中**每一个状态一定是由上一个状态推导出来的**，这一点就区分于贪心，**贪心没有状态推导，而是从局部直接选最优的**，
### 处理的问题
![image](https://github.com/user-attachments/assets/bdcfa453-38e0-40ae-89a8-366e959bfaed)
  * 斐波那契数列
  * 背包问题
  * 打家劫舍
  * 股票问题
  * 子序列问题：求最长递增子序列，最长连续递增子序列。。。
### 动态规划五部曲
* 理解dp数组以及下标的含义
* 写出递推公式/状态转移公式
* dp数组如何初始化：每题都不太一样
* 遍历顺序
* 打印dp数组（出现了问题以后便于debug）

* 为什么要先确定递推公式，然后在考虑初始化呢？
* 因为一些情况是递推公式决定了dp数组要如何初始化！

### debug时可以问自己的问题
* 这道题目我举例推导状态转移公式了么？
* 我打印dp数组的日志了么？
* 打印出来了dp数组和我想的一样么？

## 509. Fibonacci Number
* https://leetcode.com/problems/fibonacci-number/description/
* 文章：https://programmercarl.com/0509.%E6%96%90%E6%B3%A2%E9%82%A3%E5%A5%91%E6%95%B0.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 视频：https://www.bilibili.com/video/BV1f5411K7mo?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
### 我的解法：使用list记录所有的斐波那契数
```java
class Solution {
    public int fib(int n) {
        //这里学习一下怎么initialise list with content
        List<Integer> sequence = new LinkedList<Integer>(Arrays.asList(0,1));
        //如果我们要取F(2)，那么这个list里就要有3个元素（0，1，1），所以size是n+1，我们想要的元素的位置在index n
        while (sequence.size()<=n){
            sequence.add(sequence.get(sequence.size()-1) + sequence.get(sequence.size()-2));
        }
        return sequence.get(n);
    }
}
```
### 代码随想录解法：使用dp数组来保存递归结果
* 动规五部曲
1. 确定dp数组以及下标的含义
* dp[i] 的定义：第i个数的斐波那契数值是dp[i]
2. 确定递推公式
* 题目已经把递推公式给我们了： 状态转移方程 dp[i] = dp[i - 1] + dp[i - 2];
3. dp数组如何初始化
* 题目中把如何初始化也直接给我们了，dp[0] = 0; dp[1] = 1;
4. 确定遍历顺序
* 从递归公式dp[i] = dp[i - 1] + dp[i - 2];中可以看出，dp[i]是依赖 dp[i - 1] 和 dp[i - 2]，那么遍历的顺序一定是从前到后遍历的
5. 举例推导dp数组
* 按照这个递推公式dp[i] = dp[i - 1] + dp[i - 2]，我们来推导一下，当N为10的时候，dp数组应该是如下的数列：0 1 1 2 3 5 8 13 21 34 55
* 时间复杂度：O(n)
* 空间复杂度：O(n)

```java
class Solution {
    public int fib(int n) {
        //初始化dp数组和开头的元素
        int[] dp = new int[n+1];
        dp[0] = 0;
        dp[1] = 1;
        //用递推公式推导出数组中的值
        for (int i = 2; i<=n; i++){
            dp[i] = dp[i-1] + dp[i-2];
        }
        return dp[n];
    }
}
```

* 也可以只维护两个最后的数值
* 时间复杂度：O(n)
* 空间复杂度：O(1)

```java
class Solution {
    public int fib(int n) {
        if (n==0) return 0;
        //初始化dp数组和开头的元素
        int[] dp = new int[n+1];
        dp[0] = 0;
        dp[1] = 1;
        //用递推公式推导出数组中的值
        for (int i = 2; i<=n; i++){
            int temp = dp[1];
            dp[1] = dp[0] +dp[1];
            dp[0] = temp;
        }
        return dp[1];
    }
}
```
## 70. Climbing Stairs
* https://leetcode.com/problems/climbing-stairs/description/
* 文章：https://programmercarl.com/0070.%E7%88%AC%E6%A5%BC%E6%A2%AF.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 视频：https://www.bilibili.com/video/BV17h411h7UH?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
### 我的解法
* 手写模拟n=1，2，3，4，5的时候有多少种走法
* n=1, 1种走法：1
* n=2, 2种走法：[1,1],[2]
* n=3, 3种走法：[1,1,1],[1,2],[2,1]
* n=4, 5种走法：[1,1,1,1],[1,1,2],[1,2,1],[2,1,1],[2,2]
* n=5, 8种走法：[1,1,1,1,1],[1,2,1,1],[1,1,2,1],[1,1,1,2],[2,1,1,1],[1,2,2],[2,1,2],[2,2,1]
* 1,2,3,5,8,就是一个从1开始的斐波那契数列
* 那就和前一题几乎一模一样了
```java
class Solution {
    public int climbStairs(int n) {
        //初始化dp数组
        int[] dp = new int[n+1];
        dp[0] = 1;
        dp[1] = 2;
        //递推公式是一样的
        for (int i = 2; i<=n; i++){
            dp[i] = dp[i-1] + dp [i-2];
        }
        return dp[n-1];
    }
}
```
### 代码随想录的推导
* 从dp[i]的定义可以看出，dp[i] 可以有两个方向推出来。

* 首先是dp[i - 1]，上i-1层楼梯，有dp[i - 1]种方法，那么再一步跳一个台阶不就是dp[i]了么，这样的话就一共有dp[i - 1]种方法到达dp[i]
* 还有就是dp[i - 2]，上i-2层楼梯，有dp[i - 2]种方法，那么再一步跳两个台阶不就是dp[i]了么， 这样的话就有独立于前面dp[i - 1]种的dp[i - 2]种方法到达dp[i]
* 那么dp[i]就是 dp[i - 1]与dp[i - 2]之和！

* 另外代码随想录认为dp[0]不应该初始化为1，而应该直接从dp[1]初始化成1，更加符合题意，跳过dp[0]=0因为n>=1
### 拓展：把最大步数换成m，成为一个完全背包问题
* 卡码网57.爬楼梯：https://kamacoder.com/problempage.php?pid=1067
* ![Screenshot 2025-04-02 at 12 49 27](https://github.com/user-attachments/assets/5fb1026d-0efc-4d2c-b2d8-f4662669cf79)
* ![Screenshot 2025-04-02 at 12 50 39](https://github.com/user-attachments/assets/7c1f9644-75d2-40de-86b7-3f91a0633dbb)
* 简单来说，m就代表着斐波那契数列的最后m个数！之前等于2的时候就是把数列的最后两个数加起来得到新的最后的值，m==3的时候就是把最后3个加起来！
* 这个解法把m换成2就能ac力扣70爬楼梯
```java
class Solution {
    public int climbStairs(int n, int m) {
        int[] dp = new int[n+1];
        dp[0] = 1;
        for (int i =1; i<=n; i++){
            //从i开始可以走1，2，3....m步
            for (int j = 1; j<= m; j++){
                //如果 i-j >= 0，则从 i-j 级台阶跳 j 级到 i 级，把 dp[i-j] 的方案数加到 dp[i] 里。
                //否则，不能跳 j 级（因为 i-j 已经小于 0 了，相当于跳出了楼梯范围）。
                if (i-j>=0) dp[i] += dp[i-j];
            }
        }
        return dp[n];
    }
}
```
## 746. Min Cost Climbing Stairs
* https://leetcode.com/problems/min-cost-climbing-stairs/description/
* 文章：https://programmercarl.com/0746.%E4%BD%BF%E7%94%A8%E6%9C%80%E5%B0%8F%E8%8A%B1%E8%B4%B9%E7%88%AC%E6%A5%BC%E6%A2%AF.html#%E6%80%BB%E7%BB%93
* 视频：https://www.bilibili.com/video/BV16G411c7yZ?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 动态规划五部曲
1. dp数组含义：dp[i]意味着到达i台阶的时候最小的cost值
2. 递推公式：到达dp[2]有两种到达方式：从dp[0]开始走2步或者从dp[1]走1步，那走到2的cost就是dp[0] + cost[0]或者dp[1] +cost[1]，我们取这两个钟最小的就可以了
3. 初始化：题目说可以从第一个或者第0个开始，也就是说dp[0], dp[1]都是0
4. 遍历顺序：从前向后
5. 如有需求，打印dp数组
* 小难点：dp的size为cost+1，所以写for循环的时候终止条件是i==cost.size,要注意不要越界
```java
class Solution {
    public int minCostClimbingStairs(int[] cost) {
        //dp数组的长度需要记录到达终点时需要的总cost，终点的位置在cost中最后一个台阶的后面，所以length是cost.length +1
        int [] dp = new int[cost.length +1];
        //从第一步或者第0步走都没有cost
        dp[0] = 0;
        dp[1] = 0;
        //从dp[2]开始有两种到达方式：从dp[0]开始走2步或者从dp[1]走1步
        //我们想要最小化cost,就得记录到达每一个位置（包括终点）最小的cost
        //i==cost.length的时候，已经有cost.length+1个元素在dp里了，也就是说我们开始计算终点的mincost了
        for (int i =2; i<= cost.length; i++){
            // 当前的cost=前一步的cost+到达前一步所需要的cost
            dp[i] = Math.min(dp[i-1]+cost[i-1], dp[i-2]+cost[i-2]);
        }
        //返回dp中的最后一个值
        return dp[cost.length];
    }
}
```

