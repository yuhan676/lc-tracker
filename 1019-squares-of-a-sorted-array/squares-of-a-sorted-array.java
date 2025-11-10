class Solution {
    public int[] sortedSquares(int[] nums) {
        int[] res = new int[nums.length];
        int l = 0;
        int r = nums.length -1;
        int resIndex = r;
        while (l<=r){
            if (nums[l] * nums[l] < nums[r] * nums[r]){
                res[resIndex] = nums[r] * nums[r];
                resIndex --;
                r--;
            }else{
                res[resIndex] = nums[l] * nums[l];
                resIndex --;
                l++;
            }
        }
        return res;
    }
}