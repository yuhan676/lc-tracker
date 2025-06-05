class Solution {
    List<List<Integer>> res = new LinkedList<>();
    List<Integer>path = new LinkedList<>();
    public List<List<Integer>> combinationSum3(int k, int n) {
        backtrack(k,n,0,1);
        return res;
    }

    private void backtrack(int k, int n, int sum, int startIndex){
        if (sum >n || path.size()>k) return;
        if (sum==n && path.size()==k){
            res.add(new LinkedList<>(path));
            return;
        }
        for (int i = startIndex; i<= 9; i++){
            path.add(i);
            sum += i;
            //记住这里是startindex+1
            //一开始写成backtrack(k,n,sum,startIndex+1)
            //如果这个题目改成每个数字可以用很多次，那就call变成backtrack(k,n,sum,i),
            //就会输出
            //k=3, n =7, [[1,1,5],[1,2,4],[1,3,3],[2,2,3]]
            backtrack(k,n,sum,i+1);
            path.remove(path.size()-1);
            sum -= i;
        }
    }
}