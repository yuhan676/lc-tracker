class Solution {
    public int lengthOfLIS(int[] nums) {
        int res = 1;
        //dp[i]的意思：以i结尾的子序列的最大长度
        int[] dp = new int[nums.length];
        //每个元素作为结尾的最长子序列的初始值：只包括它自己，==1
        Arrays.fill(dp, 1);
        for (int i = 1; i<nums.length;i++){
            for (int j = 0; j<i;j++){
                //每个元素要和从0到它本身的元素对比
                if (nums[i]>nums[j]){
                    dp[i] = Math.max(dp[i], dp[j]+1);
                }
                res = dp[i] > res ? dp[i]:res;
            }
        }
        return res;
    }
}