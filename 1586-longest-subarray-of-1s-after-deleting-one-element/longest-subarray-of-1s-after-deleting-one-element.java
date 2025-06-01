class Solution {
    public int longestSubarray(int[] nums) {
        //不一定有几个1呢，所以初始化成0
        int res = 0;
        int n = nums.length;
        int left = 0, right = 0;
        int zeroCount = 0;
        for (; right < n; right ++){
            if (nums[right] == 0){
                zeroCount ++;
            }
            while (zeroCount >1){
                if (nums[left]==0){
                    zeroCount --;
                }
                left ++;
            }
            res = Math.max(res, right - left + 1 - zeroCount);
        }
        return (res==n) ? res - 1:res;

    }
}