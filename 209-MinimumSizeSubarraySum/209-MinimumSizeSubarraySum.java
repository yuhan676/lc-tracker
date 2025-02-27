class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        int sum = 0, res = Integer.MAX_VALUE, i = 0;
        for (int j = 0; j < nums.length; j++){
            sum += nums[j];
            while (sum >= target){
                res = Math.min(res, j - i + 1);
                sum -= nums[i];
                i++;
            }
        }
        return (res == Integer.MAX_VALUE) ? 0:res; 
    }
}