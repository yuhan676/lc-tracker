class Solution {
    Map<String, Integer> memo= new HashMap<>();

    public int change(int amount, int[] coins) {
        return (getMaxCombi(amount, coins, 0, 0));
    }

    public int getMaxCombi(int amount, int[] coins, int index, int currSum){
        //这里currsum剪枝是对的吗？coins[i] >=1
        if (index >= coins.length || currSum > amount) return 0;
        if (currSum == amount) return 1;
        String key = index + "," + currSum;
        if (memo.containsKey(key)) return memo.get(key);
        int skip = getMaxCombi(amount, coins, index+1, currSum);
        int take = 0;
        if (amount - currSum >= coins[index]){
            take = getMaxCombi(amount, coins, index, currSum + coins[index]);
        }
        int maxCombi = skip + take;
        memo.put(key, maxCombi);
        return maxCombi;
    }
}