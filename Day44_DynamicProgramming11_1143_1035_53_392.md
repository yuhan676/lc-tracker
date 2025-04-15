## 1143. Longest Common Subsequence
* https://leetcode.com/problems/longest-common-subsequence/description/
* 文章：https://programmercarl.com/1143.%E6%9C%80%E9%95%BF%E5%85%AC%E5%85%B1%E5%AD%90%E5%BA%8F%E5%88%97.html#%E5%85%B6%E4%BB%96%E8%AF%AD%E8%A8%80%E7%89%88%E6%9C%AC
* 视频： https://www.bilibili.com/video/BV1ye4y1L7CQ?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 学回来题解，没有理解为什么
* 这一题和718最长重复子数组的区别就是：上一题要求连续子数组，这一题不需要，只需要不连续子数组就行，所以dp[i][j]意味着以text1[i-1]inclusive，text2[j-1]inclusive的最大公共子数组；上一题就是要求的以[i-1][j-1]结尾的连续子数组，这一题不要求结尾一定包括[i-1], [j-1]
* 和上一题的区别：因为取得是不连续子数组，所以如果text1[i-1], text2[j-1]不一样，要额外处理
* 如果text1[i - 1] 与 text2[j - 1]不相同，那就看看text1[0, i - 2]与text2[0, j - 1]的最长公共子序列 和 text1[0, i - 1]与text2[0, j - 2]的最长公共子序列，取最大的。
* 即：dp[i][j] = max(dp[i - 1][j], dp[i][j - 1]);
* 如果[i-1], [j-1]相同，那以text1[i-1] 结尾和text2[j-1]结尾的公共子数组的数量就等于[i-2], [j-2] 的数量+1，所以dp[i][j] = dp[i-1][j-1] + 1;
* 初始化：和上一题一样，用dp[i][j] = [i-1], [j-1] 的逻辑就可以简化初始化
* 结果取哪个：不一定最后的位数就会有最大的值，所以遍历过程中要不断用最大的dp[i][j]更新res
* 遍历顺序：左到右
### 二维dp数组解法
```java
class Solution {
    public int longestCommonSubsequence(String text1, String text2) {
        //因为之后用dp[i][j] 代表text1[0,i-1], text2[0,j-1],所以size要+1才能遍历到最后一位
        char[] array1 = text1.toCharArray();
        char[] array2 = text2.toCharArray();
        int[][] dp = new int[text1.length()+1][text2.length()+1];
        int res = 0;
        for (int i =1; i<=text1.length(); i++){
            for (int j=1; j<=text2.length(); j++){
                if (array1[i-1]==array2[j-1]){
                    dp[i][j] = dp[i-1][j-1]+1;
                }else{
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
                if (dp[i][j]> res){res=dp[i][j];}
            }
        }
        return res;
    }
}
```
## 1035. Uncrossed Lines
* https://leetcode.com/problems/uncrossed-lines/description/
* 文章：https://programmercarl.com/1035.%E4%B8%8D%E7%9B%B8%E4%BA%A4%E7%9A%84%E7%BA%BF.html
* 视频：https://www.bilibili.com/video/BV1h84y1x7MP?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 这一题和上一题（1143）一样，是找相同的子序列的（不连续的子数组）
* 想象两个序列[1,2], [2,1]，是不是只能连一条线？那上面那题的方法,检查nums1[i-1] 是否==nums2[j-1]就可以用上，如果相等就dp[i][j] = dp[i-1][j-1] +1; 不想等就dp[i][j] = max(dp[i-1][j], dp[i][j-1])
* 因为相同子序列保证了和数组同样的顺序。我们知道nums1[i-2]nums2[j-1], nums1[i-1]nums2[j-2]一定会相交，两者中取dp值最大的就ok
* 解法和上一题是一模一样的
```java
class Solution {
    public int maxUncrossedLines(int[] nums1, int[] nums2) {
        int[][] dp = new int[nums1.length+1][nums2.length+1];
        int res =0;
        for (int i=1; i<=nums1.length;i++){
            for(int j=1; j<=nums2.length; j++){
                if (nums1[i-1]==nums2[j-1]){
                    dp[i][j] = dp[i-1][j-1]+1;
                }else{
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
                if (dp[i][j]>res) res =dp[i][j];
            }
        }
        return res;
    }
}
```
## 53. Maximum Subarray
* https://leetcode.com/problems/maximum-subarray/description/
* 文章：https://programmercarl.com/0053.%E6%9C%80%E5%A4%A7%E5%AD%90%E5%BA%8F%E5%92%8C%EF%BC%88%E5%8A%A8%E6%80%81%E8%A7%84%E5%88%92%EF%BC%89.html#%E6%80%9D%E8%B7%AF
* 视频：https://www.bilibili.com/video/BV19V4y1F7b5?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 找到和最大的连续子序列
* 我们之前用贪心算法解过，现在看看动态规划
1. dp数组以及下标定义：
dp[i] =以i为结尾的最大的连续子序列的和
2。 递推公式：两种场合：dp[i]=前面的和+nums[i],或者前面的都不要了断档，从i开始重新记录数字，两者取最大值
* dp[i] = max(dp[i-1] + nums[i], nums[i])
3. 初始化： dp[0]就是nums[0],其他都会被覆盖，都初始化成0就可以了
4. 遍历顺序：左到右
* 结果并不一定要在最后，所以随时根据dp[i]大小更新res
* 总体来讲和贪心很像：贪心是如果sum小于0就归零重新计数，这里是在重新计数和叠加sum之间选择最大的
```java
class Solution {
    public int maxSubArray(int[] nums) {
        if (nums.length==1) return nums[0];
        int res = nums[0];
        // dp[i] = the maximum sum of the subarray ending with nums[i]
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        for (int i =1; i<nums.length;i++){
            dp[i] = Math.max(dp[i-1]+nums[i], nums[i]);
            if (dp[i]>res){res = dp[i];}
        }
        return res;
    }
}
```
## 392. Is Subsequence
* https://leetcode.com/problems/is-subsequence/description/
* 文章：https://programmercarl.com/0392.%E5%88%A4%E6%96%AD%E5%AD%90%E5%BA%8F%E5%88%97.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 视频：https://www.bilibili.com/video/BV1tv4y1B7ym?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 又是和1143. Longest Common Subsequence很像的题，假如s是t的子序列，那么两个的最长公共子序列的长度就==s的长度
* 但是在nums1[i-1],nums2[j-1]不相等的时候，不是dp[i][j]=max(dp[i-1][j], dp[i][j-1]),而是dp[i][j] = dp[i][j-1]因为只有大一点的那个stirng可以删除元素，substring是不能删除元素的
* 最大公共序列组可以用dp[s.length()][t.length]来求，意味着两个string都已经遍历到最后了，找了所有可能的公共子序列
* 时间复杂度：O(n × m)
* 空间复杂度：O(n × m)
### dp解法
```java
class Solution {
    public boolean isSubsequence(String s, String t) {
        int[][] dp = new int[s.length()+1][t.length()+1];
        char[] substring = s.toCharArray();
        char[] longstring = t.toCharArray();
        int max = 0;
        for (int j = 1; j<=t.length(); j++){
            for (int i=1; i<=s.length();i++){
                if (longstring[j-1]==substring[i-1]){
                    dp[i][j] = dp[i-1][j-1]+1;
                }else{
                    dp[i][j] = dp[i][j-1];
                }
                if (dp[i][j]>max){max = dp[i][j];}
            }
        }
        if (s.length()==max) {return true;}
        else{ return false;}
    }
}
```
