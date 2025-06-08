class Solution {
    int res = 0;
    public int findTargetSumWays(int[] nums, int target) {
        backtrack(nums, target, 0, 0);
        return res;
    }
    private void backtrack(int[] nums, int target, int index, int currSum){
        if (index == nums.length){
            if (currSum ==target){
                res ++;
            }
            return;
        }
        // +
        backtrack(nums, target, index +1, currSum + nums[index]);
        // -
        backtrack(nums,target, index+1, currSum - nums[index]);
    }
}