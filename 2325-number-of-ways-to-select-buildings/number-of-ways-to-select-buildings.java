class Solution {
    public long numberOfWays(String s) {
        long zero = 0, one = 0, zeroone = 0, onezero = 0, total = 0;
        for (char c: s.toCharArray()){
            if (c == '0'){
                zero ++;
                //假如之前有one，现在就加一种onezero的可能性
                onezero += one;
                total += zeroone;
            }else{
                one++;
                zeroone += zero;
                total += onezero;
            }
        }
        return total;
    }
}