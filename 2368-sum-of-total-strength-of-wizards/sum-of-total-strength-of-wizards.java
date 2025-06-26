class Solution {
    public int totalStrength(int[] strength) {
        //这里学一下10^9 的写法,是scientific notation。 pow的写法有性能开销（返回 double，有精度风险 + 性能开销）
        //直接写成1000000007也可以
        int MOD = (int) 1e9 +7;
        int n = strength.length;
        Stack<Integer> st = new Stack<>();
        int[] left = new int[n];
        int[] right = new int[n];

        long[] prefix = new long[n+1];
        long[] pprefix = new long[n+2];
        //构造prefix和preprefix： pass1
        for(int i =1;i<=n;i++){
            prefix[i] = prefix[i-1] + strength[i-1];
            pprefix[i] = pprefix[i-1] + prefix[i-1];
        }
        //最后一个pprefix手动补上
        pprefix[n+1] = pprefix[n] + prefix[n];
        //构造i作为最小元素的子数组的左边界：pass2
        for (int i = 0;i <n;i++){
            while (!st.isEmpty() && strength[i] < strength[st.peek()]){
                st.pop();
            }
            left[i] = st.isEmpty()? -1: st.peek();
            st.push(i);
        }
        st.clear();
        //pass3:构造i为最小元素的字数组的右边界，记得不规则设计：左边严格递增stack，右边就不严格
        for (int i = n-1; i>=0; i--){
            while (!st.isEmpty() && strength[i] <= strength[st.peek()]){
                st.pop();
            }
            right[i] = st.isEmpty()? n:st.peek();
            st.push(i);
        }


        long res = 0;
        for (int i =0; i<n;i++){
            int r = right[i];
            int l = left[i];
            //本来我写的是：res += ((pprefix[r+1] - pprefix[i+1]) * (i-l) - (pprefix[i+1] - pprefix[l+1]) * (r-i)) * strength[i];
            //后来发现有可能溢出
            //而且遇到res是负数的情况下，没有办法返回正数的结果
            //所以每一步都要确保是正数，取模，防止java的负数取模陷阱
            long leftSum = (pprefix[r+1] - pprefix[i+1] + MOD) % MOD;
            long rightSum = (pprefix[i+1] - pprefix[l+1] + MOD) % MOD;
            long total = (leftSum * (i-l) % MOD - rightSum * (r-i)%MOD + MOD) %MOD;
            res += (total * strength[i]) % MOD;
        }

        return (int)(res%MOD);
    }
}