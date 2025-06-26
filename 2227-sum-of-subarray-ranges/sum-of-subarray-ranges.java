class Solution {
    public long subArrayRanges(int[] nums) {
        int n = nums.length;
        //Increasing monotonic stack from bottom to up: to get the next smaller item
        Stack<Integer> increSt = new Stack<>();
        //Decreasing monotonic stack from bottom to up: to get the next bigger item
        Stack<Integer> decreSt = new Stack<>();

        int[][] closestBig = new int[n][2];
        int[][] closestSmall = new int[n][2];

        //get the bigger/ smaller element to the left: iterate from left to right
        for (int i = 0; i<n;i++){
            //find the closest smaller to the left
            //！！这里的陷阱：假如我们保证栈是严格单调的：使用nums[i] <= nums[increSt.peek()
            //就会出现同样大小的element被重复当成同一个字数组的最大值/最小值
            //导致同一个字数组被多次计算
            //例子：nums =[1,3,3]
            //使用严格单调栈：closest bigger element index:[-1, 1]，[-1, 3]，[-1, 3]
            //这样，两个3都被算成了[-1,3]的最大值
            //我们应该保证的是：3只被当成最大值一次，也就是说，第二个3的边界，应该是index 2, 因为它在[2，3](它本身)这个字数组才能算最大的
            //所以出栈策略要改成nums[i] <nums[increSt.peek()] -> increSt.pop()
            //这样子的话，指向的就是上一个数值相等或者比当前数值大的element的index
            while (!increSt.isEmpty() && nums[i] <nums[increSt.peek()]){
                increSt.pop();
            }
            closestSmall[i][0] = increSt.isEmpty() ? -1: increSt.peek();
            increSt.push(i);
            //find the index of the closest bigger element to the left
            while (!decreSt.isEmpty() && nums[i] >nums[decreSt.peek()]){
                decreSt.pop();
            }
            closestBig[i][0] = decreSt.isEmpty() ? -1: decreSt.peek();
            decreSt.push(i);
        }
        increSt.clear();
        decreSt.clear();


        //get the bigger/ smaller element to the right: iterate from the right to left
        //不对称设计：单调栈构建的时候，如果两个方向都用严格比较，相等值会被重复考虑；
        //所以我们选择：
        //一个方向严格比较（比如左边界）→ 保证相等值不会都被看作“同一个最大/最小”；
        //另一个方向非严格比较（比如右边界）→ 避免重复的最大/最小计入同一个区间。
        for (int i = n-1; i>= 0;i--){
            while (!increSt.isEmpty() && nums[i] <=nums[increSt.peek()]){
                increSt.pop();
            }
            closestSmall[i][1] = increSt.isEmpty() ? n: increSt.peek();
            increSt.push(i);

            //find the index of the closest bigger element to the left
            while (!decreSt.isEmpty() && nums[i] >=nums[decreSt.peek()]){
                decreSt.pop();
            }
            closestBig[i][1] = decreSt.isEmpty() ? n: decreSt.peek();
            decreSt.push(i);
        }
        System.out.println("closest bigger element index:");
        for (int[] pair: closestBig){
            //注意不是as list, 而是Arrays.toString()
            System.out.println(Arrays.toString(pair));
        }
        System.out.println("closest smaller element index:");
        for (int[] pair: closestSmall){
            //注意不是as list, 而是Arrays.toString()
            System.out.println(Arrays.toString(pair));
        }

        //sum (max in all subarry - min in all subarray) = sum (each element * time it appears as the biggest - each element * time it appears as the smallest);
        //time it appears as the biggest = number of subarray from range (left smaller to right smaller), number of valid subarray where this element is the largest = (i - closestSmall[i][0]) * (closestSmall[i][1] - i)
        long res = 0;

        for (int i = 0; i<n;i++){
            //这里注意不要搞反了：closest big的界限内，element就是最大的，closest small的界限内，element就是最小的
            long countSumSmallest = (long)(i - closestSmall[i][0]) * (closestSmall[i][1] - i) * nums[i];
            long countSumBiggest = (long)(i - closestBig[i][0]) * (closestBig[i][1] - i) * nums[i];
            res += countSumBiggest - countSumSmallest;
        }
        return res;

    }
}