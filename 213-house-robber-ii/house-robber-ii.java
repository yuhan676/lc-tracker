class Solution {
    public int rob(int[] nums) {
        if (nums.length==1) return nums[0];
        //not including the last
        int[][] dp1 = new int[nums.length-1][2];
        dp1[0][0] = 0;
        dp1[0][1] = nums[0];
        for (int i = 1; i<= nums.length -2;i++){
            dp1[i][0] = Math.max(dp1[i-1][0],dp1[i-1][1]);
            dp1[i][1] = dp1[i-1][0] + nums[i];
        }
        int res1 = Math.max(dp1[nums.length-2][0], dp1[nums.length-2][1]);
        //not including the first 
        int[][] dp2 = new int[nums.length-1][2];
        dp2[0][0] = 0;
        dp2[0][1] = nums[1];
        int index = 1;
        //shifting 
        for (int i =2; i<=nums.length-1;i++){
            dp2[i-1][0] = Math.max(dp2[i-2][0], dp2[i-2][1]);
            dp2[i-1][1] = dp2[i-2][0] + nums[i];
        }
        int res2 = Math.max(dp2[nums.length-2][0], dp2[nums.length-2][1]);
        return Math.max(res1,res2);
    }
}