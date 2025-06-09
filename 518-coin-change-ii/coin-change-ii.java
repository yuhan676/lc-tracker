class Solution {
    public int change(int amount, int[] coins) {
        int[] dp = new int[amount +1];
        for (int j = 0; j<=amount;j++){
            if (j%coins[0] == 0) dp[j] = 1;
        }
        for (int i = 1; i< coins.length;i++){
            for (int j = coins[i]; j<= amount;j++){
                dp[j] = dp[j] + dp[j-coins[i]];
            }
        }
        return dp[amount];
    }
}