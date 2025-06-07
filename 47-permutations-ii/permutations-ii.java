class Solution {

    List<List<Integer>> res = new LinkedList<>();
    List<Integer> path = new LinkedList<>();

    public List<List<Integer>> permuteUnique(int[] nums) {
        //sorting nums to skip similar starting elements
        Arrays.sort(nums);        
        boolean[] usedIndex = new boolean[nums.length];
        
        backtrack(nums,usedIndex);
        return res;
    }

    private void backtrack(int[] nums, boolean[] usedIndex){
        if (path.size() == nums.length){
            res.add(new LinkedList<>(path));
            return;
        }
        for (int i = 0; i<nums.length;i++){
            if (usedIndex[i] || i > 0 && nums[i] == nums[i-1] && !usedIndex[i-1]) continue;
            usedIndex[i] = true;
            path.add(nums[i]);
            backtrack(nums,usedIndex);
            usedIndex[i] = false;
            path.remove(path.size()-1);
        }
    }
}