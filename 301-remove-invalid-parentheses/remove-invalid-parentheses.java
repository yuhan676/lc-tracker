class Solution {
    public List<String> removeInvalidParentheses(String s) {
        Stack<Character> st = new Stack<>();
        int removeRight = 0;
        for (char c: s.toCharArray()){
            if (c=='('){
                st.push('(');
            }else if(c==')'){
                if (!st.isEmpty()){st.pop();}
                else{
                    removeRight ++;
                }
            }
        }
        int removeLeft = st.size();
        StringBuilder path = new StringBuilder();
        Set<String> resSet = new HashSet<String>();
        backtrack(s, 0, 0, removeLeft, removeRight, resSet, path);
        return new ArrayList<>(resSet);
    }
    //openleft用来监督当前stringbuilder里有没有开口的左括号，只有有的情况下，才能加上右边的括号才合法
    //结果先用一个string set收集着方便去重
    private void backtrack(String s, int index, int openLeft, int removeLeft, int removeRight, Set<String> res, StringBuilder path){
        //终止条件:遍历到s最后一位了
        if (index ==s.length()){
            //只有当openLeft, removeLeft, removeRight 都为0的时候，结果才可以加入res
            if (openLeft == 0 && removeRight == 0 && removeLeft == 0){
                res.add(path.toString());
            }
            return;
        }
        char c = s.charAt(index);
        //记录当前stringbuilder长度方便回溯
        int len = path.length();
        //每个字节可以选择保留和不保留
        if (c=='('){
            //删除，就不需要修改path（path中不加入c），所以不需要回溯；
            if (removeLeft>0){
                backtrack(s, index+1, openLeft,removeLeft -1, removeRight, res, path);
            }
            //保留这个c，就需要修改path
            path.append(c);
            backtrack(s,index+1,openLeft+1,removeLeft,removeRight,res,path);
            //递归完回溯：即删除刚才append的c
            path.setLength(len);
        }else if (c==')'){
            //删除
            if (removeRight>0){
                //path不改变，不需要回溯
                backtrack(s,index+1,openLeft,removeLeft,removeRight -1, res, path);
            }
            //只有前面还有openleft的时候，才能保留当前的右括号
            if (openLeft>0){
                path.append(c);
                //递归
                backtrack(s,index+1,openLeft-1,removeLeft,removeRight,res,path);
                //回溯
                path.setLength(len);
            }
        }else{
            //普通字符，一定保留，即加入path
            path.append(c);
            backtrack(s,index+1,openLeft,removeLeft,removeRight,res,path);
            //加入了就需要回溯
            path.setLength(len);
        }
    }
}