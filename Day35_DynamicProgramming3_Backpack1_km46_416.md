## 背包问题基础
* 背包问题分类
* ![image](https://github.com/user-attachments/assets/546934ba-7e95-4ab5-adbd-79161eff6f02)
* 掌握01背包+完全背包+（多重背包）就ok了
* 01背包：n种物品，每种物品只有一个
* 完全背包：n种物品，每种物品有无限个
* 多重背包：n种物品，每种物品的个数各不相同
* 力扣上没有纯粹的01，完全背包问题
## 01背包
* 卡码网46: https://kamacoder.com/problempage.php?pid=1046
* 有n件物品和一个最多能背重量为w 的背包。第i件物品的重量是weight[i]，得到的价值是value[i] 。每件物品只能用一次，求解将哪些物品装入背包里物品价值总和最大。
* <img width="282" alt="Screenshot 2025-04-04 at 13 33 57" src="https://github.com/user-attachments/assets/1618dcd9-1597-43ab-b938-df6be08260b1" />

### 暴力解法
* 每个物品，取&不取，用回溯算法进行暴力枚举
* 时间复杂度：2^n，因为每个物品只有两个状态：取与不取，所以一共有2^n个状态
### 卡码网
* 注意每个var和method都是static，main才能调用
```java
import java.util.*;

public class Main {
    static int maxVal = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int M = sc.nextInt(); // 材料种类
        int N = sc.nextInt(); // 背包容量

        int[] weights = new int[M];
        int[] values = new int[M];

        for (int i = 0; i < M; i++) weights[i] = sc.nextInt();
        for (int i = 0; i < M; i++) values[i] = sc.nextInt();

        // 从第0个物品开始做回溯
        dfs(0, 0, 0, M, N, weights, values);
        System.out.println(maxVal);
    }

    // 当前是第 index 个物品，当前背包空间是 currWeight，当前价值是 currValue
    public static void dfs(int index, int currWeight, int currValue, int M, int N, int[] weights, int[] values) {
        // 剪枝：超出背包容量
        if (currWeight > N) return;

        // 到达叶子节点（考虑完所有物品）
        if (index == M) {
            maxVal = Math.max(maxVal, currValue);
            return;
        }

        // 分支1：不选当前物品
        dfs(index + 1, currWeight, currValue, M, N, weights, values);

        // 分支2：选当前物品
        dfs(index + 1, currWeight + weights[index], currValue + values[index], M, N, weights, values);
    }
}
```
### 使用dp数组动态规划
1. 确定dp数组以及下标的含义
* 使用二维数组，因为我们有两个维度分别表示：物品（i）和背包容量（j）
* ![image](https://github.com/user-attachments/assets/081ac42c-cc9e-4ec7-b6fc-88d697d2b07c)
* dp[i][j] 表示从下标为[0-i]的物品里任意取，放进容量为j的背包，价值总和最大是多少。
2. 确定递推公式
* 用dp[1][4]来举例（下标为0-1的物品里任意取，放入容量为4的包，价值总和最大的值）
* 求取dp[1][4]有两种方式：放物品1，不放物品1）
* 如果不放物品1， 那么背包的价值就是do[0][4]，只能放物品0的最大
* 如果放物品1，那么我们要流出物品1的容量，目前容量是4，物品1的容量是3，拿剩下的容量就是1
* 那么放物品1的最大价值就是：物品1的价值+剩余容量能放的最大的价值=cost[1]+dp[0][1] (因为物品1已经被放过了，所以i最多只能到物品0，然后j是1是因为容量只有1）
* 在放物品1和不放物品1中我们取最大值：dp[1][4] = max(dp[0][4], value[1] + dp[0][1])
* 抽象成递归公式
* dp[i][j] = max(dp[i-1][j], value[i]+dp[i-1][j-weight[i]]
3. 数组如何初始化
* 状态转移方程 dp[i][j] = max(dp[i - 1][j], dp[i - 1][j - weight[i]] + value[i]); 可以看出i 是由 i-1 推导出来，那么i为0的时候就一定要初始化。
* 第一列初始化dp[i][0]:最大容量只能是0，所以第一列一切都是0（装不进任何有价值的东西）
* 第一行初始化：第一行只能装下物品0，所以一旦容量>= 物品0的重量，之后的dp[0][j]就都是value[0]
```
// 第一列直接从第二个开始，因为第一行已经初始化了【0】【0】
for (int i = 1; i<weight.length; i++){
  do[i][0] = 0;
}
// 从背包能装下物品0开始，每一个容量只能装下物品0，就会拥有物品0的价值
for (int j = weight[0]; j<= bagweight; j++){
  dp[0][j] = value[0]
}
```
* dp[0][j] 和 dp[i][0] 都已经初始化了，那么其他下标应该初始化多少呢？
* 其实从递归公式： dp[i][j] = max(dp[i - 1][j], dp[i - 1][j - weight[i]] + value[i]); 可以看出dp[i][j] 是由左上方数值推导出来了，那么 其他下标初始为什么数值都可以，因为都会被覆盖。
* 初始化成0就可以了
4. 确定遍历顺序：先遍历i再遍历j，或者先遍历j再遍历i，都是可以的
* 虽然两个for循环遍历的次序不同，但是dp[i][j]所需要的数据就是左上角，根本不影响dp[i][j]公式的推导！
* dp[i-1][j]和dp[i - 1][j - weight[i]] 都在dp[i][j]的左上角方向（包括正上方向）
5. 举例推导dp数组
![image](https://github.com/user-attachments/assets/14712a9e-69e9-49db-be21-2924fc6f700b)
### 二维dp数组解法
```java
import java.util.*;

public class Main{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int M = sc.nextInt();
        int N = sc.nextInt();
        int[] weights = new int[M];
        int[] values = new int[M];
        for (int i = 0; i<M;i++){
            weights[i] = sc.nextInt();
        }
        for(int i=0; i<M; i++){
            values[i] = sc.nextInt();
        }
        System.out.println(findMaxVal(M,N,weights,values));
        // System.out.println(res);
    }

    public static int findMaxVal(int M, int N, int[]weights, int[] values){
        //容量从0开始，要一直包括到N，所以初始化要是N+1
        int[][] dp = new int[M][N+1];
        for (int i = 1; i<M; i++){
            dp[i][0] = 0;
        }
        for (int j = 0; j<=N; j++){
            if (j>= weights[0]){
                dp[0][j] = values[0];
            }else{
                dp[0][j]=0;
            }
        }

        for (int i = 1; i<M;i++){
            for (int j=1;j<=N;j++){
                if (j<weights[i]){dp[i][j] = dp[i-1][j];}
                else{
                    dp[i][j] = Math.max(dp[i-1][j], values[i] +dp[i-1][j-weights[i]]);
                }
                
                System.out.print(dp[i][j] + " ");
            }
            System.out.println();
        }
        return dp[M-1][N];
    }
        
}
```
## 使用一维数组来解决01背包问题
* **初始化**
* 代码随想录认为，dp[j]全部初始化成0就可以
* dp[j]表示：容量为j的背包，所背的物品价值可以最大为dp[j]，那么dp[0]就应该是0，因为背包容量为0所背的物品的最大价值就是0。
* 那么dp数组除了下标0的位置，初始为0，其他下标应该初始化多少呢？
* 看一下递归公式：dp[j] = max(dp[j], dp[j - weight[i]] + value[i]);
* dp数组在推导的时候一定是取价值最大的数，如果题目给的价值都是正整数那么非0下标都初始化为0就可以了。这样才能让dp数组在递归公式的过程中取的最大的价值，而不是被初始值覆盖了。
* 其实我自己觉得直接把它初始化成二维dp数组的第一行也可以

* **遍历顺序**
* 遍历物品是正序还是到序：
* 二维dp数组遍历顺序，背包容量必须从大到小，保证物品i只被放入背包1次，一旦正序遍历，物品0就会被重复加入多次（当前轮次更新的 dp[j - weights[i]]会被重复使用）
* 看这个例子：
* ![Screenshot 2025-04-04 at 20 04 05](https://github.com/user-attachments/assets/b62f04f3-9baa-4260-864d-934abf5fd30d)
* 15是物品0（i）的价值
* dp[2]为什么会是30呢？说明物品0被加入了2次，起始dp[2]的值应该是15。
* 如果使用到序遍历
* ![Screenshot 2025-04-04 at 20 07 22](https://github.com/user-attachments/assets/7ebd321a-99de-4bc4-a5a6-de062a246e33)
* 这个情况中计算dp[2]的时候dp[1]还是0，这样符合01背包的规则，保证每个物品只能加入一次



* 先遍历背包还是物品？
* 再来看看两个嵌套for循环的顺序，代码中是先遍历物品嵌套遍历背包容量，那可不可以先遍历背包容量嵌套遍历物品呢？
* 不可以！
* 因为一维dp的写法，背包容量一定是要倒序遍历（原因上面已经讲了），如果遍历背包容量放在上一层，那么每个dp[j]就只会放入一个物品，即：背包里只放入了一个物品。
* 如果这样写
```java
for (int j = 0; j <= N; j++) {     // 遍历背包容量
    for (int i = 0; i < M; i++) {  // 遍历物品
        if (j >= weights[i]) {
            dp[j] = Math.max(dp[j], dp[j - weights[i]] + values[i]);
        }
    }
}
```
* 这相当于“对一个容量 j 来说，我遍历所有物品来看看能不能装进去”。
* 乍一看没毛病，但你其实在同一轮里允许了重复使用物品！（因为 dp[j - weights[i]] 可能已经包含了 values[i] 了）
```java
import java.util.*;

public class Main{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int M = sc.nextInt();
        int N = sc.nextInt();
        int[] weights = new int[M];
        int[] values = new int[M];
        for (int i = 0; i<M;i++){
            weights[i] = sc.nextInt();
        }
        for(int i=0; i<M; i++){
            values[i] = sc.nextInt();
        }
        System.out.println(findMaxVal(M,N,weights,values));
        // System.out.println(res);
    }

    public static int findMaxVal(int M, int N, int[]weights, int[] values){
        //容量从0开始，要一直包括到N，所以初始化要是N+1
        int[] dp = new int[N+1];
        //初始化一维数组
        //这里我是按照二维ddp数组的第一行来进行初始化的
        for (int j = 0; j<=N; j++){
            if (j>= weights[0]){
                dp[j] = weights[0];
            }else{
                dp[j]=0;
            }
        }
        for (int i =1; i<M; i++){
            //使用后序遍历，确保values[i]+dp[j-weights[i]]不会被重复使用
            for (int j = N; j>=weights[i];j--){
                dp[j] = Math.max(dp[j], values[i]+dp[j-weights[i]]);
            }
        }
        return dp[N];
    }
        
}
```
## 416. Partition Equal Subset Sum
* https://leetcode.com/problems/partition-equal-subset-sum/
* 文章：https://programmercarl.com/0416.%E5%88%86%E5%89%B2%E7%AD%89%E5%92%8C%E5%AD%90%E9%9B%86.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 视频：https://www.bilibili.com/video/BV1BU4y177kY?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 看起来是回溯，其实是查询背包能不能放入sum/2 的的内容
* 每个数字的weight是它本身，价值也是它本身
* 先贴一下回溯算法
### 回溯算法（会超时）
```java
class Solution {
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int num:nums){
            sum += num;
        }
        if (sum%2==1) return false;
        int halfSum = sum/2;
        return backtrack(nums,0,halfSum,0);

    }
    private boolean backtrack(int[]nums, int startIndex, int target, int currentSum){
        if (currentSum > target) return false;
        if (currentSum == target) return true;
        for (int i = startIndex; i<nums.length; i++){
            if (backtrack(nums, i+1, target, currentSum + nums[i])) return true;
        }
        return false;
    }
}
```
### 01背包解法
* 可以用01背包解是因为每个数字只能被加入sum一次
* 但是我们要求的问题不是背包能装的最大值，而是背包能不能装满
1.首先要明确dp数组的下标和含义
* 背包的价值：就是nums中元素的值
* 背包的重量：就是nums中元素的重量
* 当背包最大容量为target，价值也是target
* 如果dp[j] == j 说明，集合中的子集总和正好可以凑成总和j，理解这一点很重要。
* 那dp二维数组应该怎么初始化呢？
  * i：一共有nums.length种物品，所以nums.length 就可以
  * j：我们想找的价值/重量是sum/2，所以这个维度初始化成sum/2就可以。另外记得因为我们的背包容量从0开始，所以其实初始化的时候维度是sum/2 +1
2.  确定递推公式
* 01背包的递推公式为：dp[i][j] = max(dp[i-1][j], dp[j - weight[i]] + value[i]);
* 本题物品i重量是nums[i],价值也是nums[i]，所以dp[i][j] = max(dp[i-1][j], dp[j-nums[i]] + nums[i]]
3. dp数组如何促使话
* 画一画图，第一行肯定是如果j< nums[0],就初始化成0，不然就初始化成nums[0]
* 第一列（最大容量为0）只能是0
4.确定遍历顺序
* 我写的版本是2维度dp数组，所以从前往后遍历（dp[i][j]从左上方的值推导而来），并且我个人习惯先遍历物品再遍历背包容量
* 但是代码随想录的写法是一维数组，所以外层遍历物品，内层遍历背包容量，而且内层for循环到序遍历
5. 举例推导dp数组
* ![image](https://github.com/user-attachments/assets/ae43733f-7d34-435e-88d1-56ae3b860170)
* 最后dp[11] == 11，说明可以将这个数组分割成两个子集，使得两个子集的元素和相等。
### 01背包二维dp数组
* 时间复杂度：O(n^2)
* 空间复杂度：O(n^2)，虽然dp数组大小为一个常数，但是大常数
#
```java
class Solution {
    public boolean canPartition(int[] nums) {
        int len = nums.length;
        int sum = 0;
        for (int i = 0; i<len;i++){
            sum += nums[i];
        }
        if (sum%2!=0) return false;
        int target = sum/2;

        int [][] dp = new int[len][target+1];
        for (int i = 0; i<len; i++){
            dp[i][0] = 0;
        }
        for(int j = 1; j<= target; j++){
            if (j<nums[0]) {dp[0][j] = 0;}
            else{dp[0][j] = nums[0];}
        }
        for (int i = 1; i<len;i++){
            for (int j=1; j<= target;j++){
                if (j<nums[i]){
                    dp[i][j] = dp[i-1][j];
                }else{
                    dp[i][j] = Math.max(dp[i-1][j], nums[i]+dp[i-1][j-nums[i]]);
                }
                if (dp[i][j] == target) {return true;}
            }
        }
        return false;

    }
}
```
## 01背包一维数组解法
* 时间复杂度：O(n^2)
* 空间复杂度：O(n)，虽然dp数组大小为一个常数，但是大常数

```java
class Solution {
    public boolean canPartition(int[] nums) {
        int len = nums.length;
        int sum = 0;
        for (int i = 0; i<len;i++){
            sum += nums[i];
        }
        if (sum%2!=0) return false;
        int target = sum/2;

        int []dp = new int[target+1];

        for(int j = 0; j<= target; j++){
            if (j<nums[0]) {dp[j] = 0;}
            else{dp[j] = nums[0];}
        }
        for (int i = 1; i<len;i++){
            for (int j=target; j>=0;j--){
                if (j<nums[i]){
                    continue;
                }else{
                    dp[j] = Math.max(dp[j], nums[i]+dp[j-nums[i]]);
                }
                if (dp[j] == target) {return true;}
            }
        }
        return false;

    }
}
```
