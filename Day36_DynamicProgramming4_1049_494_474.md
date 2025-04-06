## 1049. Last Stone Weight II
* https://leetcode.com/problems/last-stone-weight-ii/description/
* 文章：https://programmercarl.com/1049.%E6%9C%80%E5%90%8E%E4%B8%80%E5%9D%97%E7%9F%B3%E5%A4%B4%E7%9A%84%E9%87%8D%E9%87%8FII.html
* 视频：https://www.bilibili.com/video/BV14M411C7oV?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 怎么能想到是背包问题的。。。
* 题意：
  * 有一堆石头，每块石头的重量都是正整数。
  * 每一回合，从中选出任意两块石头，然后将它们一起粉碎。假设石头的重量分别为 x 和 y，且 x <= y。那么粉碎的可能结果如下：
  * 如果 x == y，那么两块石头都会被完全粉碎；
  * 如果 x != y，那么重量为 x 的石头将会完全粉碎，而重量为 y 的石头新重量为 y-x。
  * 最后，最多只会剩下一块石头。返回此石头最小的可能重量。如果没有石头剩下，就返回 0。
* 观察一下相撞的结果， 如果x==y, 结果=0==y-x
* 如果y>x, 结果=y-x
* 那就是无论如何结果都是y-x,想要结果最小化，那最好是让y和x尽量相等
* 那我们是不是就可以把石头分成相近重量的两堆，这样两两相撞的结果就也最小化了
* 那这样就和day35的分割等和子集几乎一模一样了，因为我们想把weight sum 分割成两堆重量为weight sum /2 和 weight sum - weight sum /2的石头堆
* 但那题是返回boolean，这题是返回最小数值
* 所以找到了dp数组（i维度=stones.length; j维度=weightSum/2)中最右下角的值以后（尽量装到近似weightSum/2的值的值），找到它和剩下的石头的重量的差就可以了
### 二维01背包（我自己写的！）
```java
class Solution {
    public int lastStoneWeightII(int[] stones) {
        int stoneCount = stones.length;
        int weightSum = 0;
        for (int i = 0; i<stoneCount;i++){
            weightSum+= stones[i];
        }
        int halfWeightSum = weightSum/2;
        int [][] dp = new int[stoneCount][halfWeightSum+1];

        for (int i = 0; i<stoneCount; i++){
            dp[i][0] = 0;
        }
        for (int j=1; j<= halfWeightSum; j++){
            if (j<stones[0]) {dp[0][j] = 0;}
            else { dp[0][j] = stones[0];}
        }
        for (int i=1; i<stoneCount; i++){
            for (int j = 1; j<=halfWeightSum;j++){
                if (j<stones[i]){dp[i][j] = dp[i-1][j];}
                else{
                    dp[i][j] = Math.max(dp[i-1][j], stones[i] + dp[i-1][j-stones[i]]);
                }
            }
        }
        //打印检查
        for (int i = 0; i< stoneCount;i++){
            for (int j = 0; j<=halfWeightSum;j++){
                System.out.print(dp[i][j] + " ");
            }
            System.out.println();
        }
        //记得找absolute value
        return Math.abs(dp[stoneCount-1][halfWeightSum]-(weightSum - dp[stoneCount-1][halfWeightSum]));
    }
}
```
### 优化成一维dp数组01背包
* 和前面的题一样，一维一定是先循环物品再循环容量
* 容量循环得到序，避免重复放入同样一个石头
```java
class Solution {
    public int lastStoneWeightII(int[] stones) {
        int stoneCount = stones.length;
        int weightSum = 0;
        for (int i = 0; i<stoneCount;i++){
            weightSum+= stones[i];
        }
        int halfWeightSum = weightSum/2;
        int [] dp = new int[halfWeightSum+1];

        for (int j=1; j<= halfWeightSum; j++){
            if (j<stones[0]) {dp[j] = 0;}
            else { dp[j] = stones[0];}
        }
        for (int i=1; i<stoneCount; i++){
            for (int j = halfWeightSum; j>=0;j--){
                if (j<stones[i]){continue;}
                else{
                    dp[j] = Math.max(dp[j], stones[i] + dp[j-stones[i]]);
                }
            }
        }        
        //记得找absolute value
        return Math.abs(dp[halfWeightSum]-(weightSum - dp[halfWeightSum]));
    }
}
```
## 494. Target Sum
* https://leetcode.com/problems/target-sum/description/
* 文章：https://programmercarl.com/0494.%E7%9B%AE%E6%A0%87%E5%92%8C.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 视频：https://www.bilibili.com/video/BV1o8411j73x?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
### 我一开始写的回溯方法（有bug）
```java
class Solution {
    int count = 0;
    public int findTargetSumWays(int[] nums, int target) {
        backtrack(nums,target,0,0);
        return count;
    }
    private void backtrack(int[]nums, int target, int startIndex, int sum){
        if (startIndex == nums.length) return;
        if (sum == target){
            count +=1;
            return;
        }
        for (int i = startIndex; i<nums.length-1;i++){
            backtrack(nums,target,i+1, sum + nums[i+1]);
            backtrack(nums,target,i+1, sum-nums[i+1]);
        }
    }
}
```
* ❌ 问题分析：
* 用了一个 for 循环，但这道题其实是每个位置都只能选一次加 or 减 —— 也就是说它不是组合问题，而是“路径问题”，直接按顺序递归遍历就好，不需要 for。
* 你的回溯起点是 i+1，并且跳过了 i = 0 的加减选择，这会导致初始元素的选择被忽略。
* 终止条件不对：你应该在 startIndex == nums.length 的时候判断 sum == target，而不是提前判断。
### 改完bug以后的回溯（没超时）
* 时间复杂度：2^n（每个元素有选和不选两种可能）
```java
class Solution {
    int count = 0;
    public int findTargetSumWays(int[] nums, int target) {
        backtrack(nums,target,0,0);
        return count;
    }
    private void backtrack(int[]nums, int target, int index, int sum){
        //只有到遍历到底的时候才检查是否sum==target
        if (index == nums.length) {
            if (sum == target){
                count++;
            }
            return;
        }
        //sum 加上当前数字
        backtrack(nums,target,index+1, sum - nums[index]);
        //sum减去当前数字
        backtrack(nums,target,index+1, sum + nums[index]);
        
    }
}
```
### 01背包
* 这道题其实递推公式挺绕的
* 首先我们看看怎么把这题抽象成成背包问题来解答
* 我们知道这些数字一定有是正数，有些是负数，而且正数和+负数和=target
* 所以positiveSum + NegativeSum = TotalSum; PositiveSum - NegativeSum = target
* 2* positiveSum = target + TotalSum => positiveSum = (target+totalSum)/2
* 这样我们要求的就是，能不能凑出和为positivesum的子集了，成为了一个01背包问题
* 剪枝
  * 但是有两个需要考虑的地方，假如target+totalSum是奇数呢？那就直接返回0，因为**我们不可能从整数数组中选出若干数，使它们加起来是一个非整数。**
  * 另一个要考虑的地方：如果target大于totalsum，也是没有可能找到满足条件的解法的，返回0。。。但是如果target是负数呢？
  * 有两种方法：
