class Solution {
    List<List<Integer>> res = new LinkedList<>();
    List<Integer> path = new LinkedList<>();

    public List<List<Integer>> findSubsequences(int[] nums) {
        backtrack(nums,0);
        return res;
    }

    private void backtrack(int[] nums, int start){
        if (isValid(path)){
            res.add(new LinkedList<>(path));
        }
        //当层的map
        Map<Integer, Boolean> used = new HashMap<>();
        for (int i = start; i<nums.length;i++){
            //检查当前层，同元素有没有被用过
            if(used.containsKey(nums[i])){continue;}
            used.put(nums[i],true);
            if (path.isEmpty() || nums[i] >= path.getLast()){
                path.add(nums[i]);
                backtrack(nums,i+1);
                path.remove(path.size()-1);
            }
        }

    }
    private boolean isValid(List<Integer> path){
        return path.size() >= 2;
    }
}