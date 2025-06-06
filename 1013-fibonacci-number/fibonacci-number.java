class Solution {
    public int fib(int n) {
        if (n == 0 || n == 1) return n;
        int[] fib = new int[2];
        fib[0] = 0;
        fib[1] = 1;
        for(int i =2; i<=n;i++){
            int temp = fib[0] + fib[1];
            fib[0] = fib[1];
            fib[1] = temp;
        }
        return fib[1];
    }
}