1. if (sum < Math.abs(target)) return 0; 因为所有 nums[i] ≥ 0，所以 sum 是总和的最大正值。如果 target 的绝对值比 sum 还大，那肯定无论怎么加减都达不到 target，比如：nums = [1, 2, 3], sum = 6，target = 7 → 无论怎么组合正负号，也不会等于 7所以这个判断能更早、更直接地剪掉不可能的情况 ✅
2. if ((sum + target) % 2 != 0 || (sum + target) < 0) return 0; 确保bagsize是一个非负整数
**递归五部曲**
1. 确定dp数组以及下标
* 二维数组dp，dp[i][j]：使用 下标为[0, i]的nums[i]能够凑满j（包括j）这么大容量的包，有dp[i][j]种方法。
2. 确定递推公式：当我们可以选择下标为（0-i）的物品，我们一共有两种走法：不选择第i个数（dp[i-1][j]，即背包最大容量为j时，不选i凑满j的方法数量），和选择第i个数（dp[i-1][j - nums[i]]：我们选了第 i 个数，那我们就要看看剩下 j - nums[i] 的部分用前 i-1 个数能不能凑出来。）
* 递推公式就是：dp[i][j] = dp[i-1][j] + dp[i-1][j-nums[i]]
* 和之前爬楼梯的题有一点像（都是求路径综合），不过爬楼梯每次只能走一步或者两步（dp[i] = dp[i - 1] + dp[i - 2];），这一题是看选不选某个数字，选的那些数字能不能加出目标
3. 初始化dp数组；dp[i][j] = dp[i-1][j] + dp[i-1][j-nums[i]]，递推方向是有由上方[i-1]和左方[j-nums[i]]得来的
* ![image](https://github.com/user-attachments/assets/56d6d4de-aaa0-49b0-a2d1-d7cfe4013fbf)
* 所以最上行和最左列一定要初始化
* 最上行：记得我们每一个格子记录的是**在容量为j的情况下，有这么多种方式把j的容量放满**，第一行只能放物品[0]所以只有j==物品[0]的价值（也就是nums[0]）的时候才能放满（方法count为1），其他方法count都是0
* 最左列：关于dp[0][0]的值，装满背包容量为0 的方法数量是1，即 放0件物品。
* 例外：假如物品数值就是0呢？如果有两个物品，物品0为0， 物品1为0，装满背包容量为0的方法有几种。
  * 放0件物品
  * 放物品0
  * 放物品1
  * 放物品0 和 物品1
  * 此时是有4种方法。
  * 其实就是算数组里有t个0，然后按照组合数量求，即 2^t 。

4. 确定遍历顺序，我们刚才说了是从上方和左上方推出当前值，所以从上到下，从左到右
5. 举例推导dp数组：
* 输入：nums: [1, 1, 1, 1, 1], target: 3
* bagSize = (target + sum) / 2 = (3 + 5) / 2 = 4
* dp数组状态变化如下
* ![image](https://github.com/user-attachments/assets/a7b18085-0818-4aed-af41-e65dc5f04751)
### 二维dp数组解法
```java
class Solution {
    public int findTargetSumWays(int[] nums, int target) {
        //我们之前已经推导出来bagsize j = （sum+target)/2
        int sum = 0;
        for (int num:nums){
            sum+= num;
        }
        //剪枝：背包里的内容不能加起来是一个小数，所以sum+target必须是双数
        if ((sum + target)%2 != 0) return 0;
        //剪枝：target不能比sum还大
        if (Math.abs(target)>sum) return 0;
        int bagsize = (sum + target)/2;
        //初始化dp数组,dp[i][j]意味着从物品（0-i）中选择，有dp[i][j]种方法可以填满容量为j的背包
        //记得我们的dp列数是bagsize+1，要记录bagsize=0的情况
        int [][] dp = new int [nums.length][bagsize+1];

        //初始化dp第一列：容量为0的背包，每个物品i的情况（这里注意需要注意nums里0的个数，因为把很多个不同的0放入背包也是属于不同的解法）
        int zeroCount = 0;
        for (int i = 0; i<nums.length;i++){
            if (nums[i]==0){
                zeroCount ++;
            }
            // 这里如果直接写dp[i][0] = Math.pow(2,zeroCount)， math.pow是double，会出现 error: incompatible types: possible lossy conversion from double to int
            dp[i][0] = (int) Math.pow(2,zeroCount);
        }
        //初始化第一行：只能放入物品0，能放满的情况就只有j=物品0的情况
        for (int j =1; j<=bagsize; j++){
            if (j==nums[0]){
                dp[0][j] = 1;
            }else{
                dp[0][j] = 0;
            }
        }

        //开始递推
        for (int i =1; i<nums.length;i++){
            for (int j =1; j<=bagsize; j++){
                //加入装不下，那就只能选不装,所以直接有（不包括物品i，装满容量j的解法的数量）那么多种装法
                if (nums[i]>j){
                    dp[i][j] = dp[i-1][j];
                }
                //加入装得下，就有装进包里和不装进包里两种可能，这两种方法各包含互相不重叠的装法数量，所以把它们加起来
                else{
                    dp[i][j] = dp[i-1][j] + dp[i-1][j-nums[i]];
                }
                System.out.print(dp[i][j]+" ");
            }
            System.out.println();
        }
    //最后我们想知道能装满bag size的包包一共有几种方法
    return dp[nums.length-1][bagsize];
    }
}
```
### 一维dp数组解法
* 和之前的简化成dp数组的方法一样
* 确保先遍历物品，再遍历容量
* 遍历容量的时候到序，确保物品不会被重复加进同一个背包
* 但是处理物品值=0的时候和二维数组比较不一样，总体来说还是二维数组更好理解
### 代码随想录：隐形处理物品=0的情况
```java
class Solution {
    public int findTargetSumWays(int[] nums, int target) {
        int sum = 0;
        for (int i = 0; i < nums.length; i++) sum += nums[i];

        //如果target的绝对值大于sum，那么是没有方案的
        if (Math.abs(target) > sum) return 0;
        //如果(target+sum)除以2的余数不为0，也是没有方案的
        if ((target + sum) % 2 == 1) return 0;

        int bagSize = (target + sum) / 2;
        int[] dp = new int[bagSize + 1];
        dp[0] = 1;

        for (int i = 0; i < nums.length; i++) {
            for (int j = bagSize; j >= nums[i]; j--) {
                dp[j] += dp[j - nums[i]];
            }
        }

        return dp[bagSize];
    }
}
```
这一段
```java
for (int j = bagSize; j >= nums[i]; j--) {
    dp[j] += dp[j - nums[i]];
}
```
* 遇到nums[i] = 0 的时候，就会变成dp[j] += dp[j]; // 相当于 dp[j] *= 2
* 也就是说：👉 遇到 0 时，每一个当前的 dp[j] 解法数都会变成 2 倍！
* 在这个一维数组的写法中，因为 j >= nums[i] 包括了 nums[i] == 0 的情况，并且 dp[j - 0] = dp[j]，相当于自动完成了 dp[j] *= 2，所以你确实不需要手动维护 zeroCount，也不需要最后再乘以 2^zeroCount。
### chatgpt：显性处理物品值=0的情况
```java
class Solution {
    public int findTargetSumWays(int[] nums, int target) {
        int sum = 0;
        int zeroCount = 0;
        for (int num : nums) {
            sum += num;
            if (num == 0) zeroCount++;
        }

        if ((sum + target) % 2 != 0 || Math.abs(target) > sum) return 0;
        int bagsize = (sum + target) / 2;
        
        int[] dp = new int[bagsize + 1];
        dp[0] = 1;

        for (int num : nums) {
            if (num == 0) continue; // 先跳过0，最后统一处理
            for (int j = bagsize; j >= num; j--) {
                dp[j] += dp[j - num];
            }
        }

        return dp[bagsize] * (int)Math.pow(2, zeroCount); // 考虑0元素的正负组合
    }
}
```
## 474. Ones and Zeroes
* https://leetcode.com/problems/ones-and-zeroes/description/
* 文章：https://programmercarl.com/0474.%E4%B8%80%E5%92%8C%E9%9B%B6.html
* 视频：https://www.bilibili.com/video/BV1rW4y1x7ZQ?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 对m和n理解成容器的大小就可以了，我们想知道装满这两个容器，最多有多少个元素（物品），就输出多少个
* 最多m个0，n个1
* 这是一个01背包问题，每个物品只可以使用一次，不过背包有两个维度（m和n）
* 多重背包指的是物品可以使用多次
* 一般的01背包只有一个维度：重量
* 现在我们有两个维度，m和n，相当于重量a和重量b
* **动态规划五部曲**
1.dp数组的含义：我们一共有三个变量： m,n，和最多能放进多少物品dp[i][j]。这个时候就不能用一维数组解了，必须至少用二维dp数组能表现这三个维度
* dp[i][j]：最多有i个0和j个1的strs的最大子集的大小为dp[i][j]。
2. 确定递推公式
* 普通的01背包递推公式dp[j] = max(dp[j], dp[j-weight[i]] + value[i])（注意这是一个一维表达式）
* 这就意味着，要么不放这个物品，要么放这个物品，并且加上放了这个物品以后，剩下容量能储存的最大重量
* 在这一题里，物品的重量就是zeroNum个0和oneNum个1
* 递推公式就是dp[i][j] = max (dp[i][j], dp[i-zeroNum][j-oneNum] +1
* dp[i][j]代表不放入这个元素，保持不动
* dp[i-zeroNum][j-oneNum] +1意味着我们得从之前的状态转移过来。选了这个字符串，就会消耗 zeroNum 个 0 和 oneNum 个 1，那么你要从：dp[i - zeroNum][j - oneNum]的那个状态转移过来（那是你选了这个字符串前的状态），然后 +1 表示你多选了一个字符串
* 和纯01背包递推公式的区别：weight有两个：i和j，value是元素的个数，所以加入了当前元素就要+1
3. dp数组如何初始化
* dp[0][0] = 要求装0个1和0个0，那就一个元素也不能放进去，就是0
* 非0下标也初始化成0就可以避免它们覆盖掉之后的值
4. 遍历顺序
* 虽然dp数组是2维，但记住我们的本质是3个维度，这个已经是压缩过了的结果
* 所以和之前的一维解法一样，一定先遍历物品再遍历背包容量，并且遍历背包容量得到序
* 先遍历物品，每个物品就是个字符串，我们需要取出其中的zeroNum, oneNum,这样就得到了每个物品的重量a（m）和重量b（n）
* 再遍历重量（zeroNum, oneNum),而且是到序，这两个值的遍历是两个嵌套for循环，不在乎哪个在外层。到序遍历从m和n开始往前走，>=zeroNum 和 onenum就可以了，小于这两个数就没有意义了，因为反正也加不进去
5. 距离推倒dp数组以输入：["10","0001","111001","1","0"]，m = 3，n = 3为例
* 最后dp数组的状态如下所示：
* ![image](https://github.com/user-attachments/assets/f5923614-c9ae-4dae-a4d9-85a9fbec8e4a)
### 压缩后的二维数组解法
```java
class Solution {
    public int findMaxForm(String[] strs, int m, int n) {
        int [][] dp = new int[m+1][n+1];
        dp[0][0] = 0;

        for (String str: strs){
            int zeroNum = 0;
            int oneNum =0;
            //java不能直接遍历str里的char，必须先转变成charArray
            for (char c:str.toCharArray()){
                if (c=='0'){zeroNum++;}
                else{oneNum++;}
            }
            for (int i = m; i>=zeroNum;i--){
                for (int j=n; j>=oneNum; j--){
                    dp[i][j]=Math.max(dp[i][j], dp[i-zeroNum][j-oneNum]+1);
                }
            }
        }
        return dp[m][n];
    }
}
```
### 代码随想录：三维数组解法：
* 初始化比较麻烦，相对比较难以理解
* 我没有自己写出来，贴了代码随想录的解法
* 它其实是01背包，dp[i][j][k] 表示：只考虑前 i + 1 个字符串（注意不是 i，是包含 i）；且最多用 j 个 0、k 个 1；能组成的子集最大长度。
* 它的初始化方式
```java
// 初始化dpArr数组
int zeroNum = 0;
int oneNum = 0;
for (char c : strs[0].toCharArray()) {
    if (c == '0') zeroNum++;
    else oneNum++;
}
// 从 [zeroNum, m] 和 [oneNum, n] 开始填 1
for (int j = zeroNum; j <= m; j++) {
    for (int k = oneNum; k <= n; k++) {
        dpArr[0][j][k] = 1;
    }
}
```
* 你正在考虑第 0 个字符串，那当前只有这一种可能性：如果你的容量（j 个 0，k 个 1）能容得下这个字符串，那么最多能选的子集长度就是 1（就选这个字符串）。
* 这个初始化只管理了第一层也就是 i == 0 那一层（第一个字符串）；❌ 不需要手动初始化“第一列”或“第一行”，即不是专门去初始化 j == 0 或 k == 0。
* 为什么只需要初始化第一层？为什么我们在二维 0-1 背包里经常初始化第一列（dp[i][0]），但在三维 DP（例如 dp[i][j][k]）中，我们却好像不需要专门初始化第一列和第一行了？
* ![Screenshot 2025-04-06 at 22 39 30](https://github.com/user-attachments/assets/926c11f5-1f3f-4222-9cc3-115cee15ce9c)
* ![Screenshot 2025-04-06 at 22 40 15](https://github.com/user-attachments/assets/3e8cd7b9-59ae-47c9-bad9-9167ed47fa57)
* ![Screenshot 2025-04-06 at 22 41 17](https://github.com/user-attachments/assets/57ebff16-1ec0-4582-ad68-938e722fd681)
* ![Screenshot 2025-04-06 at 22 41 41](https://github.com/user-attachments/assets/5ab560c2-5451-4083-9cea-e954a484d873)


### 三维解法代码
```java
class Solution {
    public int findMaxForm(String[] strs, int m, int n) {
        /// 数组有三个维度
        // 第一个维度：取前面的几个字符串
        // 第二个维度：0的数量限制（背包维度 1 容量）
        // 第三个维度：1的数量限制（背包维度 2 容量）
        int[][][] dpArr = new int[strs.length][m + 1][n + 1];

        /// 初始化dpArr数组
        // 计算第一个字符串的零数量和1数量
        int zeroNum = 0;
        int oneNum = 0;
        for (char c : strs[0].toCharArray()) {
            if (c == '0') {
                zeroNum++;
            } else {
                oneNum++;
            }
        }
        // 当0数量、1数量都容得下第一个字符串时，将DP数组的相应位置初始化为1，因为当前的子集数量为1
        for (int j = zeroNum; j <= m; j++) {
            for (int k = oneNum; k <= n; k++) {
                dpArr[0][j][k] = 1;
            }
        }
        /// 依次填充加入第i个字符串之后的DP数组
        for (int i = 1; i < strs.length; i++) {
            zeroNum = 0;
            oneNum = 0;
            for (char c : strs[i].toCharArray()) {
                if (c == '0') {
                    zeroNum++;
                } else {
                    oneNum++;
                }
            }
            for (int j = 0; j <= m; j++) {
                for (int k = 0; k <= n; k++) {
                    if (j >= zeroNum && k >= oneNum) {
                        // --if-- 当0数量维度和1数量维度的容量都大于等于当前字符串的0数量和1数量时，才考虑是否将当前字符串放入背包
                        // 不放入第i个字符串，子集数量仍为 dpArr[i - 1][j][k]
                        // 放入第i个字符串，需要在0维度腾出 zeroNum 个容量，1维度腾出 oneNum 个容量，然后放入当前字符串，即 dpArr[i - 1][j - zeroNum][k - oneNum] + 1)
                        dpArr[i][j][k] = Math.max(dpArr[i - 1][j][k], dpArr[i - 1][j - zeroNum][k - oneNum] + 1);
                    } else {
                        // --if--  无法放入第i个字符串，子集数量仍为 dpArr[i - 1][j][k]
                        dpArr[i][j][k] = dpArr[i - 1][j][k];
                    }
                }
            }
        }
        return dpArr[dpArr.length - 1][m][n];
    }
}
```
