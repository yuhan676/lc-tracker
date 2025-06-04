class Solution {

    List<List<Integer>> res = new LinkedList<>();
    List<Integer> path = new LinkedList<>();

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        //这个题我一开始看以为是完全背包问题每个物品可以用无数次，需要装满一个背包
        //但是背包dp解决的是组合/排列总数：一共有多少种方法填满target背包重量
        //求出所有路径，得靠dfs，就是回溯
        if (candidates.length==0) return res;
        backtracking(candidates, target, 0, 0);
        return res;


    }
    private void backtracking(int[]candidates, int target, int sum, int startIndex){
        if (sum == target){
            res.add(new LinkedList<>(path));
            return;
        }
        if (sum > target){
            return;
        }
        for (int i = startIndex; i< candidates.length; i++){
            path.add(candidates[i]);
            sum+= candidates[i];
            //只要还能用当前的i，就一直用i，反正没有超过sum，i可以用无限次，如果超过了，i会backtrack以后自动在for循环中++
            backtracking(candidates, target, sum, i);
            path.remove(path.size()-1);
            sum -= candidates[i];
        }
    }
}