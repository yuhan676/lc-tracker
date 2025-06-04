class Solution {
    public List<String> removeInvalidParentheses(String s) {
        int leftRem = 0;
        int rightRem = 0;
        for (char c: s.toCharArray()){
            if (c=='('){
                leftRem ++;
            }else if (c==')'){
                if (leftRem>0){
                    leftRem --;
                }else{
                    rightRem ++;
                }
            }
        }
        int validLength = s.length() - leftRem -rightRem;
        char[] path = new char[validLength];
        List<String> res = new ArrayList<>();
        backtrack(s,0,path,0,leftRem,rightRem,0,res);
        return res;

    }
    private void backtrack(String s, int index, char[] path, int pathIndex, int leftRem, int rightRem, int balance, List<String> res){
        //balance: (.count - ).count, 如果多出来一个‘）’，那接下来的也就都剪枝了
        if (balance < 0){
            return;
        }
        if (index == s.length()){
            if (leftRem == 0 && rightRem == 0){
                res.add(new String(path));
            }
            return;
        }
        char c = s.charAt(index);
        //三种情况都写成if，就可以保证删或者不删的情况都能覆盖到
        //如果写成if else...elseif..else,每次只执行一个分支(删除/保留），就无法同时探索“删除”和“保留”的两种路径，导致漏解。
        //OP1
        if (c == '(' && leftRem>0 && (pathIndex == 0 || path[pathIndex-1] != '(')){
            //删除可以删除的(
            //剪枝：想避免的是构建路径中的重复删除方式，所以检查的不是index==0 || s.charAt(index) == s.charAt(index-1)
            //（（（：我们只删第一个 (，其他重复的 ( 只保留，不再尝试删除，防止生成重复的结果字符串。

                backtrack(s,index+1,path,pathIndex,leftRem-1,rightRem,balance,res);
            
        }
        //OP2
        if (c==')' && rightRem >0 && (pathIndex == 0 || path[pathIndex-1] != ')')){
            //删除可以删除的)
                backtrack(s,index+1,path,pathIndex,leftRem,rightRem-1,balance,res);
        }
        //OP3: 保留
        if(pathIndex < path.length){
            //保留当前character （可能是非括号字符，要么就是不需要删除左右括号的情况下）,加入path
            path[pathIndex] = c;
            int newBalance = balance;
            if (c=='('){
                newBalance ++;
            }else if (c==')'){
                newBalance --;
            }
            backtrack(s,index+1,path,pathIndex+1,leftRem,rightRem,newBalance,res);
        }
        
    }
}