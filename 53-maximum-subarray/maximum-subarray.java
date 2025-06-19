class Solution {
    public int maxSubArray(int[] nums) {
        if (nums.length==1) return nums[0];
        int res = nums[0];
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        for (int i = 1; i<nums.length;i++){
            //卡丹算法，假如前面的和变负了，那不如重新从当前元素开始叠加
            dp[i]= Math.max(nums[i], dp[i-1] + nums[i]);
            //每走一步都更新最大和
            if (dp[i]>res) res = dp[i];
        }
        return res;
    }
}