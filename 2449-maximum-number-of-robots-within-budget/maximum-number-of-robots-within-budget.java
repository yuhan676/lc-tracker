class Solution {
    public int maximumRobots(int[] chargeTimes, int[] runningCosts, long budget) {
        int n = chargeTimes.length;
        MonoDeque mq = new MonoDeque();
        int left = 0;
        long rcSum = 0;
        long totalCost = 0;
        int maxRobot = 0;
        for (int right = 0; right <n; right ++){
            int ct = chargeTimes[right];
            int rc = runningCosts[right];
            mq.add(ct);
            rcSum += rc;
            int robotCount = right - left + 1;
            //totalCost = (mq.peek() != null ? mq.peek():0) + robotCount * rcSum;
            //这么写的话左边是int，右边是integer，有时候编译器无法拆箱,所以逻辑交给monodeque class
            totalCost = mq.peek()+ (long)robotCount * rcSum;
            //从左边界开始滑动删除元素
            while (totalCost > budget){
                int leftct = chargeTimes[left];
                int leftrc = runningCosts[left];
                mq.poll(leftct);
                rcSum -= leftrc;
                //别忘了left++
                left ++;
                robotCount = right - left + 1;
                totalCost = mq.peek()+ (long)robotCount * rcSum;
            }
            maxRobot = Math.max(maxRobot, robotCount);
        }
        return maxRobot;
    }
}
//自定义一个单调队列，可以O(1)维护窗口内的最大值
class MonoDeque{
    Deque<Integer> deque = new LinkedList<>();

    void poll(int val){
        if (!deque.isEmpty() && deque.peek() == val){
            deque.poll();
        }
    }

    void add(int val){
        while(!deque.isEmpty() && val>deque.getLast()){
            deque.removeLast();
        }
        //加入到队尾
        deque.add(val);
    }

    int peek(){
        //现在假如为空会返回null，如果加入了检查是否为empty，是empty就返回0， 就可以规避这一点
        if (!deque.isEmpty()){
            return deque.peek();
        }
        return 0;
    }
}