## 121. Best Time to Buy and sell stock
* https://leetcode.com/problems/best-time-to-buy-and-sell-stock/description/
* 文章：https://programmercarl.com/0121.%E4%B9%B0%E5%8D%96%E8%82%A1%E7%A5%A8%E7%9A%84%E6%9C%80%E4%BD%B3%E6%97%B6%E6%9C%BA.html
* 视频：https://www.bilibili.com/video/BV1Xe4y1u77q?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 只能卖卖一次
### 暴力解法
* 两个for循环枚举所有盈利可能
* 时间复杂度：O(n^2), n = prices.length
* 空间复杂度=1
* 会超时
```java
class Solution {
    //暴力枚举，两个for循环
    public int maxProfit(int[] prices) {
        int res = 0;
        for (int i =0; i<prices.length;i++){
            for (int j = i+1; j<prices.length;j++){
                res = Math.max(res,prices[j]-prices[i]);
            }
        }
        return res;
    }
}
```
### 贪心解法
* 股票只卖卖一次，所以取左边最小值，最小值右边区间最大值就ok
* 代码随想录的解法只要一层for循环就好
* 时间复杂度；O(n)
* 空间复杂度O(1)
```java
class Solution {
    public int maxProfit(int[] prices) {
        int low = Integer.MAX_VALUE;
        int res = 0;
        for (int i =0; i<prices.length;i++){
            //这里一开始我以为要先找到low的index，然后再遍历右边区间
            //但代码随想录的解法说明，起始不用break，找到了继续用i遍历找到最大就好
            low = Math.min(low, prices[i]);
            res = Math.max(res, prices[i]-low);
        }
        return res;
    }
}
```
### 动态规划解法- 二维dp
* 能狗买卖多次
1. 确定dp数组以及下标的含义
* 我们定义一个二维数组，dp[i][0]代表在第i天持有股票所得最多现金（不卖：可以是今天买进或者之前买进），dp[i][1]代表在第i天不持有股票所得最多现金（可以是之前卖出或者是今天卖出）
2. 确定递推公式
* 第i天持有股票所得最多现金dp[i][0]，有两种可能，一种是今天买进，所持有现金=-prices[i](负数），或者是第i-1就持有股票，那么就保持现状dp[i][0] = dp[i-1][0]
* 那么dp[i][0] = max(dp[i-1][0], -prices[i])
* 第i天不持有股票所得最多现金dp[i][1], 有两种可能，一种是之前已经卖出，就保持不动dp[i-1][1], 或者今天卖出=昨天不卖的钱dp[i-0] + 今天卖出的钱prices[i]
* 所以dp[i][1] = max(dp[i-1][1], dp[i-1][0] + prices[i])
3. dp数组初始化
* 由递推公式 dp[i][0] = max(dp[i - 1][0], -prices[i]); 和 dp[i][1] = max(dp[i - 1][1], prices[i] + dp[i - 1][0]);可以看出其基础都是要从dp[0][0]和dp[0][1]推导出来。
* dp[0][0] 表示第0天持有股票，此时持有就意味着我们一定当天买进了，所以dp[0][0] = -prices[0]
* dp[0][1] 表示第0天不持有股票，那么就代表没有买，所以现金=0
4. 遍历顺序，从递推公式可以看出dp[i]都是由dp[i - 1]推导出来的，那么一定是从前向后遍历。
5. 以示例1，输入：[7,1,5,3,6,4]为例，dp数组状态如下：
![image](https://github.com/user-attachments/assets/f3efa09d-566c-489d-ab9e-6707099b78d3)
* dp[5][1]就是最终结果。
* 为什么不是dp[5][0]呢？因为本题中不持有股票状态所得金钱一定比持有股票状态得到的多！因为只能卖一次，一定要卖出才能盈利
* 时间复杂度：O(n)
* 空间复杂度：O(n)
* 你开了一个 len × 2 的二维数组
* 意味着你每一天都要保存两个状态
* 所以空间复杂度是 O(n)，随着天数（数组长度）线性增长
```java
class Solution {
    public int maxProfit(int[] prices) {
        int[][] dp = new int[prices.length][2];
        //第一天持有证明一定买进了
        dp[0][0] = -prices[0];
        //第一天不持有证明没有买进
        dp[0][1] = 0;
        for (int i =1; i<prices.length;i++){
            dp[i][0] = Math.max(dp[i-1][0], -prices[i]);
            dp[i][1] = Math.max(dp[i-1][1], dp[i-1][0]+prices[i]);
        }
        //最后一天，不持有股票的最大现金
        return dp[prices.length-1][1];
    }
}
```
### 二维滚动dp解法
* 从递推公式可以看出，dp[i]只是依赖于dp[i - 1]的状态。
```
dp[i][0] = max(dp[i - 1][0], -prices[i]);
dp[i][1] = max(dp[i - 1][1], prices[i] + dp[i - 1][0]);
```
* 那我们只需要开一个2*2大小的数组记录当天dp状态和前一天dp的状态就可以了，使用滚动数组，但是我们需要做一个i%2的处理，有点麻烦
* 我自己写出来二维数组就可以了,这里复制一下代码随想录的解法
* 时间复杂度：O(n)
* 空间复杂度：O(1)
* 利用 i % 2 和 (i-1) % 2 来交替覆盖前一天的数据
* 实质上，只保留了当前和前一天的状态，因为你不再依赖更早的天数
* 所以不论 prices 多长，占用空间始终是常数：O(1)
```java
class Solution {
public:
    int maxProfit(vector<int>& prices) {
        int len = prices.size();
        vector<vector<int>> dp(2, vector<int>(2)); // 注意这里只开辟了一个2 * 2大小的二维数组
        dp[0][0] -= prices[0];
        dp[0][1] = 0;
        for (int i = 1; i < len; i++) {
            dp[i % 2][0] = max(dp[(i - 1) % 2][0], -prices[i]);
            dp[i % 2][1] = max(dp[(i - 1) % 2][1], prices[i] + dp[(i - 1) % 2][0]);
        }
        return dp[(len - 1) % 2][1];
    }
};
```
## 122. Best Time to Buy and Sell Stock II
* https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/description/
* 文章：https://programmercarl.com/0122.%E4%B9%B0%E5%8D%96%E8%82%A1%E7%A5%A8%E7%9A%84%E6%9C%80%E4%BD%B3%E6%97%B6%E6%9C%BAII%EF%BC%88%E5%8A%A8%E6%80%81%E8%A7%84%E5%88%92%EF%BC%89.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 视频：https://www.bilibili.com/video/BV1D24y1Q7Ls?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 贪心中我们已经讲了这道题，只要一直累加正数的daily profit（昨天买入，今天卖出就可以）
### 贪心解法
* 时间复杂度：O(n)
* 空间复杂度O(1)
```java
class Solution {
    public int maxProfit(int[] prices) {
        int maxProf = 0;
        for (int i =1; i<prices.length;i++){
            int dailyProf = prices[i]-prices[i-1];
            if (dailyProf >0){
                maxProf += dailyProf;
            }
        }
        return maxProf;
    }
}
```
### 动态规划解法 -二维
* 和买卖股票1起始差不多，主要区别在递推公式上：因为我们可以无限次买和卖（只要每次只持有一只股票）
1. dp数组的含义
* dp[i][0] = 第i天持有股票所得现金，有两个状态推断而来：dp[i-1]就持有股票，保持现状：dp[i][0] = dp[i-1][0]; 或者i-1的时候不持有股票，今天买入=dp[i-1][1] - prices[i]，两者取最大
* 注意这里和买卖股票1题唯一不同的地方，就是买卖股票1只能买进一次，所以dp[i][0] = max(dp[i - 1][0], -prices[i]);； 但是这一题可以重复买入卖出，素以dp[i][0] = max(dp[i-1][0], dp[i-1][1] - prices[i])
* dp[i][1] = 第i天不持有股票所得最多现金,两个状态:第i-1天就不持有股票，保持现状（dp[i-1][1]),或者前一天持有股票，今天卖出（dp[i-1][0] + prices[i])
* 时间复杂度：O(n)
* 空间复杂度：O(n)
```java
class Solution {
    public int maxProfit(int[] prices) {
        int[][] dp = new int[prices.length][2];
        //第一天持有=买入
        dp[0][0] = -prices[0];
        //第一天不持有=没买入
        dp[0][1] = 0;

        for (int i =1; i<prices.length; i++){
            dp[i][0] = Math.max(dp[i-1][0], dp[i-1][1] - prices[i]);
            dp[i][1] = Math.max(dp[i-1][1], dp[i-1][0] + prices[i]);
        }
        //最后一天，不持有肯定比持有要赚的多
        return dp[prices.length-1][1];
    }
}
```
### 动态规划解法 -二维滚动
* 一样是一个2*2的滚动数组，简化空间复杂度为O(1)
* 我在这里贴一下代码随想录的解法
```java
class Solution {
public:
    int maxProfit(vector<int>& prices) {
        int len = prices.size();
        vector<vector<int>> dp(2, vector<int>(2)); // 注意这里只开辟了一个2 * 2大小的二维数组
        dp[0][0] -= prices[0];
        dp[0][1] = 0;
        for (int i = 1; i < len; i++) {
            dp[i % 2][0] = max(dp[(i - 1) % 2][0], dp[(i - 1) % 2][1] - prices[i]);
            dp[i % 2][1] = max(dp[(i - 1) % 2][1], prices[i] + dp[(i - 1) % 2][0]);
        }
        return dp[(len - 1) % 2][1];
    }
};
```
## 123. Best Time to Buy and Sell Stock III
* https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/description/
* 文章：https://programmercarl.com/0123.%E4%B9%B0%E5%8D%96%E8%82%A1%E7%A5%A8%E7%9A%84%E6%9C%80%E4%BD%B3%E6%97%B6%E6%9C%BAIII.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 视频：https://www.bilibili.com/video/BV1WG411K7AR?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 最多只能买卖两次（可以卖一次也可以卖两次），而且只能买卖，买卖，不能买买卖卖
1. 确定dp数组：
* dp[i][j] = 第i天，j为[0-4]五种状态，dp[i][j]表示第i天状态j所剩最大现金。
0. 没有操作 （其实我们也可以不设置这个状态）
1. 第一次持有股票
2. 第一次不持有股票
3. 第二次持有股票
4. 第二次不持有股票
* 需要注意：dp[i][1]，表示的是第i天，买入股票的状态，并不是说一定要第i天买入股票
2. 确定递推公式
* 没有操作，应该操作就是前一天的dp[i-1][0]
* 达到dp[i][1] 有两个具体操作： 第i天买入股票了，那么dp[i][1] = dp[i-1][0]-prices[i],或者没有操作：dp[i-1][dp[1],两者选最大的dp[i][1] = max(dp[i-1][0] - prices[i], dp[i - 1][1]);
* 达到dp[i][2] 也有两个操作：第i天卖出股票了，dp[i][2] = dp[i-1][1] + prices[i]; 第i天没有操作，沿用前一天卖出股票的状态dp[i][2] = dp[i-1][2]
* 达到dp[i][3] = max(dp[i-1][2] - prices[i], dp[i-1][3])
* dp[i][4] = max(dp[i-1][3] + prices[i], dp[i-1][4])
3. dp数组初始化
* dp[0][0] 没有操作， 就是0
* dp[0][1] 第一次买入： = -prices[0]
* dp[0][2] = 第一次卖出，只可能当天买入，当天卖出， 所以余额=0
* dp[0][3]:还没第一次买入呢，怎么初始化第二次买入？第二次买入依赖于第一次卖出的状态，相当于第0天第一次买入了，第一次卖出了，然后再买入一次， 所以手头现金=-price[0]
* dp[0][4] = 第二次卖出，和第一次卖出同理，现金余额=0
4. 确定遍历顺序：从前向后，因为我们依赖于dp[i-1]的所有数值
5. 举例推导dp数组
* ![image](https://github.com/user-attachments/assets/41eadecc-d0a5-49d7-a216-886d844af035)
* 现在最大的时候一定是卖出的状态，而两次卖出的状态现金最大一定是最后一次卖出。如果想不明白的录友也可以这么理解：**如果第一次卖出已经是最大值了，那么我们可以在当天立刻买入再立刻卖出**。所以dp[4][4]已经包含了dp[4][2]的情况。也就是说第二次卖出手里所剩的钱一定是最多的。
* 所以最终最大利润是dp[4][4]，卖两次一定能得到最大利润
### 二维dp数组解法（推荐）
* 时间复杂度：O(n)
* 空间复杂度：O(n × 5)
```java
class Solution {
    public int maxProfit(int[] prices) {
        int[][] dp = new int[prices.length][5];
        //初始化：0=没操作；1=第一次持有；2=第一次不持有；3=第二次持有；4=第二次不持有
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        //第一天当天买当天卖
        dp[0][2] = 0;
        //第一天当天买当天卖，然后再第二次买入
        dp[0][3] = -prices[0];
        dp[0][4] = 0;

        for (int i =1; i<prices.length; i++){
            //dp[i][0]一直没操作就一直没变化，所以不用管
            //第一次持有，可能是之前就持有，或者今天买入（0操作持有的钱-今天买入的价格）
            dp[i][1] = Math.max(dp[i-1][1],dp[i-1][0] - prices[i]);
            //第一次不持有，可能是之前就已经卖出了，或者今天卖出
            dp[i][2] = Math.max(dp[i-1][2], dp[i-1][1] + prices[i]);
            dp[i][3] = Math.max(dp[i-1][3], dp[i-1][2] - prices[i]);
            dp[i][4] = Math.max(dp[i-1][4], dp[i-1][3] + prices[i]);
        }
        return dp [prices.length-1][4];

    }
}
```
### 力扣官方优化空间解法：一维（很绕）
*大家会发现dp[2]利用的是当天的dp[1]。 但结果也是对的。解释如下：
* dp[1] = max(dp[1], dp[0] - prices[i]); 如果dp[1]取dp[1]，即保持买入股票的状态，那么 dp[2] = max(dp[2], dp[1] + prices[i]);中dp[1] + prices[i] 就是今天卖出。
* 如果dp[1]取dp[0] - prices[i]，今天买入股票，那么dp[2] = max(dp[2], dp[1] + prices[i]);中的dp[1] + prices[i]相当于是今天再卖出股票，一买一卖收益为0，对所得现金没有影响。相当于今天买入股票又卖出股票，等于没有操作，保持昨天卖出股票的状态了。
* 这种写法看上去简单，其实思路很绕，不建议大家这么写，这么思考，很容易把自己绕进去！
* 时间复杂度：O(n)
* 空间复杂度：O(1)
```java
class Solution {
    public int maxProfit(int[] prices) {
        int[] dp = new int[4]; 
        // 存储两次交易的状态就行了
        // dp[0]代表第一次交易的买入
        dp[0] = -prices[0];
        // dp[1]代表第一次交易的卖出
        dp[1] = 0;
        // dp[2]代表第二次交易的买入
        dp[2] = -prices[0];
        // dp[3]代表第二次交易的卖出
        dp[3] = 0;
        for(int i = 1; i <= prices.length; i++){
            // 要么保持不变，要么没有就买，有了就卖
            dp[0] = Math.max(dp[0], -prices[i-1]);
            dp[1] = Math.max(dp[1], dp[0]+prices[i-1]);
            // 这已经是第二次交易了，所以得加上前一次交易卖出去的收获
            dp[2] = Math.max(dp[2], dp[1]-prices[i-1]);
            dp[3] = Math.max(dp[3], dp[2]+ prices[i-1]);
        }
        return dp[3];
    }
}
```
