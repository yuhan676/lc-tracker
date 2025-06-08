class Solution {
    public int lastStoneWeightII(int[] stones) {
        //假设我们有4个石头：1，3，2，2，可以分成相等的两堆
        //1<->2 ->1
        //3<->2 ->1
        //1<-->1 = 0
        //也就是说能分成相等的两堆就一定能碰撞完
        int totalWeight = 0;
        for (int stone: stones){
            totalWeight += stone;
        }
        //基数总数，最后肯定剩一个...当然不是！这里容易出错，并不是说是基数就一定会剩下1，假如是3，6呢？剩下3
        // 一开始写了if (totalWeight%2!=0) return 1;
        //target weight
        int target = totalWeight/2;
        int[] dp = new int[target +1];
        //初始化第一行
        for (int j = 0; j<= target;j++){
            if (j>= stones[0]){
                dp[j] = stones[0];
            }
        }
        //第一列肯定是0所以无所谓初始化
        for (int i = 1; i<stones.length;i++){
            for (int j =target; j>= 0; j--){
                if (j>= stones[i]){
                    dp[j] = Math.max(dp[j], dp[j-stones[i]] + stones[i]);
                }
            }
        }
        int lastmaxFill = dp[target];
        int resOfStone = totalWeight - lastmaxFill;
        return resOfStone - lastmaxFill;
    }
}