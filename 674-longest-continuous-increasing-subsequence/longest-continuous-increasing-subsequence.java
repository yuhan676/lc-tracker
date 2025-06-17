class Solution {
    public int findLengthOfLCIS(int[] nums) {
        int res = 1;
        int currLength = 1;
        for (int i = 1; i<nums.length;i++){
            if(nums[i] > nums[i-1]) currLength ++;
            else currLength = 1;
            if (currLength > res) res = currLength;
        }
        return res;
    }
}