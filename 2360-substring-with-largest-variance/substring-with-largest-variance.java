class Solution {
    //卡丹算法解法
    //时间复杂度： 26 ^2 * n, charset最大的话就是每个字母都包括
    //每一组a，b遍历string两次（一次正序，一次倒序）
    public int largestVariance(String s) {
        int res = 0;
        Set<Character> charset = new HashSet<>();
        for (char c: s.toCharArray()){
            charset.add(c);
        }
        //制造a,b的比较pair
        for (char a:charset){
            for (char b:charset){
                //假如两个字母一样，就没有比较的意义了
                if (a==b) continue;
                //这里的一个易错点：kadane(s,a,b)和kadane(s,b,a)两种情况（a比b多，b比a多）是由两层for循环保证都会call到的
                //但是我们在找'a比b多‘的情况下，假如我们的string是baa，那一上来diff就变成了-1，diff归0，hasB变成false，即使之后diff变成2（2个a,++两次），只有在hasA, hasB的情况下才会更新maxvar的数值，所以解决办法只能是后序遍历一遍，遍历顺序变成aab，就能准确找到a比b多的时候的var
                res = Math.max(res, kadane(s,a,b));
                //记得string本身不可变，但是sb可以，reverse的时间复杂度也是O(n)
                res = Math.max(res, kadane(new StringBuilder(s).reverse().toString(),a,b));
            }
        }
        return res;
    }

    private int kadane(String s, char a, char b){
        int maxVar = 0;
        boolean hasa = false;
        boolean hasb = false;
        int diff= 0;
        for (char c: s.toCharArray()){
            if (c!=a&& c!=b){
                continue;
            }
            if (c==a){
                hasa = true;
                diff ++;
            }
            if (c==b){
                hasb = true;
                diff --;
            }
            if (diff<0){
                diff = 0;
                hasa = false;
                hasb = false;
            }
            if (hasa && hasb){
                maxVar = Math.max(maxVar, diff);
            }
        }
        return maxVar;
    }
}