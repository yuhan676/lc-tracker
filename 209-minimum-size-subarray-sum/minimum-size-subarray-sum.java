class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        int sum = 0,res = Integer.MAX_VALUE,i = 0;
        //[2,3,1,2,4,3] targert = 7;
        //j=1,i=0;
        //sum=5;
        //res = 2;
        for(int j =0; j< nums.length;j++){
            sum += nums[j];
            while (sum >= target){ 
                res = Math.min(res,j-i+1);
                sum -= nums[i];
                i++;
            }
        }
        return (res==Integer.MAX_VALUE)?0:res;
    }
}