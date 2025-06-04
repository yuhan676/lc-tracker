class Solution {
    public int calculate(String s) {
        // sign before the (), previous calculation result
        Stack<Integer> st = new Stack<>();
        //num = number in construction
        //result = 中程计算结果
        int num = 0;
        int res = 0;
        int n = s.length();
        //记录当前number的sign
        int sign = 1;
        for (int i=0; i<n;i++){
            char c = s.charAt(i);
            if (Character.isDigit(c)){
                num = num*10 + c - '0';
            }else if (c=='+' || c=='-'){
                res += sign * num;
                num = 0;
                sign = (c == '+') ? 1:-1;
            }else if (c=='('){
                //（）前结果存入栈
                st.push(res);
                //（）前sign存入栈
                st.push(sign);
                //reset
                sign = 1;
                //result现在只记录括号中的结果，所以要清零
                res = 0;
            }else if (c==')'){
                //终止计算括号里所有数字的和
                res += sign * num;
                num = 0;
                //给括号里的result加上外层包的sign
                res *= st.pop();
                //给res加上括号外面的运算结果
                res += st.pop();
            }
        }
        //假设input仅仅是数字，没有任何符号，那res就还没有改变
        if (num!= 0) {res += sign * num;}
        return res;
    }
}