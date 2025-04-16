## 115. Distinct Subsequences
* **这题超级难，而且代码随想录讲得不太好，二刷重温**
* https://leetcode.com/problems/distinct-subsequences/description/
* 文章：https://programmercarl.com/0115.%E4%B8%8D%E5%90%8C%E7%9A%84%E5%AD%90%E5%BA%8F%E5%88%97.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 视频：https://www.bilibili.com/video/BV1fG4y1m75Q?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* ![Screenshot 2025-04-16 at 17 48 40](https://github.com/user-attachments/assets/1db80a88-f563-4534-9b2d-5a3c63d505be)
* 子序列不一定是连续的。
* 换一种问法**s中有多少种删除元素的方式，使s可以变成t**
1. dp数组含义和下标定义：对比两个字符串元素是否相同，我们要定义二维dp数组
* 题目求的是s里面有多少个t这样的子序列
* dp[i][j] = 用 s 的前 i 个字符（即 s[0..i-1]）构造 t 的前 j 个字符（即 t[0..j-1]）的不同子序列的个数。
* ![Screenshot 2025-04-16 at 21 29 23](https://github.com/user-attachments/assets/567cbefe-dba9-4ac7-863f-7549be73feb4)
2. 递推公式
* 这一类问题，基本是要分析两种情况
* s[i - 1] 与 t[j - 1]相等
* s[i - 1] 与 t[j - 1] 不相等
* 当s[i - 1] 与 t[j - 1]相等时，dp[i][j]可以有两部分组成。
* 一部分是用s[i - 1]来匹配，那么个数为dp[i - 1][j - 1]。即不需要考虑当前s子串和t子串的最后一位字母，所以只需要 dp[i-1][j-1]。
* 一部分是不用s[i - 1]来匹配，个数为dp[i - 1][j]，模拟一个把s[i-1]删除掉的操作，对应着dp[i-1][j],也就对应着s[i-2]t[j-1]
* ![Screenshot 2025-04-16 at 21 12 07](https://github.com/user-attachments/assets/0cf46764-a213-44db-b82c-0cea8158c6df)
* 当我们决定**使用 s[i-1] 来匹配 t[j-1]**时，意味着这一步中 s[i-1] 与 t[j-1]形成了匹配。
* 这样，这两个字符就“消耗”掉了，接下来我们只需要考虑如何用 s 的剩余部分（即前 i-1 个字符，即 s[0..i-2]）构造出 t 的剩余部分（即前 j-1 个字符，即 t[0..j-2]）。
* 这里可以看到，使用s[3]匹配的话可以得到一个匹配的bag，不使用s[3]也可以得到一个匹配的bag
* ![Screenshot 2025-04-16 at 20 33 18](https://github.com/user-attachments/assets/acbcf075-aef7-4e71-bb4e-0b6381e5b582)
* 为什么不考虑不使用t[j-1]的情况呢？因为我们要求s里有多少个t，不求t里有多少s，所以我们不用模拟在t中删除的操作
* 不相同的情况：模拟s中元素删除的操作==不考虑这个元素
* else{dp[i][j] = dp[i-1][j]}，同样不需要模拟删除t中元素的操作
* 我觉得以下的解释，两种情况分别是什么很有用
* ![Screenshot 2025-04-16 at 21 31 18](https://github.com/user-attachments/assets/5d645b6e-2d40-4efe-b4bf-2a4bf5f285b9)

3. 初始化
* dp[i][j]从dp[i-1][j-1], dp[i-1][j]来，说明从上方和左上方来
* ![Screenshot 2025-04-16 at 20 39 57](https://github.com/user-attachments/assets/1d57c551-9a03-4bea-88fc-170c3b55faac)
* 所以第一行和第一列一定要进行初始化
* dp[i][0]第一列， dp[0][j]第一行
* dp[i][0]，s有内容，t的指针在0，0-1=-1， 那说明是空字符串。s中有多少个空字符串呢？有1个，因为把s中所有的元素删除完就成了空字符串，所以有一种方法
* dp[0][j] =s是空字符串，t有内容，无论如何删除s中的元素都变不成t，所以只有0种方法
* 交集点：dp[0][0] = 1， 空字符串中有一个空字符串
4.遍历顺序
* 从左到右，从上到下
* s在外，t在内
```java
class Solution {
    public int numDistinct(String s, String t) {
        int[][] dp = new int [s.length()+1][t.length()+1];
        char[] sarry = s.toCharArray();
        char[] tarry = t.toCharArray();

        //初始化
        for (int i=0; i<= s.length(); i++){
            dp[i][0] =1;
        }
        for (int j=1; j<=t.length();j++){
            dp[0][j] = 0;
        }

        for (int i=1; i<=s.length();i++){
            for (int j=1; j<=t.length();j++){
                if (sarry[i-1]==tarry[j-1]){
                    //新元素和t的最后一个元素相等，那么构成总数的就是：原来能够成多少个t（dp[i-1][j]），加入新的元素能构成多少个（dp[i-1][j-1]）
                    dp[i][j] = dp[i-1][j-1] + dp[i-1][j];
                }else{
                    //如果不相等，就只有不使用的可能性
                    dp[i][j] = dp[i-1][j];
                }
            }
        }
        return dp[s.length()][t.length()];
    }
}
```
## 583. Delete Operation for Two Strings
* **没完全搞懂代码随想录的解法**
* **也可以直接使用最长公共子串的方法，求最长公共子串的长度，然后两个word的长度分别减去这个最长公共子串的长度，求和
* https://leetcode.com/problems/delete-operation-for-two-strings/
* 文章：https://programmercarl.com/0583.%E4%B8%A4%E4%B8%AA%E5%AD%97%E7%AC%A6%E4%B8%B2%E7%9A%84%E5%88%A0%E9%99%A4%E6%93%8D%E4%BD%9C.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 视频：https://www.bilibili.com/video/BV1we4y157wB?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 本题和动态规划：115.不同的子序列 相比，其实就是两个字符串都可以删除了，情况虽说复杂一些，但整体思路是不变的。
1. dp数组的定义
* 比较两个数组的元素，需要二维dp
* dp[i][j] = 用s[0,i-1]和t[0,j-1]达到相等所需要的最小删除次数
2. 递推公式
* 如果两个元素相同，dp[i][j]考虑不考虑都是一样的，都等于dp[i-1][j-1]，就不需要删除
* 如果两个元素不相同，那就需要删除元素了，分3种情况，只删除word1的元素，只删除word2的元素，和同时删除word1，word2中的元素.三种方法取最小的
i. 只删除word1的元素=dp[i-1][j]+1
ii. 只删除word2的元素:dp[i][j-1] +1
iii. 和同时删除word1，word2中的元素:dp[i-1][j-1] + 2
* 其实第三种情况和前面两个重复计算了，因为dp[i-1][j-1] + 1 = dp[i][j-1]
3. 初始化
* dp[0][j] 第一行： word1是空字符串，一共删除j次就行
* dp[i][0]第一列：word2是空字符串，删除word1中的i个字符串就行（i-1的下标意味着有i个元素在word里）
* 相交点[0][0] = 0
4. 遍历顺序：从左到右，从上到下
### 代码随想录解法
```java
class Solution {
    public int minDistance(String word1, String word2) {
        int[][] dp = new int[word1.length()+1][word2.length()+1];
        for (int i=0; i<=word1.length();i++){
            dp[i][0] = i; 
        }
        for (int j=1; j<=word2.length();j++){
            dp[0][j] = j;
        }
        for (int i=1; i<=word1.length();i++){
            for (int j=1; j<=word2.length();j++){
                if (word1.charAt(i-1)==word2.charAt(j-1)){
                    //如果都一样，就不用删除,不需要增加删除数量
                    dp[i][j]=dp[i-1][j-1];
                }else{
                    //如果不一样，那就得在word1和word2中选一个，删除最后一个元素(增加一次删除操作），哪个删除次数少选哪个 
                    dp[i][j]=Math.min(dp[i-1][j]+1,dp[i][j-1]+1);
                }
            }
        }
        return dp[word1.length()][word2.length()];
    }
}
```
### b站评论区：求最大公共子序列长度，然后word1，word2长度分别减去最大公共子序列长度
* 我个人觉得更容易理解一些
```java
class Solution {
    public int minDistance(String word1, String word2) {
        //求最大公共子序列长度解法
        //长度为[0, i - 1]的字符串text1与长度为[0, j - 1]的字符串text2的最长公共子序列为dp[i][j]
        int[][] dp = new int[word1.length()+1][word2.length()+1];
        for (int i=1; i<=word1.length();i++){
            for (int j=1; j<=word2.length();j++){
                if (word1.charAt(i-1)==word2.charAt(j-1)){
                    //如果最后一个元素相同，找到了一个公共元素
                    dp[i][j]=dp[i-1][j-1] +1;
                }else{
                    //如果不一样，那就看看text1[0, i - 2]与text2[0, j - 1]的最长公共子序列 和 text1[0, i - 1]与text2[0, j - 2]的最长公共子序列，取最大的。    
                    dp[i][j]=Math.max(dp[i-1][j],dp[i][j-1]);
                }
            }
        }
        int maxlength = dp[word1.length()][word2.length()];
        return word1.length() + word2.length() - 2*maxlength;
    }
}
```
## 编辑距离72. Edit Distance
* https://leetcode.com/problems/edit-distance/
* 文章： https://programmercarl.com/0072.%E7%BC%96%E8%BE%91%E8%B7%9D%E7%A6%BB.html
* 视频：https://www.bilibili.com/video/BV1qv4y1q78f?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 比起上一题又进阶了，可以添加，可以替换，可以删除
* word1和word2都可以操作
1. 定义dp数组
* dp[i][j]=word1【0,i-1]到word2[0,j-1]进行最少的操作次数
2. 递推公式
* 比较word1[i-1], word2[j-1]
* 假如相同，那就不用操作，不用添加/删除/替换了！就和dp[i][j] = dp[i-1][j-1]，就延续前一个元素结尾的最少操作次数
* 假如不相同，因为我们添加和删除都可以在word1和word2上进行，添加和删除的操作数量是一样的。我们都看成删除就可以。
* 删除word1[i-1]的情况=dp[i-1][j] + 1
* 删除word2[j-1]的情况=dp[i][j-1]+1
* 替换：dp[i-1][j-1]（上一个元素相同的状态）+1
* 这三种情况取最小值
3. 三种情况初始化
* dp[i][j]从左边 (dp[i][j-1])，上边(dp[i-1][j]，左上方(dp[i-1][j-1]得出
* 所以第一行和第一列一定要初始化
* 第一列dp[i][0]：最少操作i次才能出现空串（word1里此时有i个元素）
* 第一行dp[0][j]:最小操作j次才能出现空串（word2里此时有j个元素）
4. 遍历顺序：和以前一样，从左到右从上到下
* 代码是怎么知道word1和word2越来越接近的呢？
* ![Screenshot 2025-04-16 at 22 40 47](https://github.com/user-attachments/assets/8ad3b0e1-e7b3-4871-81eb-9a1a9d571381)
```java
class Solution {
    public int minDistance(String word1, String word2) {
        int l1 = word1.length();
        int l2 = word2.length();
        int[][] dp = new int[l1+1][l2+1];

        for (int i=0; i<= l1; i++){
            dp[i][0] = i;
        }
        for(int j=1; j<=l2; j++){
            dp[0][j] = j;
        }

        for (int i=1; i<=l1;i++){
            for (int j=1; j<=l2;j++){
                if (word1.charAt(i-1)==word2.charAt(j-1)){
                    //如果最后一位相同，不需要操作
                    dp[i][j] = dp[i-1][j-1];
                }else{
                    //在删word1，删word2和替换之间选操作数量最少的
                    dp[i][j] =Math.min(Math.min(dp[i-1][j]+1, dp[i][j-1]+1),dp[i-1][j-1]+1);
                }
            }
        }
        return dp[l1][l2];
    }
}
```
## 编辑距离总结篇
https://programmercarl.com/%E4%B8%BA%E4%BA%86%E7%BB%9D%E6%9D%80%E7%BC%96%E8%BE%91%E8%B7%9D%E7%A6%BB%EF%BC%8C%E5%8D%A1%E5%B0%94%E5%81%9A%E4%BA%86%E4%B8%89%E6%AD%A5%E9%93%BA%E5%9E%AB.html#%E5%88%A4%E6%96%AD%E5%AD%90%E5%BA%8F%E5%88%97
