class Solution {
    public int maxProfit(int[] prices) {
        // 4 status: holding, continue to not hold, nothold because selling today, cooling day 
        int hold = -prices[0];
        int notHold = 0;
        int sell = 0;
        int cool = 0;
        for (int i = 1; i<prices.length;i++){
            int prehold = hold;
            int prenotHold = notHold;
            int presell = sell;
            int precool = cool;

            hold = Math.max(prehold, Math.max(prenotHold - prices[i], precool - prices[i]));
            notHold = Math.max(prenotHold, precool);
            sell = prehold + prices[i];
            cool = presell;
        }
        return Math.max(notHold, Math.max(sell,cool));
    }
}