class Solution {
    public int climbStairs(int n) {
        return climbStairsM(n,2);
    }
    private int climbStairsM(int n, int m){
        int[] dp = new int[n+1];
        dp[0] = 1;
        for (int i =1; i<=n;i++){
            for (int j = 1; j<= m && i>= j;j++){
                //最大情况下 i == j, i-j = 0,走到最后一步了
                    dp[i] += dp[i-j];
            }
        }
        return dp[n];
    }
}