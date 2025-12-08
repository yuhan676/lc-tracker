class Solution {
    public String[] splitMessage(String message, int limit) {
        // subStrNum = number of substring we are slicing message into
        // cummulative_a_length = total string length of count 'a' in all substrings in res containing <a/b>
        int subStrNum = 0;
        int cummulative_a_length = 0;
        int i = 0;

        //因为要找到b，我们不能直接用message.length / limit, 因为suffix<a/b>的长度会随着b变化，所以之后从b=0开始一个一个试，这里的b也就是subStrNum，切分的总份数
        //我们得看，什么情况下，b合格
        //第一，suffix也就是“</b.length>”假如比limit大，那一定不合格
        //第二，假如res中所有的substring（包括message本身的内容，a,</b>的内容，假如总长度要比subStrNum * limit 大，那也不合格
        while(3 + String.valueOf(subStrNum).length()*2 < limit && cummulative_a_length + message.length() + (3 + String.valueOf(subStrNum).length()) * subStrNum > limit * subStrNum){
            subStrNum ++;
            //这里记住，每一个res中的stirng的'a'的长度都是叠加的
            cummulative_a_length += String.valueOf(subStrNum).length();
        }

        List<String> res = new ArrayList<>();

        //现在subStrNum满足了res总长度小于limit * subStrNum了，我们开始检查是否符合第一个条件
        if (3 + String.valueOf(subStrNum).length() * 2 < limit){
            //遍历j从1到subStrNum
            for (int j = 1; j <=subStrNum;j++){
                //现在计算prefix的长度: limit - j's digit length - </>'s length - subStrNum's digit's length 
                int prefix_len = limit - String.valueOf(j).length() - 3 - String.valueOf(subStrNum).length();
                String prefix = message.substring(i, Math.min(i + prefix_len, message.length()));
                res.add(prefix + "<" + j + "/" + subStrNum + ">");
                i += prefix_len;
            }
        }
        
        return res.toArray(new String[res.size()]);
        
        
    }
}