class Solution {
    public int calculate(String s) {
        s = s.replaceAll("\\s+","");
        int n = s.length();

        int num = 0;
        int res = 0;
        //1 = +, -1 = -
        int sign = 1;
        //"- (3 + (4 + 5))"的情况，不能一遇')'到就把flip消除掉
        Stack<Integer> signStack = new Stack<>(); // -1 = -, 1 = +
        signStack.push(1);
        for (int i=0;i<n;i++){
            char c = s.charAt(i);
            if (Character.isDigit(c)){
                num = num*10 + c-'0';
            }
            else if (c=='+' || c =='-'){
                res += signStack.peek() * sign * num;
                num = 0;
                if (c =='+'){sign = 1;}
                else{sign = -1;}
            }
            else if(c=='('){
                signStack.push(sign*signStack.peek());
                sign = 1;
            }else if (c==')'){
                res += sign * signStack.pop() * num;
                sign = 1;
                num = 0;
            }
        }
        res += sign * signStack.peek() * num; 
        return res;
    } 
}