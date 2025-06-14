class Solution {
    public int maxProfit(int[] prices) {
        //dp[i][0] = money held if holding on the ith day
        //dp[i][1] = money held if not holding stalk on the ith day
        int[][] dp= new int[prices.length][2];
        dp[0][0] = -prices[0];
        dp[0][1] = 0;
        for (int i = 1; i<prices.length;i++){
            //持有，要么是保持之前买过的状态，要么是第一次买进
            //因为只能买一次，所以不是Math.max(dp[i-1][0], dp[i-1][0]-prices[i]);
            dp[i][0] = Math.max(dp[i-1][0], -prices[i]);
            dp[i][1] = Math.max(dp[i-1][1],dp[i-1][0] + prices[i]);
        }
        return dp[prices.length-1][1];
    }
}