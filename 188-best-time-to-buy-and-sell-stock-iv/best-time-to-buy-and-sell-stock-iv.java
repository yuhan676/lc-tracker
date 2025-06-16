class Solution {
    Map<String, Integer> memo = new HashMap<>();
    public int maxProfit(int k, int[] prices) {
        return dfs(prices, 0, 0, k);
    }
    private int dfs(int[] prices, int holding, int index, int remTransac){
        if (index >= prices.length || remTransac == 0){
            return 0;
        }
        String key = index + "," + holding + "," + remTransac;
        if (memo.containsKey(key)) return memo.get(key);
        //holding choice: sell or do nothing
        //not holding choise: buy or do nothing
        int doNothing = dfs(prices, holding, index +1, remTransac);
        int doSomething;
        if (holding == 1){
            //do selling
            doSomething = dfs(prices, 0, index +1, remTransac - 1) + prices[index];
        }else{
            //do buying
            doSomething = dfs(prices, 1, index +1, remTransac) - prices[index];
        }
        int val = Math.max(doNothing, doSomething);
        memo.put(key, val);
        return val;
    }
}