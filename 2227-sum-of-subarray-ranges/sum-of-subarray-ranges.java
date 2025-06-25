class Solution {
    public long subArrayRanges(int[] nums) {
        int n = nums.length;
        long res = 0;
        for (int i = 0; i< n;i++){
            int max = nums[i];
            int min = nums[i];
            for (int j =i; j<n;j++){
                if (i==j) continue;
                if (nums[j] > max) max = nums[j];
                if (nums[j] < min) min = nums[j];
                res += max-min;
            }
        }
        return res;
    }

}