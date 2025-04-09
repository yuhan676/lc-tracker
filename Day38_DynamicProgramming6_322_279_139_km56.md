## 322. Coin Change
* https://leetcode.com/problems/coin-change/description/
* 文章：https://programmercarl.com/0322.%E9%9B%B6%E9%92%B1%E5%85%91%E6%8D%A2.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 视频：https://www.bilibili.com/video/BV14K411R7yv?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 有一些面值的零钱（每一个有无限个）,凑出amount最小的硬币数量是多少
* 这是一个完全背包问题，**无所谓排列和组合，因为我们求的是硬币数量，所以用组合求(外层物品，内层容量）的话会更快速一些（直接不用枚举排列）**
* **递归五部曲**
1. 确定dp数组的含义：dp[j] = 装满总价值为j的背包最少需要dp[j]个硬币
2. 递推公式：这是一个“选不选第i个物品”的问题，所以我们要在选与不选之间取最小值：dp[j] = Math.min(dp[j], dp[j-coins[i]] +1)
3. 初始化：dp[0]=装满总价值为0的背包最少需要0个硬币，所以是0； 其余的dp中的值**初始化成integer.max**就可以确保正确更新（因为我们要找最小值）
4. 遍历顺序：本题求钱币最小个数，那么钱币有顺序和没有顺序都可以，都不影响钱币的最小个数。所以本题并不强调集合是组合还是排列。那么我用coins放在外循环，target在内循环的方式。本题钱币数量可以无限使用，那么是完全背包。所以遍历的内循环是正序
5. 举例推导dp数组
* 注意的点还挺多的，比如如果dp[j-coins[i]]没有被更新成非max的数的话，就不用更新dp[j]了（没办法reach[j-coins[i]]，就更没可能reach [j])
* 
```java
class Solution {
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount+1];
        int max = Integer.MAX_VALUE;
        //初始化
        dp[0] = 0;
        for (int j = 1; j<=amount; j++){
            //初始化成最大值方便之后math.min()更新
            //如果你还没找到任何方法可以组成这个金额，那我们就认为这个值是无穷大（即目前“不可达”）。
            dp[j] = max;
        }
        for (int i = 0; i<coins.length;i++){
            for (int j = 1; j<=amount;j++){
                if (j<coins[i]){continue;}
                else{
                    //因为如果 dp[j - coins[i]] 是 Integer.MAX_VALUE，意味着：“凑不出金额 j - coins[i]”
                    //你自然也凑不出金额 j（因为你连中间那一段都没凑出来）。
                    if (dp[j-coins[i]]!= max){
                        dp[j] = Math.min(dp[j],dp[j-coins[i]]+1);
                    }
                    
                }
                // 如果全部打印，对于一些testcase会超时
                //System.out.print(dp[j]+" ");
            }
            //System.out.println();
        }
        //dp[amount] == Integer.MAX_VALUE，说明 无法用任何硬币组合出这个金额，此时题目要求返回 -1
        return dp[amount]==max ? -1: dp[amount];
    }
}
```
## 279. Perfect Squares
* https://leetcode.com/problems/perfect-squares/description/
* 文章：https://programmercarl.com/0279.%E5%AE%8C%E5%85%A8%E5%B9%B3%E6%96%B9%E6%95%B0.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 视频：https://programmercarl.com/0279.%E5%AE%8C%E5%85%A8%E5%B9%B3%E6%96%B9%E6%95%B0.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* ![Screenshot 2025-04-08 at 16 42 21](https://github.com/user-attachments/assets/4bc093fe-364f-43e2-b6f8-b906d0f4e597)
* 这道题和322题几乎一样：不过面值是平方数，n是target，我们要找到最少的平方数的个数（硬币个数）
* 动规五部曲
1. dp数组的含义：dp[j]：凑成和为j的平方数，最少有dp[j]个
2. 状态转移函数：一共有选当前数字i和不选的可能，dp[j] = Math.min(dp[j],dp[j-i*i]+1
3. 初始化：装满目标价值为j的口袋最少需要0个硬币，所以dp[0]=0
4. 遍历顺序：和上题一样，外层装物品或者容量都可以，因为是完全背包所以内层遍历顺序是正序
5. 举例：已输入n为5例，dp状态图如下：
* ![image](https://github.com/user-attachments/assets/119b48cd-5348-4054-8601-450d102c0e79)
* 难点：需要自己生成平方数的遍历体系，直接这样：for (int i =1; i*i <=n; i++)
### 代码随想录解法
```java
class Solution {
    public int numSquares(int n) {
        int [] dp = new int[n+1];
        dp[0] = 0;
        for (int i =1;i<=n;i++){
            dp[i] = Integer.MAX_VALUE;
        }
        //这样相当于自动生成了从1到小于等于n的平方数的最大值的所有candidate（就是硬币的面值）
        for (int i =1; i*i<=n;i++){
            //j<=i*i就肯定是没可能装下的
            for (int j =i*i; j<=n;j++){
                if (dp[j-i*i]!= Integer.MAX_VALUE){
                    //这里注意是dp[j]因为我们要装满容量为j的背包
                    dp[j] = Math.min(dp[j], dp[j-i*i]+1);
                }
            }
        }
        return dp[n];
    }
}
```
## 139. Word Break
* https://leetcode.com/problems/word-break/description/
* 文章：https://programmercarl.com/0139.%E5%8D%95%E8%AF%8D%E6%8B%86%E5%88%86.html#%E6%80%9D%E8%B7%AF
* 视频：https://www.bilibili.com/video/BV1pd4y147Rh?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 代码随想录上动规五部曲很清楚，这里先不写了
* dp[i]（boolean）代表着长度为i的substring可以由dictwords拼凑而成
* dp[i]如果能work，是因为s[i-j]的子字符串在字典里，而且dp[j]也是true
* 是排列问题（因为字符串出现有先后顺序），所以外层遍历背包
* 初始化：dp[0]=true没有意义（字典里没有空字符串，怎么能平凑成空字符串呢？？），但是为了之后的都能变true，故第一项一定得是true；其他项初始化成false就好
* **自己写晕乎乎的，二刷重新刷**
```java
class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        //注意j是容量也就是s的长度
        boolean[] dp = new boolean[s.length()+1];
        dp[0] = true;
        for (int i =1; i<=wordDict.size(); i++){
            dp[i] = false;
        }

        for (int j = 1; j<=s.length();j++){//背包容量
            for (int i=0; i<j && !dp[j]; i++){//物品,强调dp[j]未更新
                if (wordDict.contains(s.substring(i,j)) && dp[i]){
                    dp[j] = true;
                }
            }
        }
        return dp[s.length()];
    }
}
```
# 多重背包基础
* 文章：https://programmercarl.com/%E8%83%8C%E5%8C%85%E9%97%AE%E9%A2%98%E7%90%86%E8%AE%BA%E5%9F%BA%E7%A1%80%E5%A4%9A%E9%87%8D%E8%83%8C%E5%8C%85.html#%E5%85%B6%E4%BB%96%E8%AF%AD%E8%A8%80%E7%89%88%E6%9C%AC
* 卡玛网56练习题：https://kamacoder.com/problempage.php?pid=1066
* 多重背包就是每个物品可以使用大于一次但不是无限次
* 我们把它展开做成一个01背包就好（物品0可以用3次，变成物品0，物品0，物品0—），就变成每个物品只能使用一次了
* **二刷重温**
### 展开成01背包
* 时间复杂度：O(m × n × k)，m：物品种类个数，n背包容量，k单类物品数量
* 空间复杂度：n+ kn
```java
import java.util.*;
public class Main{
    public static void main (String[] args){
        Scanner sc = new Scanner(System.in);
        int C = sc.nextInt();
        int N = sc.nextInt();
        int[] weights = new int[N];
        int[] values = new int[N];
        int[] count = new int[N];

        int totalcount = 0;
        for (int i=0; i<N; i++){
            weights[i] = sc.nextInt();
        }
        for (int i=0; i<N; i++){
            values[i] = sc.nextInt();
        }
        for (int i=0; i<N; i++){
            count[i] = sc.nextInt();
            totalcount += count[i];
        }
        
        int[] expandedweights = new int[totalcount];
        int[] expandedvalues = new int[totalcount];
        //这里要用指针记录我们填充过多少个物品了，不然每次新的i循环j都会被重置成0
        int index = 0;
        for (int i=0; i<N;i++){
            for (int j=0; j<count[i];j++){
                expandedweights[index] = weights[i];
                expandedvalues[index] = values[i];
                index++;
            }
        }
        //使用一维背包，外层物品内层容量，内层到序遍历
        int dp[] = new int[C+1];
        for (int i = 0; i<totalcount;i++){
            for (int j=C; j>=expandedweights[i];j--){
                dp[j] = Math.max(dp[j],dp[j-expandedweights[i]]+ expandedvalues[i]);
            }
        }
        System.out.print(dp[C]);
    }
}
```

# 背包问题总结篇
https://programmercarl.com/%E8%83%8C%E5%8C%85%E6%80%BB%E7%BB%93%E7%AF%87.html#%E6%80%BB%E7%BB%93

