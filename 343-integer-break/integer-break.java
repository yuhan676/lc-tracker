class Solution {
    Map<Integer, Integer> maxBreak = new HashMap<>();

    public int integerBreak(int n) {
        maxBreak.put(2,1);
        return backtrack(n);
        
    }

    private int backtrack(int n){
        if (maxBreak.containsKey(n)) return maxBreak.get(n);
        int res = 0;
        for (int i = 1; i<= n/2; i++){
            res = Math.max(res, Math.max(i * (n-i), i * backtrack(n-i)));
        }
        maxBreak.put(n,res);
        return res;
    }
}