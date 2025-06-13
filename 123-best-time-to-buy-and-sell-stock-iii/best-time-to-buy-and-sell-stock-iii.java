class Solution {
    public int maxProfit(int[] prices) {
        int[][] dp = new int[prices.length][5];
        // money of no action
        dp[0][0] = 0;
        // money of first time holding
        dp[0][1] = -prices[0];
        // money of first time not holding
        dp[0][2] = 0;
        //money of second time holding
        dp[0][3] = -prices[0];
        //money of second time not holding
        dp[0][4] = 0;
        for (int i = 1; i<prices.length;i++){
            //money of holding first time
            dp[i][1] = Math.max(dp[i-1][0] - prices[i], dp[i-1][1]);
            //money of not holding first time
            dp[i][2] = Math.max(dp[i-1][1] + prices[i], dp[i-1][2]);
            //money of second time holding
            dp[i][3] = Math.max(dp[i-1][3],dp[i-1][2] - prices[i]);
            //money of second time not holding
            dp[i][4] = Math.max(dp[i-1][3] + prices[i], dp[i-1][4]);
        }
        return dp[prices.length-1][4];
    }
}