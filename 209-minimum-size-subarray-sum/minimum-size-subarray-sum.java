class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        int minL = Integer.MAX_VALUE;
        int i = 0;
        int sum = 0;
        for (int j = 0; j< nums.length;j++){
            sum += nums[j];
            int len = j-i + 1;
            while (sum >= target){
                minL = Math.min(minL, j - i + 1);
                sum -= nums[i];
                i++;//这里记得不能先increment i，不然容易把已经increment的i index的内容剪掉
            }
        }
        return minL == Integer.MAX_VALUE ? 0: minL;
    }
}