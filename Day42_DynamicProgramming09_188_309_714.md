## 188. Best Time to Buy and Sell Stock IV
* https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/
* 文章：https://programmercarl.com/0188.%E4%B9%B0%E5%8D%96%E8%82%A1%E7%A5%A8%E7%9A%84%E6%9C%80%E4%BD%B3%E6%97%B6%E6%9C%BAIV.html
* 视频：https://www.bilibili.com/video/BV16M411U7XJ/?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5
* 和买卖股票3的区别：3最多买进卖出两次，这题最多可以买进卖出k次
1. 2. dp数组含义和递推公式
* dp数组含义上一题已经讲过，这里着重讲递推公式和怎么模拟j在奇数和偶数的时候的变化
* 上一题中我们用了一个2维的数组，最多可以卖出两次的逻辑如下：
* dp[i][0] =无动作
* dp[i][1] =第一次持有 = 要么前一天就已经持有（dp[i-1][1])要么今天买进（dp[i-1][0] - prices[i])
* dp[i][2] = 第一次不持有
* dp[i][3] = 第二次持有
* dp[i][4] = 第二次不持有。。。
* 以这个标准，如果最多买进卖出k次，那这个第二个维度就应该是2*k + 1(要保留0）,最后一个pointer是k，所以我们需要遍历到pointer=k
* 那我们就需要类比j=奇数和偶数的时候的不同状态
* 注意这里j的边界：2*k -1
```
for (int j = 0; j < 2 * k - 1; j += 2) {
    dp[i][j + 1] = max(dp[i - 1][j + 1], dp[i - 1][j] - prices[i]);
    dp[i][j + 2] = max(dp[i - 1][j + 2], dp[i - 1][j + 1] + prices[i]);
}
```
3. 初始化dp
* 由买卖股票3推出，j=奇数的时候dp[i][j] = -prices[0], 偶数的时候=0
```
for (int j = 1; j < 2 * k; j += 2) {
    dp[0][j] = -prices[0];
}
```
4. 确定遍历顺序： 从递归公式其实已经可以看出，一定是从前向后遍历，因为dp[i]，依靠dp[i - 1]的数值。
5. 举例推导dp数组，以输入[1,2,3,4,5]，k=2为例。
* ![image](https://github.com/user-attachments/assets/00326118-ccf1-441c-963a-7458d540b6b5)
* 最后一次卖出，一定是利润最大的，dp[prices.size() - 1][2 * k]即红色部分就是最后求解。
### 二维dp数组（非滚动）解法
* 时间复杂度: O(n * k)，其中 n 为 prices 的长度
* 空间复杂度: O(n * k)
```java
class Solution {
    public int maxProfit(int k, int[] prices) {
        int[][] dp = new int[prices.length][2*k +1];
        //初始化,所有奇数状态（持有）都是买入，所有偶数状态（卖出）
        for (int j =1; j<2*k; j+=2){
            dp[0][j] = -prices[0];
        }
        for (int i =1; i<prices.length; i++){
            //最后一位是2k，所以最后一个j得=2k-2,才能填充到2k
            for (int j = 0; j<2*k-1;j+= 2){
                dp[i][j+1] = Math.max(dp[i-1][j+1], dp[i-1][j] - prices[i]);
                dp[i][j+2] = Math.max(dp[i-1][j+2], dp[i-1][j+1] + prices[i]);
            }
        }
        return dp[prices.length-1][2*k];
    }
}
```
### 三位数组解法：更加直观
```java
// 版本一: 三维 dp数组
class Solution {
    public int maxProfit(int k, int[] prices) {
        if (prices.length == 0) return 0;

        // [天数][交易次数][是否持有股票]
        int len = prices.length;
        int[][][] dp = new int[len][k + 1][2];
        
        // dp数组初始化
        // 初始化所有的交易次数是为确保 最后结果是最多 k 次买卖的最大利润
        for (int i = 0; i <= k; i++) {
            dp[0][i][1] = -prices[0];
        }

        for (int i = 1; i < len; i++) {
            for (int j = 1; j <= k; j++) {
                // dp方程, 0表示不持有/卖出, 1表示持有/买入
                dp[i][j][0] = Math.max(dp[i - 1][j][0], dp[i - 1][j][1] + prices[i]);
                dp[i][j][1] = Math.max(dp[i - 1][j][1], dp[i - 1][j - 1][0] - prices[i]);
            }
        }
        return dp[len - 1][k][0];
    }
}
```
## 309. Best Time to Buy and Sell Stock with Cooldown
* https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/description/
* 文章：https://programmercarl.com/0309.%E6%9C%80%E4%BD%B3%E4%B9%B0%E5%8D%96%E8%82%A1%E7%A5%A8%E6%97%B6%E6%9C%BA%E5%90%AB%E5%86%B7%E5%86%BB%E6%9C%9F.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 视频：https://www.bilibili.com/video/BV1rP4y1D7ku?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 可以购入无限次，和买卖股票2类似，但是增加了冷冻期（今天卖了，明天不能买进，后天才能）
1. 确定递推数组含义：dp[i][j]代表第i天第j种状态能获得的最大利润
* 在动态规划：122.买卖股票的最佳时机II (https://programmercarl.com/0122.%E4%B9%B0%E5%8D%96%E8%82%A1%E7%A5%A8%E7%9A%84%E6%9C%80%E4%BD%B3%E6%97%B6%E6%9C%BAII%EF%BC%88%E5%8A%A8%E6%80%81%E8%A7%84%E5%88%92%EF%BC%89.html)中有两个状态，持有股票后的最多现金，和不持有股票的最多现金。
* 这一题和之前的买卖股票题不一样，不持有股票的状态需要特意去分今天卖出股票/今天保持不持有股票因为之前就卖出的两种情况，因为只有我们明确今天卖出股票，才能推算到明天是冷冻期。
* 这样算的话就一共有4个状态：状态0:持有股票（保持持有/今天买入）， 状态1:不持有股票状态1（保持不持有，过去卖出），状态2:不持有股票状态2（今天卖出），状态3:冷冻期
2. 递推公式
* 状态0：持有股票：可以是保持持有dp[i-1][0] 或者是今天买入，分两种情况，前一天是冷冻期状态3=dp[i-1][3] - prices[i], 前一天是状态1保持不持有=dp[i-1][1] - prices[i]。注意今天买入，前一天不可能是当天卖出，因为有冷冻期限制。三者取最大
* 状态1：保持不持有，可能前一天就已经是状态1dp[i-1[1]，或者前一天是冷冻期dp[i-1][3]，两者取最大
* 状态2：今天卖出状态，前一天一定持有股票(状态0），所以今天的钱=dp[i-1][0] + prices[i]
* 状态3:冷冻期，前一天只能是当天卖出（状态2），就保持当时的状态不变：dp[i-1][2]
3. dp数组如何初始化
* 这里主要讨论一下第0天如何初始化。
* 如果是持有股票状态（状态0）那么：dp[0][0] = -prices[0]，一定是当天买入股票。
* 保持卖出股票状态（状态1），这里其实从 「状态1」的定义来说 ，很难明确应该初始多少，这种情况我们就看递推公式需要我们给他初始成什么数值。
* 如果i为1，第1天买入股票，那么递归公式中需要计算 dp[i - 1][1] - prices[i] ，即 dp[0][1] - prices[1]，那么大家感受一下 dp[0][1] （即第0天的状态1）应该初始成多少，只能初始为0。想一想如果初始为其他数值，是我们第1天买入股票后 手里还剩的现金数量是不是就不对了。
* 今天卖出了股票（状态2），同上分析，dp[0][2]初始化为0，dp[0][3]也初始为0。可以视为当天买当天卖，并且冷冻，利润都是0
4. 遍历顺序
* dp[i]由dp[i-1]推导而来，所以从左到右遍历
5. 举例推导
* 以 [1,2,3,0,2] 为例，dp数组如下：
* ![image](https://github.com/user-attachments/assets/72bb84e8-791a-4523-bd3b-e8028d191001)
* 那结果呢？取最后一天非持有股票状态的最大值,因为非持有股票就意味着卖出了（状态1,2,3)
### 二维非滚动方法
* 时间复杂度：O(n)
* 空间复杂度：O(n)
* 空间复杂度可以优化，定义一个dp[2][4]大小的数组就可以了，就保存前一天的当前的状态
```java
class Solution {
    public int maxProfit(int[] prices) {
        int[][] dp = new int[prices.length][4];
        //初始化:0=持有，1=保持不持有，2=今天卖出不持有，3=冷冻期（昨天卖出）
        dp[0][0] = -prices[0];
        dp[0][1] = 0;
        dp[0][2] = 0;
        dp[0][3] = 0;
        for (int i=1; i<prices.length;i++){
            //可能是前一天就持有，或者今天买入（昨天可能是保持不持有，或者冷冻期）；昨天不可能卖出。今天买入一定会花钱
            //注意math.max只能有两个args
            dp[i][0]= Math.max(Math.max(dp[i-1][0], dp[i-1][1] - prices[i]), dp[i-1][3]-prices[i]);
            //今天不持有，可能是昨天就不持有，或者昨天是冷冻期，无利润
            dp[i][1]=Math.max(dp[i-1][1], dp[i-1][3]);

            //今天卖出，那昨天只有可能是持有状态，有利润
            dp[i][2] = dp[i-1][0] + prices[i];

            //今天冷冻期，那昨天只有可能是卖出,且无利润
            dp[i][3] = dp[i-1][2];
        }
        //不持有状态中选择最大的
        return Math.max(Math.max(dp[prices.length-1][1], dp[prices.length-1][2]),dp[prices.length-1][3]);
    }
}
```
### 代码随想录上的滚动二维数组解法(我自己没有写出来）
```java
//using 2*4 array for space optimization
//這裡稍微說一下，我在LeetCode提交的時候，2*4 2-D array的performance基本上和下面的1-D array performance差不多
//都是time: 1ms, space: 40.X MB （其實 length*4 的 2-D array也僅僅是space:41.X MB，看起來不多）
//股票累的DP題目大致上都是這樣，就當作是一個延伸就好了。真的有人問如何優化，最起碼有東西可以講。
class Solution {
    /**
    1. [i][0] holding the stock
    2. [i][1] after cooldown but stil not buing the stock
    3. [i][2] selling the stock
    4. [i][3] cooldown
     */
    public int maxProfit(int[] prices) {
        int len = prices.length;
        int dp[][] = new int [2][4];
        dp[0][0] = -prices[0];
        
        for(int i = 1; i < len; i++){
            dp[i % 2][0] = Math.max(Math.max(dp[(i - 1) % 2][0], dp[(i - 1) % 2][1] - prices[i]), dp[(i - 1) % 2][3] - prices[i]);
            dp[i % 2][1] = Math.max(dp[(i - 1) % 2][1], dp[(i - 1) % 2][3]);
            dp[i % 2][2] = dp[(i - 1) % 2][0] + prices[i];
            dp[i % 2][3] = dp[(i - 1) % 2][2];
        }
        return Math.max(Math.max(dp[(len - 1) % 2][1], dp[(len - 1) % 2][2]), dp[(len - 1) % 2][3]);
    }
}
```

## 714. Best Time to Buy and Sell Stock with Transaction Fee
* https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/description/
* 文章：https://programmercarl.com/0714.%E4%B9%B0%E5%8D%96%E8%82%A1%E7%A5%A8%E7%9A%84%E6%9C%80%E4%BD%B3%E6%97%B6%E6%9C%BA%E5%90%AB%E6%89%8B%E7%BB%AD%E8%B4%B9%EF%BC%88%E5%8A%A8%E6%80%81%E8%A7%84%E5%88%92%EF%BC%89.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 视频：https://www.bilibili.com/video/BV1z44y1Z7UR?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 相对122.买卖股票的最佳时机II（可以无限次买卖） ，本题只需要在计算卖出操作的时候减去手续费就可以了，代码几乎是一样的
* 同样二维数组第二个维度只需要记录持有或者不持有股票，能获得的最大利润
* 我统一在卖出股票的时候缴纳手续费，比较方便比较
### 二维非滚动解法
* 时间复杂度：O(n)
* 空间复杂度：O(n)
```java
class Solution {
    public int maxProfit(int[] prices, int fee) {
        int[][] dp = new int[prices.length][2];
        //0 =不持有；1=持有
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        for (int i =1; i<prices.length;i++){
            //不持有可能是前一天就不持有，或者昨天持有今天卖出，卖出的时候缴纳手续费
            dp[i][0] = Math.max(dp[i-1][0],dp[i-1][1] + prices[i] - fee);
            //持有可能是前一天就持有，或者今天购入
            dp[i][1] = Math.max(dp[i-1][1], dp[i-1][0]-prices[i]);
        }
        return dp[prices.length-1][0];
    }
}
```
### 代码随想录滚动数组2*2
* 空间复杂度简化为o1
* 只记录前一天和今天的内容
* 我自己没有手写出来
```java
class Solution {
    public int maxProfit(int[] prices, int fee) {
        int dp[][] = new int[2][2];
        int len = prices.length;
        //[i][0] = holding the stock
        //[i][1] = not holding the stock
        dp[0][0] = -prices[0];

        for(int i = 1; i < len; i++){
            dp[i % 2][0] = Math.max(dp[(i - 1) % 2][0], dp[(i - 1) % 2][1] - prices[i]);
            dp[i % 2][1] = Math.max(dp[(i - 1) % 2][1], dp[(i - 1) % 2][0] + prices[i] - fee);
        }

        return dp[(len - 1) % 2][1];
    }
}
```
### 股票问题总结
* https://programmercarl.com/%E5%8A%A8%E6%80%81%E8%A7%84%E5%88%92-%E8%82%A1%E7%A5%A8%E9%97%AE%E9%A2%98%E6%80%BB%E7%BB%93%E7%AF%87.html#%E4%B9%B0%E5%8D%96%E8%82%A1%E7%A5%A8%E7%9A%84%E6%9C%80%E4%BD%B3%E6%97%B6%E6%9C%BAiv
