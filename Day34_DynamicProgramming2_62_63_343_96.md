## 62. Unique Paths
* https://leetcode.com/problems/unique-paths/
* 文章：https://programmercarl.com/0062.%E4%B8%8D%E5%90%8C%E8%B7%AF%E5%BE%84.html#%E6%80%9D%E8%B7%AF
* 视频：https://www.bilibili.com/video/BV1ve4y1x7Eu?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 一刷我只学动态规划的题解，代码随想录还提供了图论深度搜索（会超时）的题解和数论的题解
* 动态规划五部曲
1. 确定dp数组以及下标的含义：dp[i][j] 表示从(0,0)出发，到(i,j)有dp[i][j]中不同的路径
2. 确定递推公式：到达dp[i][j]，只有两种方式：从它的上方和左边来，即dp[i-1][j], dp[i][j-1],那么到达dp的所有路径的和就是:dp[i][j] = dp[i - 1][j] + dp[i][j - 1],到达这两个位置的路径没有可能重合
3. dp数组的初始化：dp[i][0]肯定是1，因为纵列中只有一种方式到达（i,0),dp[0,j]同理
```
for (int i = 0; i < m; i++) dp[i][0] = 1;
for (int j = 0; j < n; j++) dp[0][j] = 1;
```
4. 确定遍历顺序：递推公式告诉我们dp[i][j] = dp[i - 1][j] + dp[i][j - 1]，dp[i][j]都是从其上方和左方推导而来，那么从左到右一层一层遍历就可以了。
5. 举例推导出dp数组 （画好图会更好理解）
![image](https://github.com/user-attachments/assets/d4fbfb33-4524-45d7-8a2d-edb03a174e83)
### 二维dp数组解法
* 时间复杂度：O(m × n)
* 空间复杂度：O(m × n)
```java
class Solution {
    public int uniquePaths(int m, int n) {
        //初始化数组
        int[][] dp = new int[m][n];
        //初始化第一行和第一列，到这些格子都只有一种走法
        for (int i = 0; i<m;i++){
            dp[i][0] = 1;
        }
        for (int j=0; j<n;j++){
            dp[0][j] = 1;
        }
        //从第二行第二列开始算到达每一个格子有多少步
        for (int i = 1; i<m;i++){
            for (int j = 1; j<n;j++){
                //当前各自的步数=到达左边格子的步数+到达上面格子的步数
                dp[i][j] = dp[i-1][j]+dp[i][j-1];
            }
        }
        return dp[m-1][n-1];
    }
}
```
## 一维dp数组解法
* 简化成一维数组（长度为n），相当于每次只记录一行
* 下一行同位置的元素的值=当前值+左边的元素的值
* 时间复杂度：O(m × n)
* 空间复杂度：O(m × n)

```java
class Solution {
    public int uniquePaths(int m, int n) {
        int[] dp = new int[n];
        for (int i = 0; i<n;i++){
            dp[i] = 1;
        }
        //一共要循环更新这一行m次（m是列数）
        for (int j =1; j<m; j++){
            for(int i = 1; i<n; i++){
                dp[i] = dp[i] + dp[i-1];
            }
        }
        return dp[n-1];
    }
}
```
## 63. Unique Paths II
* https://leetcode.com/problems/unique-paths-ii/description/
* 文章：https://programmercarl.com/0063.%E4%B8%8D%E5%90%8C%E8%B7%AF%E5%BE%84II.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 视频：https://www.bilibili.com/video/BV1Ld4y1k7c6?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 思路和上面一题很像，不过在初始化的时候记得第一行和第一列，遍历的时候如果碰到障碍物，那它的初始化数字就是0，后面遍历的数值也全部都是0
* 遍历第一行的时候直接检查obstacleGrid[0][i]是不是1，是的话直接break
* 遍历第一列的时候要检查上面的一个元素是不是0，如果是0，它自己也就是0
* 另外如果第一个元素就是obstacle，那直接返回0
### 二维数组解法
* 时间复杂度：O(n × m)，n、m 分别为obstacleGrid 长度和宽度
* 空间复杂度：O(n x m)
#
```java
class Solution {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        //得到m和n的大小
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        //初始化dp数组，dp[m][n]的意思是到达obstacleGrid[m][n]一共有几种走法，如果是障碍物的话，dp[m][n]为0
        int[][] dp = new int[m][n];
        //如果第一个grid就是障碍物，直接返回0
        if (obstacleGrid[0][0]==1) return 0;
        //填充第一行
        for (int i = 0; i<n;i++){
            //一旦遇到障碍物，之后的都是0
            if (obstacleGrid[0][i] == 1) break;
            else dp[0][i] = 1;
        }
        //填充第一列
        for (int j = 1; j<m;j++){
            //如果当前值是障碍物，直接break，之后的格子都填充0
            if (obstacleGrid[j][0]==1) break;
            //如果当前不是障碍物，但是上方格子是障碍物，那么当前格子也是0
            dp[j][0] = dp[j-1][0]==1? 1:0;
        }

        //从第二行第二列开始填充值
        for (int i = 1; i<m;i++){
        for (int j = 1; j<n;j++){
            //如果遇到障碍物，那那个位置的值就是0
            if (obstacleGrid[i][j] == 1) {dp[i][j]=0;}
            //如果不是障碍物，那那个位置的值就是左方+上方
            else{dp[i][j] = dp[i-1][j] +dp[i][j-1];}
        }
        }
        //这里学习一下打印数组
        for (int i=0;i<m;i++){
            for (int j=0;j<n;j++){
                //打印一个数字，加一个空格
                System.out.print(dp[i][j] + " ");
            }
            //每一行过去以后要换行
            System.out.println();
        }
        return dp[m-1][n-1];
    }
}
```
### 压缩成一维数组的解法
* 第一行和第一列的继承方法有点难想
* 时间复杂度：O(n × m)，n、m 分别为obstacleGrid 长度和宽度
* 空间复杂度：O(m)
#
```java
class Solution {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        //得到m和n的大小
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        //初始化dp数组，dp[m][n]的意思是到达obstacleGrid[m][n]一共有几种走法，如果是障碍物的话，dp[m][n]为0
        int[] dp = new int[n];
        //如果第一个grid就是障碍物，直接返回0
        if (obstacleGrid[0][0]==1) return 0;
        //确定了第一个不是障碍物，就可以填充1了
        dp[0] = 1;
        for (int i = 1; i<n;i++){
            //第一列后面的从前面继承
            if (obstacleGrid[0][i] == 1) dp[i]=0;
            else dp[i] =dp[i-1];
        }
        
        for (int i = 1; i<m;i++){
            //下一列第一个如果是障碍物，直接set0,这个更新一次就好，后面的列都会继承这个0
            if (obstacleGrid[i][0] == 1) dp[0]=0;
            for (int j=1; j<n;j++){
                if (obstacleGrid[i][j] == 1) dp[j] = 0;
                else{dp[j]+= dp[j-1];}
            }
        }96. Unique Binary Search Trees
        
        return dp[n-1];
    }
}
```
## 343. Integer Break
* https://leetcode.com/problems/integer-break/description/
* 文章：https://programmercarl.com/0343.%E6%95%B4%E6%95%B0%E6%8B%86%E5%88%86.html#%E6%80%9D%E8%B7%AF
* 视频：https://www.bilibili.com/video/BV1Mg411q7YJ?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 我读了文章看了视频，能写出来代码了，但是自己想出这个逻辑会很难
```java
class Solution {
    public int integerBreak(int n) {
        //从0开始的数组，到n就有n+1个元素
        int[]dp = new int[n+1];
        //dp[0],dp[1]都是没有意义的故不用初始化
        dp[2] = 1;
        for (int i =3; i<=n;i++){
            for (int j = 1; j<=i/2 ;j++){
                //每次过一个j，dp[i]都有可能更新，所以要比较现在的值和新的值
                //j*(i-j)是2数相乘，dp[i-j]*j是3数相乘，3最接近3的值，就能最大化results
                dp[i] = Math.max(dp[i],Math.max(j*(i-j), j*dp[i-j]));
            }
        }
        for (int k=0;k<=n;k++){
            System.out.println(dp[k]);
        }
        return dp[n];
    }
}
```
## 96. Unique Binary Search Trees
* https://leetcode.com/problems/unique-binary-search-trees/description/
* 文章：https://programmercarl.com/0096.%E4%B8%8D%E5%90%8C%E7%9A%84%E4%BA%8C%E5%8F%89%E6%90%9C%E7%B4%A2%E6%A0%91.html#%E6%80%9D%E8%B7%AF
* 视频：https://www.bilibili.com/video/BV1eK411o7QA?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 看了视频但是没有完全理解，二刷重新尝试
