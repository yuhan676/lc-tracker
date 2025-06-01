class Solution {
    public int maxFrequency(int[] nums, int k) {
        //res 最小也是1
        int res = 1;
        int n = nums.length;
        //记得sort
        Arrays.sort(nums);
        //sum + k >= max * windowSize
        //不合法的话就得左边减去一个元素
        int left = 0, right;
        //初始化成sum，防止溢出 （2^31是10^10级别的，但是k <= 10 ^5, nums[i]<= 5,勉强够的上）
        long sum = 0;
        for (right = 0; right <n;right ++){
            sum += nums[right];
            //这里记得也转化成long一下，不然可能溢出
            while (sum + k < (long) nums[right] * (right - left + 1)){
                sum -= nums[left];
                left ++;
            }
            res = Math.max(res, right - left + 1);
        }
        return res;

    }
}