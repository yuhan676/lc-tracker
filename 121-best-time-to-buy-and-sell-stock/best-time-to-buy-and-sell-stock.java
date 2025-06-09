class Solution {
    public int maxProfit(int[] prices) {
        int maxPrice = 0;
        int lowest = Integer.MAX_VALUE;
        for (int price: prices){
            lowest = Math.min(lowest, price);
            maxPrice = Math.max(maxPrice, price - lowest);
        }
        return maxPrice;
    }
}