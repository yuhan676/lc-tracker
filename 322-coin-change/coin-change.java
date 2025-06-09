class Solution {
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount +1];
        //先不要fill-1，fill一个永远都达不到的数字，比如amount +1 或者Integer.MAX_VALUE
        //构成amount面值的钱币数量永远都达不到amount+1是因为有amount+1个数值是1的硬币，最小也只能构造出>amount的数量
        Arrays.fill(dp, amount+1);
        dp[0] = 0;
        //我一开始的想法： 如果j 能整除nums[0]就有j/nums[0] 种填满的方法
        //但你根本不需要这样做，因为：
        //完全背包是构造“所有可能”，不是“只用前一个物品”，这个和01背包有区别，而且内循环每次都会检查nums[i], i == 0的情况
        //所以basecase只有j = 0的时候， 填满的最小的硬币数=0
        for (int j =1; j<= amount; j++){
            for (int i = 0; i < coins.length;i++){
                if (j>= coins[i]){
                    dp[j] = Math.min(dp[j], dp[j-coins[i]] +1);
                }
            }
        }
        return dp[amount] == amount +1 ? -1: dp[amount];
    }
}