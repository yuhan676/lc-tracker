class Solution {
    public int maxProfit(int[] prices) {
        // 4 status: holding, continue to not hold, nothold because selling today, cooling day 
        int[][] dp = new int[prices.length][4];
        dp[0][0]= -prices[0];
        for (int i = 1; i<prices.length;i++){
            //这里我没想错，持有要么是保持持有，要么是今天买入（前一天要么是continue to not hold, 要么是cooling day）
            dp[i][0] = Math.max(dp[i-1][0], Math.max(dp[i-1][1] - prices[i], dp[i-1][3] - prices[i]));
            //今天是保持不持有，前一天要么是cooling day要么是continue to not hold
            dp[i][1] = Math.max(dp[i-1][3], dp[i-1][1]);
            //今天卖出, 昨天只有可能持有
            dp[i][2] = dp[i-1][0] + prices[i];
            //今天cooling，昨天只有可能卖出
            dp[i][3] = dp[i-1][2];
        }
        //最后一天不持有状态中选最大
        return Math.max(dp[prices.length-1][3],Math.max(dp[prices.length-1][1], dp[prices.length-1][2]));
    }
}