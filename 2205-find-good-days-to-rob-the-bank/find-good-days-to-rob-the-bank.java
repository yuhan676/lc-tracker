class Solution {
    public List<Integer> goodDaysToRobBank(int[] security, int time) {
        int n = security.length;
        List<Integer> res = new LinkedList<>();
        if (time ==0) {
            for(int i = 0; i< n;i++){
                res.add(i);
            }
            return res;
        }
        if (n < time *2) return res;

        //constructing nonInc && nonDec int[] to save TC
        int[] nonInc = new int[n];
        int[] nonDec = new int[n];
        nonInc[0] = 1;
        nonDec[n-1] = 1;
        for(int i =1; i<n;i++){
            if (security[i]<=security[i-1]){
                nonInc[i] = nonInc[i-1] +1;
            }else{
                nonInc[i] = 0;
            }
        }
        for(int i =n-2; i>=0;i--){
            if (security[i]<=security[i+1]){
                nonDec[i] = nonDec[i+1] +1;
            }else{
                nonDec[i] = 0;
            }
        }
        System.out.print(Arrays.toString(nonInc));
        System.out.print(Arrays.toString(nonDec));
        for (int i=time; i<n-time;i++){
            if (nonInc[i]>=time && nonDec[i]>=time){
                res.add(i);
            }
        }
        return res;
    }

}