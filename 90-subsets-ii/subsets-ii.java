class Solution {
    List<List<Integer>> res = new LinkedList<>();
    List<Integer> path = new LinkedList<>();
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        backtrack(nums,0);
        return res;

    }
    private void backtrack(int[] nums, int start){
        res.add(new LinkedList<>(path));
        for (int i = start; i< nums.length; i++){
            //一开始写成i>0 了，就出错了，会把之后的index的一些开始元素都跳过，而且也会错过合法的子集（比如到了1,2,它会跳过下一个2的遍历，无法得到1,2,2的子集
            if (i > start && nums[i] == nums[i-1]){
                continue;
            }
            path.add(nums[i]);
            backtrack(nums, i+1);
            path.remove(path.size()-1);
        }
    }

    
}