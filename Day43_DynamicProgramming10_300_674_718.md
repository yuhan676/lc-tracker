## 300. Longest Increasing Subsequence
* https://leetcode.com/problems/longest-increasing-subsequence/description/
* 文章：https://programmercarl.com/0300.%E6%9C%80%E9%95%BF%E4%B8%8A%E5%8D%87%E5%AD%90%E5%BA%8F%E5%88%97.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 视频：https://www.bilibili.com/video/BV1ng411J7xP?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 首先明确什么是子序列：“子序列是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序”。**并不是连续子序列！我一开始以为是连续子序列**
* 子序列：定义：A **subsequence** is an array that can be derived from another array by deleting some or no elements without changing the order of the remaining elements
* 连续子序列=subarray！
1. dp[i]的含义： i之前包括i的以nums[i]结尾的最长递增子序列的长度
* 因为用尾部的元素比较可以计算递增
2. 递推公式
* 假设j是i的前面，且dp[j] <dp[i],那么 dp[i] = dp[j] +1
* ![Screenshot 2025-04-14 at 15 44 24](https://github.com/user-attachments/assets/d8e60897-3965-4b53-ba0e-1355afc52f0c)
* j的定义是什么呢？j就是从0到i之间的所有位数，我们要在所有dp[j]中取最大值，不停地更新dp[i]
* 那么dp[i] = max(dp[j]+1, dp[i])就可以不停地更新dp[i]了
* 比较最大值是因为如果我们直接dp[i] = dp[j] + 1就会覆盖掉上一个结果，假如上一个结果的dp[i]比较大，就会丢失最大结果
3. dp数组初始化
* dp[i]至少长度都是i，因为都至少包含nums[i]
* 所以所有默认初始值都是1就可以了
4. 遍历顺序
* 我们求的是递增子序列，依赖前面的元素和当前元素进行比较，所以从左到右，从小到大遍历
```
for (i=1; i<nums.length; i++) //直接从第二个元素开始{
  for (j= 0; j<i; i++{
    if(nums[i]>nums[j]){递推公式}
  }
}
```
* 最大的结果存在哪里呢？不一定是dp数组的最后一位
* 应该遍历dp数组，找到最大值
* **本题最关键的是要想到dp[i]由哪些状态可以推出来，并取最大值**
* 时间复杂度: O(n^2)
* 空间复杂度: O(n)
```java
class Solution {
    public int lengthOfLIS(int[] nums) {
        //dp[i]意味着从0到i（inclusive）的子序列中最大递增子序列的长度
        int[] dp = new int[nums.length];
        //全部初始化成1
        //这里学习一下Arrays.fill(array, value)的方法
        Arrays.fill(dp,1);
        //以下这种方法不可以用： 是按值拷贝，修改的是临时变量num而不是dp内的数值本身。用for循环或者Arrays.fill才能直接修改数组本身的值
        // for (int num:dp){
        //     num = 1;
        // }
        for (int i =1; i<nums.length; i++){
            for (int j=0; j<i; j++){
                //从0到i-1之间的j一个一个和i对比，如果nums[j]比nums[i]小，那么dp[i]就可以在当前值dp[i]和dp[j]+1之间挑一个
                if (nums[i]>nums[j]){
                    dp[i] = Math.max(dp[j]+1,dp[i]);
                }
            }
        }
        int res = 1;
        for (int i=0; i< nums.length; i++){
            System.out.print(dp[i] + " ");
            res = Math.max(res, dp[i]);
        }
        return res;
    }
}
```
## 674. Longest Continuous Increasing Subsequence
* https://leetcode.com/problems/longest-continuous-increasing-subsequence/description/
* 文章：https://programmercarl.com/0674.%E6%9C%80%E9%95%BF%E8%BF%9E%E7%BB%AD%E9%80%92%E5%A2%9E%E5%BA%8F%E5%88%97.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 视频：https://www.bilibili.com/video/BV1bD4y1778v?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
### 暴力解法
* 我自己写出来的，一共两层for循环，找从每一个nums[i]开始，最长的连续递增子序列有多长，并且记录最长的子序列长度
* 时间复杂度：n*2
* 空间复杂度：1
```java
class Solution {
    public int findLengthOfLCIS(int[] nums) {
        int maxLength = 1;
        for (int i=0; i<nums.length;i++){
            //从每个nums[i]开始，currlength都要重置成1
            int currLength = 1;
            for (int j = i+1; j<nums.length; j++){
                if (nums[j]>nums[j-1]){
                    currLength+=1;
                    maxLength = Math.max(currLength,maxLength);
                }else{
                    //从当前i开始的连续递增子数组已经找到了，break
                    break;
                }
            }
        }
        return maxLength;
    }
}
```
### 动态规划解法
1. dp数组和下标定义：dp[i] = 以下标i为结尾的连续递增子序列长度为dp[i]
2. 递推公式：
* 因为本题要求连续递增子序列，所以就只要比较nums[i]与nums[i - 1]，而不用去比较nums[j]与nums[i] （j是在0到i之间遍历）。
* 既然不用j了，那么也不用两层for循环，本题一层for循环就行，比较nums[i] 和 nums[i - 1]。
* 那么如果nums[i] > nums[i-1], dp[i] = dp[i-1] +1
3. dp数组初始化：以下标i为结尾的连续递增的子序列长度最少也应该是1，即就是nums[i]这一个元素。也就是说全都Arrays.fill(dp,1)就行。
4. 遍历顺序：从左到右，因为dp[i]依赖于dp[i-1]
5. 举例推导
* 已输入nums = [1,3,5,4,7]为例，dp数组状态如下：
* ![image](https://github.com/user-attachments/assets/811e56a5-6b47-4463-8fcd-d62a4c03503d)
* 时间复杂度：n
* 空间复杂度：n
```java
class Solution {
    public int findLengthOfLCIS(int[] nums) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp,1);
        int maxLength = 1;

        for (int i =1; i<nums.length; i++){
            if (nums[i]>nums[i-1]){
                dp[i] = dp[i-1] + 1;
            }
            if (dp[i]> maxLength){maxLength=dp[i];}
        }
        return maxLength;
    }
}
```
### 贪心解法
* 总的来说和动态规划解法没什么太大区别，和暴力解法也挺像的
* 如果nums[i] <= nums[i-1] 那么currlength要归零
* 时间复杂度：O(n)
* 空间复杂度：O(1)
```java
class Solution {
    public int findLengthOfLCIS(int[] nums) {
        //记得处理极端条件
        if (nums.length ==0) return 0;
        if (nums.length ==1) return 1;
        int currlength = 1;
        int maxLength = 1;

        for (int i =1; i<nums.length; i++){
            if (nums[i]>nums[i-1]){
                currlength+=1;
            }
            else{
                currlength=1;
            }
            maxLength = Math.max(currlength,maxLength);
        }
        return maxLength;
    }
}
```
### 两题对比
* 在动规分析中，关键是要理解和动态规划：300.最长递增子序列 (opens new window)的区别
* 要联动起来，才能理解递增子序列怎么求，递增连续子序列又要怎么求。
* 概括来说：**不连续递增子序列的跟前0-i 个状态有关，连续递增的子序列只跟前一个状态有关**

## 718. Maximum Length of Repeated Subarray
* https://leetcode.com/problems/maximum-length-of-repeated-subarray/description/
* 文章：https://programmercarl.com/0718.%E6%9C%80%E9%95%BF%E9%87%8D%E5%A4%8D%E5%AD%90%E6%95%B0%E7%BB%84.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 视频：https://www.bilibili.com/video/BV178411H7hV?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 注意这一题说是subarray，就是连续子序列！
### 暴力解法
* 两层for循环遍历nums1,nums2,找到开头节点相同的位置
* 指针找到了，用while循环查看从指针位置开始，两个array的subarray是否相同，有多长相同
* 时间复杂度：O(n*m*min(n,m))
* 空间复杂度：O(1)
```java
class Solution {
    public int findLength(int[] nums1, int[] nums2) {
        int maxLength=0;
        for (int i =0; i<nums1.length;i++){
            for (int j =0; j<nums2.length;j++){
                if (nums1[i]==nums2[j]){
                    int res = 1;
                    int i1 = i+1;
                    int j1 = j+1;
                    while (i1<nums1.length && j1<nums2.length){
                        if (nums1[i1]==nums2[j1]){
                            res+=1;
                            i1++;
                            j1++;
                        }else{
                            break;
                        }
                    }
                    if (res>maxLength){maxLength = res;}
                }
            }
        }
        return maxLength;
    }
}
```
### 动态规划解法
* 这一题的初始化和定义都是根据怎么初始化方便来决定的
* 需要使用2维dp数组，因为我们需要处理两个序列
* 二维dp数组每一个格子表达nums1里的元素和nums2里的元素的比较情况
* ![Screenshot 2025-04-14 at 18 35 23](https://github.com/user-attachments/assets/c8722d1b-7633-4e19-b1df-5bc66bbc4477)
1. 确定dp定义和下标含义
* dp[i][j]= 以[0,i-1]的nums1的子数组，和以[0,j-1]的nums2的子数组，这两个数组的最长公共子序列的长度
* 定义[0,i], [0,j]不行吗？其实也可以。但是上面这种方法可以帮我们精简初始化的处理。如果用[0,i]我们就必须手动初始化第一列和第一行。如果使用[i-1]的话，就全部初始化成0，剩下的交给递推公式即可
2. 递推公式
* 我们比较的是元素之间是否相同
* 那么如果nums1[i-1] == nums2[j-1], 那么dp[i][j] = dp[i-1][j-1] + 1;
3. 初始化：根据dp[i][j]的定义，dp[i][0] 和dp[0][j]其实都是没有意义的！（第一列和第一行都没有意义）
* 但dp[i][0] 和dp[0][j]要初始值，因为 为了方便递归公式dp[i][j] = dp[i - 1][j - 1] + 1;
* 举个例子A[0]如果和B[0]相同的话，dp[1][1] = dp[0][0] + 1，只有dp[0][0]初始为0，正好符合递推公式逐步累加起来。
4. 遍历顺序：外层for循环遍历A，内层for循环遍历B。反之亦可
* 同时题目要求长度最长的子数组的长度。所以在遍历的时候顺便把dp[i][j]的最大值记录下来。
5. 拿示例1中，A: [1,2,3,2,1]，B: [3,2,1,4,7]为例，画一个dp数组的状态变化，如下：
* ![image](https://github.com/user-attachments/assets/e755855f-cbba-4a06-9d9b-2cd861c7ddb5)
```java
class Solution {
    public int findLength(int[] nums1, int[] nums2) {
        //因为dp[i][j]记录的是(0,i-1), (0,j-1)的数值，所以dp要多出来一位，才能记录到nums1和nums2尾部的最大重复子数组长度
        int[][] dp = new int[nums1.length+1][nums2.length+1];
        //递推公式使用dp[i][j] = nums1(0,i-1)和nums2(0,j-1)的最大重复子数组长度，自动第一行和第一列初始化成0
        //注意这里边界是<=nums1.length因为我们要跳到index=nums1.length, 才能更新dp[nums1.length-1]（也就是最后一位）
        int res = 0;
        for (int i=1; i<=nums1.length;i++){
            for (int j=1; j<=nums2.length;j++){
                if (nums1[i-1]==nums2[j-1]){
                    dp[i][j] = dp[i-1][j-1]+1;
                }
                if (dp[i][j]>res) {res = dp[i][j];}
            }
        }
        return res;
    }
}
```
