class Solution {
    //卡丹算法变体解法，没有reverse overhead
    //时间复杂度： 26 ^2 * n
    public int largestVariance(String s) {
        int res = 0;
        int[] count = new int[26];
        for (char c: s.toCharArray()){
            count[c-'a'] ++;
        }
        //制造a,b的比较pair
        for (int i = 0; i<26;i++){
            for (int j =0; j<26;j++){
                //假如两个字母一样，就没有比较的意义了
                //假如字母根本没出现，也根本没必要构造pair
                if (i == j || count[i] == 0 || count[j] == 0) continue;
                
                int countA = 0;
                int countB = 0;
                int remainB = count[j];
                int currMaxVar = 0;

                for(char c:s.toCharArray()){
                    if (c-'a' == i) countA ++;
                    if (c-'a' == j) {
                        countB ++;
                        remainB --;
                    }
                    if (countB >0){
                        //反正肯定不会<0
                        currMaxVar = Math.max(currMaxVar, countA-countB);
                    }
                    //后面还有b，再拓展窗口也只会变得更负，不如直接重制
                    if (countA < countB && remainB >0){
                        countA = 0;
                        countB = 0;
                    }
                }
                //这个a,b pair比较完了，看看它的currmaxvar 和res谁更大
                res = Math.max(currMaxVar, res);
            }
        }
        return res;
    }
}