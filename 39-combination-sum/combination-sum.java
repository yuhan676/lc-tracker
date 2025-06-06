class Solution {

    Set<List<Integer>> res = new HashSet<>();
    List<Integer> path = new LinkedList<>();

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        //sort out the candidates to simplify the search process
        if (candidates.length==0) return new LinkedList<>();
        Arrays.sort(candidates);
        backtrack(candidates, target, 0, 0);
        return new LinkedList<>(res);
    }

    private void backtrack(int[] candidates, int target, int sum, int index){
        if (sum > target){
            return;
        }
        if (sum==target){
            res.add(new LinkedList<>(path));
            return;
        }
        for (int i =index; i< candidates.length && sum + candidates[i] <= target;i++){
            sum += candidates[i];
            path.add(candidates[i]);
            backtrack(candidates, target, sum, i);
            sum -= candidates[i];
            path.remove(path.size()-1);
        }
        
    }
}