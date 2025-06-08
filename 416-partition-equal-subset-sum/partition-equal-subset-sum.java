class Solution {
    public boolean canPartition(int[] nums) {
        // == target value = Sum(nums)/2
        // when weight limit = value, can the value in the bag == value?
        int tot = 0;
        for (int num:nums){
            tot += num;
        }
        if (tot%2 != 0) return false;
        int target = tot/2;
        //item number = nums.length
        //weight limit = target
        int[] dp = new int[target+1];

        for (int j = 0; j<= target;j++){
            if (j>= nums[0]){
                dp[j] = nums[0];
            }
        }
        //第一行刚才已经初始化过了
        for (int i = 1; i<nums.length;i++){
            for (int j = target; j>=0;j--){
                //装不下, 继承之前的状态
                if (j >= nums[i]){
                    //装得下，才改变
                    dp[j] = Math.max(dp[j], dp[j-nums[i]] + nums[i]);
                }
                if (dp[j]==target) return true;
            }
        }
        return false;
    }
}