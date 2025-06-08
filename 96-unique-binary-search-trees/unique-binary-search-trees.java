class Solution {
    public int numTrees(int n) {
        int[] dp = new int[n+1];
        //空bst和单个节点bst都只有一种排布方式
        dp[0] = dp[1] = 1;
        for (int i = 2; i<=n; i++){
            //注意从一开始可以作为头节点，到最后n可以作为头节点，全部的可能性加起来，所以是(=1, <=n)
            for (int j = 1; j<= i; j++){
                //i = 2, j = 2,j<=2
                //dp[2] += dp[0] * dp[2-1] = 1*1
                //dp[2] += dp[1] * dp[2-2] = 1+1 = 2
                dp[i] += dp[j-1] * dp[i-j];
            }
        }
        return dp[n];
    }
}