class Solution {
    List<List<Integer>> res = new ArrayList<>();
    List<Integer> path = new ArrayList<>();
    int sum = 0;

    public List<List<Integer>> combinationSum3(int k, int n) {
        backtrack(k,n,1);
        return res;
    }

    private void backtrack(int k, int n, int start){
        if (path.size() == k){
            if (sum==n){
                res.add(new ArrayList<>(path));
                return;
            }
            return;
        }

        for (int i = start; i <=9; i++){
            path.add(i);
            sum += i;
            backtrack(k,n,i+1);
            path.remove(path.size()-1);
            sum -=i;
        }
    }
}
