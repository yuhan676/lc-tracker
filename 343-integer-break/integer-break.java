class Solution {
    public int integerBreak(int n) {
        int[] dp = new int[n+1];
        //dp[0] && dp[1] are meaningless, cuz we have to split it to more than 1 positive integer
        dp[2] = 1;
        for (int i = 3; i<= n; i++){
            for (int j = 1; j<=i/2;j++){
                dp[i] = Math.max(dp[i], Math.max(j * (i-j), j* dp[i-j]));
            }
        }
        return dp[n];
    }
}