## 647. Palindromic Substrings
* https://leetcode.com/problems/palindromic-substrings/description/
* 文章：https://programmercarl.com/0647.%E5%9B%9E%E6%96%87%E5%AD%90%E4%B8%B2.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 视频：https://www.bilibili.com/video/BV17G4y1y7z9?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 暴力解法：两层for循环确定起始位置和终止位置，然后再一个for循环判断形成的子串是不是回文的
### 暴力解法
```java
class Solution {
    public int countSubstrings(String s) {
        List<String> substrings = new ArrayList<String>();
        for (int i =0;i<s.length();i++){
            for (int j=i+1; j<=s.length();j++){
                substrings.add(s.substring(i,j));
            }
        }
        int res = 0;
        for (String substring:substrings){
            if (isPalindrom(substring)) {res++;}
        }
        return res;
    }
    private boolean isPalindrom(String s){
        if (s.length()==1) return true; 
        int left = 0;
        int right = s.length()-1;
        while (left <= right){
            if (s.charAt(left)==s.charAt(right)){
                left ++;
                right --;
            }else{
                return false;
            }
        }
        return true;
    }
    
}
```
### 动态规划解法
* 如果我们定义dp[i]是以i结尾的子串的回文子串数量，那会发现有点难找到递推关系
* 怎么找到递推关系：这样想，如果我们已知中间的部分是回文串，而且左右两边的新元素也相等，那么现在这个就是回文子串
* 那这么想，i表示左边界，j表示右边界，dp[i][j]就可以表达这个范围内它是不是一个回文串了。这样只要i＋＋，jーー就能递推下一个更大的子串是不是回文的了
1. dp数组定义：dp[i][j]的定义是：[i,j]的子串是否是回文的，（type=boolean）。发现一个dp[i][j]是true，就给res++
2. 递推公式：i<=j，判断两边新增的i,j是否相同,如果相同，
* 情况1: i==j, 指的是同一个元素，是回文子串
* 情况2：ij仅仅相差1（相邻，比如aa），是回文子串
* 情况1和2都可以概括成：j-1<=1
* 情况3:  j-i >1, 子串长度>2，那么就是边界了，需要判断i+1 到j-1之间的中间部分是不是回文子串。如果中间那一段是回文子串，新家的元素也是相等的
* 那s[i],s[j]不相同的情况下，那就是false呗，那dp数组直接初始化成false就好了
3. dp[i][j]初始化
* 都初始化成false，就能默认一开始都不是回文子串
4. 遍历顺序
* dp[i][j]主要依赖dp[i+1][j-1]，在左下角
*   ![Screenshot 2025-04-17 at 15 15 42](https://github.com/user-attachments/assets/a09353a2-0418-4ae7-af7e-e38ec1eafe1e)
*   只能是从下往上(i从大到小），从左到右（j从小到大，因为是右边界所以j从i开始）
*   写代码的时候注意java会自动初始化成false，而且Arrays.fill()只能填充一维数组
```java
class Solution {
    public int countSubstrings(String s) {
        boolean[][] dp = new boolean[s.length()][s.length()];
        //二维数组默认本来就是false
        // Arrays.fill(dp,false)不能初始化2d数组
        //如果一定想要手动初始化，只能这样：
        //for (int i=0; i<s.length(); i++)
        //{Arrays.fill(dp[i],false);}
        int res = 0;
        for (int i=s.length()-1; i>=0;i--){
            for (int j=i; j<s.length();j++){
                //如果两者相邻/指向同一个
                if (s.charAt(i)==s.charAt(j)){
                    if (j-i<=1){
                        dp[i][j] = true;
                        res ++;
                    }else if (dp[i+1][j-1]){
                        dp[i][j] = true;
                        res ++;
                    }
                }
            }
        }
        return res;
    }
}
```
## 516. Longest Palindromic Subsequence
* https://leetcode.com/problems/longest-palindromic-subsequence/description/
* 文章： https://programmercarl.com/0516.%E6%9C%80%E9%95%BF%E5%9B%9E%E6%96%87%E5%AD%90%E5%BA%8F%E5%88%97.html
* 视频：https://www.bilibili.com/video/BV1d8411K7W6?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 这个求的是最长回文子序列（并不是连续的！），比如bbbab的最长回文子序列就是bbbb
1. dp数组定义
* 我们上次说了如果可以判断里面的字符串是回文的，左右两边加上的新元素是相等的，那么新的字符串就是回文的
* 我们需要二维dp数组才能表示i到j的范围
* dp[i][j]表示[i,j]的回文子序列长度
2. 递推公式
* 如果s[i]==s[j],那么它的里面的字符串的最长子序列的长度就是dp[i+1][j-1], 如果两边相等， 就是在中间的子串的最长子序列长度的基础上+2， dp[i][j] = dp[i+1][j-1]+2
* 不相同的情况：无法将两个元素一起加进来考虑，那我们就有分别考虑s[i,j-1], s[i+1,j]，这两个范围内的最长回文子序列，取最大的
3. 如何进行初始化
* 我们知道dp[i][j]从dp[i+1][j-1]来，就是i==j的情况就是最根基的情况。这个情况需要初始化。如果i==j，单个元素，它就是回文的，长度是1
* 所以当i和j相同的情况下，就都初始化成1，其他都是java默认初始化成0的
```
for (i=0; i<s.length();i++) {dp[i][i] = 1}
```
4. 遍历顺序
* 推导方向：dp[i][j] 从dp[i+1][j-1]来，说明i是从大到小遍历，j是从小到大遍历
```java
class Solution {
    public int longestPalindromeSubseq(String s) {
        int len = s.length();
        int[][] dp = new int[len][len];
        //初始化
        for (int i =0; i<len; i++){
            dp[i][i] = 1;
        }
        for (int i = len-1; i>=0;i--){
            //这里注意我们已经初始化过i=j的状态，如果j==i, 访问dp[i+1][j-1]就可能会访问i+1>j-1的区域，可能是为初始化的值或者越界区域
            for (int j =i+1; j<len; j++){
                if (s.charAt(i)==s.charAt(j)){
                    //假设i=2,j=3, dp[i+1][j-1]=dp[3][2]，这只是访问了一个合法但暂时没有意义的格子（因为我们没有提前赋值这个格子，它的默认值是0），不会抛出异常。这等于j=i+1的时候i,j中间的区间没有东西，默认是0
                    dp[i][j] = dp[i+1][j-1] + 2;
                }else{
                    dp[i][j] = Math.max(dp[i+1][j],dp[i][j-1]);
                }
            }
        }
        return dp[0][len-1];
    }
}
```
## 动态规划总结篇
* https://programmercarl.com/%E5%8A%A8%E6%80%81%E8%A7%84%E5%88%92%E6%80%BB%E7%BB%93%E7%AF%87.html#%E5%8A%A8%E6%80%81%E8%A7%84%E5%88%92%E5%9F%BA%E7%A1%80
