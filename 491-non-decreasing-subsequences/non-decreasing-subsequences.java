class Solution {
    List<List<Integer>> res;
    List<Integer> path = new LinkedList<>();
    Set<List<Integer>> resSet = new HashSet<>();

    public List<List<Integer>> findSubsequences(int[] nums) {
        backtrack(nums,0);
        res = new LinkedList<>(resSet);
        return res;
    }

    private void backtrack(int[] nums, int start){
        if (isValid(path)){
            resSet.add(new LinkedList<>(path));
        }
        Map<Integer, Boolean> used = new HashMap<>();
        for (int i = start; i<nums.length;i++){
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