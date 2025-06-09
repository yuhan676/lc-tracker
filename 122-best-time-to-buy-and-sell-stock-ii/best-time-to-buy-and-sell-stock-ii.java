class Solution {
    public int maxProfit(int[] prices) {
        int maxpro = 0;
        for (int i =1; i<prices.length;i++){
            int prof = prices[i] - prices[i-1];
            if (prof > 0){
                maxpro += prof;
            }
        }
        return maxpro;
    }
}