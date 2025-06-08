class Solution {
    public int integerBreak(int n) {
        if (n==2) return 1;
        else if (n==3) return 2;
        else{
            int k = n/3;
            int rem = n%3;
            if (rem == 0) return (int) Math.pow(3,k);
            else if (rem == 1) return (int)Math.pow(3,k-1) * 4;
            else{
                return (int)Math.pow(3,k) * 2;
            }
        }

    }
}