class Solution {
    char[] path;
    List<String> res = new LinkedList<>();
    public List<String> generateParenthesis(int n) {
        int remLeft = n;
        int remRight = n;
        path = new char[n*2];
        backtracking(n,0,0,n,n);
        return res;
    }

    private void backtracking(int n, int index, int balance, int remLeft, int remRight){
        if (index == 2*n){
            if (balance == 0 && remLeft == 0 && remRight == 0){
                res.add(new String(path));
            }
            return;
        }
        if (remLeft >0){
            path[index] = '(';
            backtracking(n, index +1, balance+1, remLeft-1, remRight);

        }
        if (remRight > 0 && balance > 0){
            path[index] = ')';
            //如果要在这里手动改变remright和balance的数字，之后就得手动回溯
            // remRight --;
            // balance --;
            //如果在call stack里储存的话，就不需要手动回溯
            backtracking(n, index +1, balance-1, remLeft, remRight-1);
        }
    }
}