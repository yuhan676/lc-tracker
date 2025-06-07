class Solution {
    List<List<Integer>> res = new LinkedList<>();
    List<Integer> path = new LinkedList<>();
    public List<List<Integer>> subsets(int[] nums) {
        //path: 1,2
        //res:[],[1],[1,2]
        backtrack(nums,0);
        return res;
    }
    private void backtrack(int[] nums, int startIndex){
        //每到一个新的节点，就把path复制一份进去，不需要检查是否到头
        res.add(new LinkedList<>(path));
        if (startIndex >= nums.length){
            //开头已经超过nums最后一位就没必要继续收集了
            return;
        }
        for (int i = startIndex; i<nums.length; i++){
            path.add(nums[i]);
            backtrack(nums,i+1);
            path.remove(path.size()-1);
        }
    }
}