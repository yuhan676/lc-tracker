class Solution {
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        //最上和最左的格子都只有一种方法到达
        for (int i = 0; i<m;i++){
            dp[i][0] = 1;
        }
        for (int i=1; i<n;i++){
            dp[0][i] = 1;
        }
        //其他格子到达的方法 dp[i][j] = dp[i-1][j] + dp[i][j-1]
        for (int i =1; i<m;i++){
            for (int j=1; j<n;j++){
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }
        return dp[m-1][n-1];
    }
}