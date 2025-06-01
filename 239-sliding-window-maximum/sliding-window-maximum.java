class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int right = 0; 
        int n = nums.length;
        int resSize = n - k + 1;
        int[] res = new int[resSize];

        MonoDeque que = new MonoDeque();
        //initialize the first window 
        for (; right < k; right ++){
            que.add(nums[right]);
        }
        int resIndex = 0;
        res[resIndex++] = que.peek();
        for (; right < n; right ++){
            //poll left
            que.poll(nums[right - k]);
            que.add(nums[right]);
            res[resIndex++] = que.peek();
        }
        return res;
    }
}    


class MonoDeque{
    Deque<Integer> q = new LinkedList<>();

    void poll(int val){
        if (!q.isEmpty() && q.peek() == val){
            q.poll();
        }
    }

    void add(int val){
        while (!q.isEmpty() && val > q.getLast()){
            q.removeLast();
        }
        q.add(val);
    }

    int peek(){
        if (!q.isEmpty()){
            return q.peek();
        }
        return 0;
    }
}