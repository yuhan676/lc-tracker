class Solution {
    public int calculate(String s) {
        //Remove all white spaces in the string
        //这个地方记得是replaceAll, 也可以在后面运算符check的时候写成 !Character.isDigit(c) && c != ' '
        //replaceAll返回的是一个copy，不是原地修改，因为string不可修改
        s=s.replaceAll("\\s+","");
        //栈里存的是计算结果（要么是单个被加减的数字，要么是乘除的结果）
        //如果前一个operand是+-，直接入栈，如果前一个operand是*/，那就需要pop出来进行运算
        //栈里存的是可以提供给*/计算的结果
        //最后的输出=所有的栈内元素相加
        Stack<Integer> stack = new Stack<>();
        //可能出现digit》1的数字，那我们就得自己去parse它
        //默认所有数字都是正数，所以第一个数字前面的sign=+
        int num = 0;
        char sign = '+';

        for (int i = 0; i< s.length();i++){
            char c = s.charAt(i);
            //新方法：Character.isDigit(c)
            if (Character.isDigit(c)){
                num = num * 10 + (c - '0');
            }
            //这里的意思是假如我们到了最后一位了，那也是要进行num和前一个运算符的计算的
            //注意计算计算到最后一位数字的时候，它也被加入了上一步num的计算中
            if (!Character.isDigit(c) || i==s.length()-1){
                if (sign=='+'){
                    stack.push(num);
                }else if(sign=='-'){
                    stack.push(num * (-1));
                }else if(sign=='*'){
                    stack.push(stack.pop()*num);
                }else if(sign=='/'){
                    stack.push(stack.pop()/num);
                }
                //更新sign和num
                sign = c;
                num = 0;
            }
        }
        int res = 0;
        for (int number:stack ){
            res += number;
        }
        return res;

    }

}