class Solution {
    Map<String, Boolean> memo = new HashMap<>();
    public boolean canPartition(int[] nums) {
        int total = 0;
        for(int num: nums){
            total += num;
        }
        if (total % 2 == 1) return false;
        int target = total/2;
        return backtrack(nums, target, 0, 0);
    }

    private boolean backtrack(int[] nums, int target, int index, int currentSum){
        if (currentSum == target) return true;
        if (currentSum > target || index >= nums.length) return false;

        String key = index + "," + currentSum;
        if (memo.containsKey(key)) return memo.get(key);
        //选与不选只要有一个对，就return 对
        boolean res = (backtrack(nums,target,index+1,currentSum + nums[index]) || backtrack(nums,target, index+1, currentSum));
        memo.put(key, res);
        
        return res;
    }
}