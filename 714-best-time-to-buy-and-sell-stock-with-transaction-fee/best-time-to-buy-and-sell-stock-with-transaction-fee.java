class Solution {
    public int maxProfit(int[] prices, int fee) {
        int[][] dp = new int[prices.length][2];
        // 0 = hold, 1 = not hold;

        //第一天买入
        int hold = -prices[0];
        //第一天卖出就亏钱了所以not hold选择不买入
        int notHold = 0;
        for (int i = 1; i<prices.length;i++){
            int prevHold = hold;
            int prevNotHold = notHold;

            hold = Math.max(prevHold, prevNotHold - prices[i]);
            notHold = Math.max(prevNotHold, prevHold + prices[i] - fee);
        }
        return Math.max(hold,notHold);
    }
}