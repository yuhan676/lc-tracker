## 完全背包理论基础
* 文章：https://programmercarl.com/%E8%83%8C%E5%8C%85%E9%97%AE%E9%A2%98%E7%90%86%E8%AE%BA%E5%9F%BA%E7%A1%80%E5%AE%8C%E5%85%A8%E8%83%8C%E5%8C%85.html#%E5%AE%8C%E5%85%A8%E8%83%8C%E5%8C%85
* 视频：https://www.bilibili.com/video/BV1uK411o7c9?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 卡码网52完全背包问题：https://kamacoder.com/problempage.php?pid=1052
### 完全背包和01背包的区别？
* 有N件物品和一个最多能背重量为W的背包。第i件物品的重量是weight[i]，得到的价值是value[i] 。每件物品都有无限个（也就是可以放入背包多次），求解将哪些物品装入背包里物品价值总和最大。
* 完全背包和01背包问题唯一不同的地方就是，每种物品有无限件。
* 递推函数也有两个状态：取物品i（取了一次value+=物品i devalue，剩下的重量还能再取物品i）和不取物品i（完全不包括，我们取这两种中value最大的可能性
* 所以和01背包的区别就是, 01背包取了物品i以后就不能在剩下的容量里包括物品i了，但是完全背包可以
  * 01背包递推函数：dp[i][j] = max(dp[i-1][j]， dp[i-1][j-weights[i]] + values[i)
  * 完全背包递推函数：dp[i][j] = max(dp[i-1][j], dp[i][j-weights[i]] + values[i)
### 卡玛网52完全背包2维数组解法
```java
import java.util.*;

public Class Main{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        //材料的种类
        int n = sc.nextInt();
        //bagsize背包承重总额
        int v = sc.nextInt();
        int[] weights = new int[n];
        int[] values = new int[n];

        //这里学习一下怎么输入正确的值到weights和value的数组里
        for (int i = 0; i<n;i++){
            weights[i] = sc.nextInt();
            values[i] = sc.nextInt();
        }
        //初始化dp
        int[][] dp = new int[n][v+1];

        //初始化第一列：总承重是0，所以最大价值也是0
        for (int i = 0; i<n;i++){
            dp[i][0] = 0;
        }
        //初始化第一行，只能装物品0，能装得下就继续装
        for (int j =1;j<=v;j++){
            //j不够装物品0就跳过
            if (j<wieghts[0]){
                continue;
            }else{
                //能装得下，就先加入物品0的价值，然后看剩余容量最多能装多少价值的东西
                dp[0][j] = values[0] + dp[0][j-wieghts[0]];
            }
        }
        for (int i =1; i<n;i++){
            for (int j =1; j<=v;j++){
                //两个可能性：不选物品i和多次选择物品i (物品i的价值+剩余容量可以再次选择物品i的最大价值)
                dp[i][j] = Math.max(dp[i-1][j], dp[i][j-weights[i]] + values[i]);
            }
        }
        //记住acm模式是输出output不是return因为main函数返回void
        System.out.println (dp[n-1][v]);
    }
}
```
## 518. Coin Change II
* https://leetcode.com/problems/coin-change-ii/description/
* 文章：https://programmercarl.com/0518.%E9%9B%B6%E9%92%B1%E5%85%91%E6%8D%A2II.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 视频：https://www.bilibili.com/video/BV1KM411k75j?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 问题：给你一些零钱面值（每个面值有无限个），想凑出一个target amount， 一共有几种方法？
* 感觉这是一个“装满target的背包一共有几种方法”的组合问题，和之前的01背包题有点像，不过这次是完全背包（因为每个面值可以用无限次）
* 每个硬币的价值和重量应该都是coins[i]。
1. 明确dp数组的意义
* dp[i][j]的意思就是，可以从物品（0-i）之间选择，装满背包j的方法数量。所以我们的dp数组维度是[nums.length][target amount +1](因为要包括重量为0的情况）
2. 确定递推公式：装满容量为j的背包一共有两种可能性：装物品i和不装物品i， 这两种方法应该互相不重合，而且我们在求组合问题（类似爬楼梯），所以它们应该相加
* dp[i-1][j] (不装物品i）， dp[i][j-coins[i] (装物品i，剩下的容量继续求能装物品i的情况下最多的的装法数量，加上一步装物品i，并不改变原有的装法的数量）
* 递推公式为 dp[i][j] = dp[i-1][j] + dp[i][j-coins[i]]  （这里我推导的时候卡壳了一下，看了代码随想录才意识到多走一步‘装物品i’起始并不会影响原有的装法的数量）
3. 初始化dp数组
* 我们之前有考虑过，如果target是0而且面值为0的话可能装法就会需要乘以2^zeroCount
* 但是这道题的意思是：1 <= coins[i] <= 5000，所以不会有面值为0的硬币，我们就不用考虑了
* 第一列：j=0，要求能装满0价值0重量的背包，那就只有一种方法：不装，所以第一列都是0
* 第一行：只能装物品0，而且我们想求装满背包容量j的方法数，那就是说若果j可以整除物品0的重量，就可以有一种装法：dp[0][j] = j%coins[0] == 0? 1:0
4. 确定遍历顺序
* 和01背包一样，二维数组先遍历物品或者背包容量都是没有问题的。而且都是从左到右从上到下
5. 打印dp数组
* 以amount为5，coins为：[2,3,5] 为例：
* dp数组应该是这样的：
```
1 0 1 0 1 0
1 0 1 1 1 1
1 0 1 1 1 2
```
### 二维数组解法
```java
class Solution {
    public int change(int amount, int[] coins) {
        //初始化dp数组
        int[][] dp = new int[coins.length][amount+1];
        //初始化第一列（都是1，因为装满容量为0的包只有一种方法就是不装）
        for (int i=0; i<coins.length;i++){
            dp[i][0] = 1;
        }
        //初始化第一行：只有当容量能整除物品0的时候才有一种装法可以给它装满
        for (int j=1; j<=amount; j++){
            dp[0][j] = (j%coins[0]==0) ? 1:0;
        }

        //用递推公式填充dp数组
        for (int i=1; i<coins.length;i++){
            for (int j=1; j<= amount; j++){
                //装不下就只好不选
                if (j<coins[i]){dp[i][j] = dp[i-1][j];}
                else{
                    //选与不选各自有那么多的装法，把它们加起来。记住选的话可以重复选
                dp[i][j] = dp[i-1][j] +dp[i][j-coins[i]];
                }
                
            }
        }
        //打印dp数组检查 （遇到大testcase会超时）
        // for (int i =0; i<coins.length;i++){
        //     for (int j =0; j<=amount;j++){
        //         System.out.print(dp[i][j]+" ");
        //     }
        //     System.out.println();
        // }
        return dp[coins.length-1][amount];
    }
}
```
### 一维dp数组解法
* 代码随想录指出，一维dp数组的难点在于理解遍历顺序
* 在求装满背包有几种方案的时候，认清遍历顺序是非常关键的。
* **如果求组合数就是外层for循环遍历物品，内层for遍历背包。我们在01背包的时候都用的是这个**
* 注意完全背包内循环都是正序！！这样才可以保证能重复加入物品
* 01背包内循环后序遍历，是为了可以保证不重复加入物品
* **如果求排列数就是外层for遍历背包，内层for循环遍历物品。之后会安排求排列数的题目**
```java
class Solution {
    public int change(int amount, int[] coins) {
        //初始化dp数组
        int[]dp = new int[amount+1];
        //初始化第一个：
        dp[0] = 1;
        //初始化第一行：只有当容量能整除物品0的时候才有一种装法可以给它装满
        for (int j=1; j<=amount; j++){
            dp[j] = (j%coins[0]==0) ? 1:0;
        }

        //用递推公式填充dp数组
        for (int i=1; i<coins.length;i++){
            //一维数组01背包得后序遍历避免重复加入，但是完全背包不用！！正序才能保证物品可以被重复加入
            for (int j=coins[i]; j<=amount; j++){
                    //这里循环直接从coins[i]开始，如果装不下，dp[j]就会保留上一层i的数值（就等于不加入物品i的装法数量）
                    //写好二维数组再精简成1维的写法，这里右边的dp[j]是更新前不装的方法数量
                dp[j] = dp[j] +dp[j-coins[i]];
                
            }
        }
        return dp[amount];
    }
}
```
## 77. Combination Sum IV
* https://leetcode.com/problems/combination-sum-iv/description/
* 文章：https://programmercarl.com/0377.%E7%BB%84%E5%90%88%E6%80%BB%E5%92%8C%E2%85%A3.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 视频：https://www.bilibili.com/video/BV1V14y1n7B6?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 这一题说是求组合总和，但是又说元素相同顺序不同（比如[1,2], [2,1]）算是两个组合，**其实就是求排列**
* 大家在公众号里学习回溯算法专题的时候，一定做过这两道题目回溯算法：39.组合总和 (opens new window)和回溯算法：40.组合总和II (opens new window)会感觉这两题和本题很像！
* 但其本质是本题求的是排列总和，而且仅仅是求排列总和的个数，并不是把所有的排列都列出来。
* **如果本题要把排列都列出来的话，只能使用回溯算法爆搜。**
* 和前一题零钱兑换2的区别：零钱兑换2求的是组合，这题求的是排列
* 所以和零钱兑换2的区别仅仅是：遍历顺序：我们得**先遍历背包再遍历物品**，而且**正序遍历**保证物品可以保证每个物品添加多次
### 为什么外层遍历物品就求的是组合？外层遍历背包就求的是排列？
* 这样想：外层遍历物品的话物品放进去的顺序是固定的，但是外层遍历背包，内层遍历物品就是物品所有顺序都要考虑的
* 
