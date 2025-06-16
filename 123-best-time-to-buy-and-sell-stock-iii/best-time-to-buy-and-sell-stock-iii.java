class Solution {
    Map<String, Integer> memo = new HashMap<>();

    public int maxProfit(int[] prices) {
        return dfs(prices,0,0,2);
    }
    //transaction = buy, sell
    //remainingT only decrement when selling
    private int dfs(int[] prices, int index, int hold, int remainingT){
        if (index >= prices.length || remainingT == 0) return 0;
        String key = index + "," + hold + "," + remainingT;
        if (memo.containsKey(key)) return memo.get(key);
        int doNothing = dfs(prices, index +1, hold, remainingT);
        int doSomething;
        //持有，考虑卖出
        if (hold == 1){
            //卖出得到的钱 + 剩下天数可以做买卖的利润最大值
            doSomething = dfs(prices, index +1, 0, remainingT -1) + prices[index];
        }else{
        //不持有，考虑买入
            //买入花去的钱 + 剩下的天数可以做买卖的利润最大值
            doSomething = dfs(prices, index +1, 1, remainingT) - prices[index];
        }
        int val = Math.max(doNothing, doSomething);
        memo.put(key, val);
        return val;
    }
}