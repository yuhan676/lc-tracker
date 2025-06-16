class Solution {
    Map<String, Integer> memo = new HashMap<>();
    public int maxProfit(int[] prices) {
        return dfs(prices, 0, 0);
    }
    private int dfs(int[] prices, int index, int holding){
        if (index >= prices.length) return 0;
        String key = index + "," + holding;
        if (memo.containsKey(key)) return memo.get(key);
        int res;
        if (holding == 1){
            int hold = dfs(prices, index +1, 1);
            //卖的话，index+1就不能有动作，直接从index+2不持有那天开始才能有动作买入
            int sell = prices[index] + dfs(prices, index+2,0);
            res = Math.max(hold,sell);
        }else{
            //不持有不买
            int skip = dfs(prices, index +1, 0);
            //不持有，买入
            int buy = dfs(prices, index +1, 1) - prices[index];
            res = Math.max(skip,buy);
        }
        memo.put(key,res);
        return res;
    }
}