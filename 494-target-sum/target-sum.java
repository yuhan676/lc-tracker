class Solution {
    //key = index + currSum, value = ways to reach target
    Map<String, Integer> memo = new HashMap<>();

    public int findTargetSumWays(int[] nums, int target) {
        return backtrack(nums, target, 0, 0);
    }
    //意思是，有了这些param，能找到多少条path
    private int backtrack(int[] nums, int target, int index, int currSum){
        String key = index + "," + currSum;
        if (memo.containsKey(key)) return memo.get(key);
        //走到最后一步了，发现一条合法的路，那就返回1，因为这个位置的index和currentsum能找到一条路
        if (index == nums.length){
            if (currSum ==target){
                return 1;
            }
            //没找到就return0条路
            return 0;
        }

        int validPathPlus = backtrack(nums, target, index +1, currSum + nums[index]);
        int validPathMinus = backtrack(nums,target, index+1, currSum - nums[index]);

        int totalPath = validPathPlus + validPathMinus;
        memo.put(key, totalPath);
        return totalPath;
    }
}