# 单调栈Monotonic Stack
* 栈中所有元素递增或者递减
* 适合求当前元素左边或者右边，第一个比当前元素大或者小的元素
* 有严格规定的单调站（没有数值一样的元素），和非严格规定的（允许数值一样的元素）
* 在单调中存元素的下标，就方便我们求距离了
* 需要关注的是：需要递增还是递减的单调站（递增指从栈口到栈底）
* 递增：求当前元素后面第一个比它大的位置； 递减：求当前元素后面第一个比它小的位置
* 单调栈的作用是储存已经遍历过的元素，保证我们只需要遍历数组一遍就行
* 需要做的比较：比较栈顶的元素和当前遍历的元素
## 739. Daily Temperatures
* https://leetcode.com/problems/daily-temperatures/description/
* 文章：https://programmercarl.com/0739.%E6%AF%8F%E6%97%A5%E6%B8%A9%E5%BA%A6.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 视频：https://www.bilibili.com/video/BV1my4y1Z7jj?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
###暴力解法(会超时）
* 从每一个元素开始找后面有没有更温暖的日子，两层for循环
* n^2时间复杂度
```java
class Solution {
    public int[] dailyTemperatures(int[] temperatures) {
        int[] answer = new int[temperatures.length];
        for (int i=0;i<temperatures.length-1;i++){
            int daycount = 0;
            //需要一个flag处理如果之后找不到更暖和的日子的情况
            boolean found = false;
            for (int j=i+1; j<temperatures.length;j++){
                daycount++;
                if(temperatures[j]>temperatures[i]){
                    found = true;
                    break;}
            }
            answer[i] = found?daycount:0;
        }
        return answer;
    }
}
```
### 单调栈解法
* 单调栈要保证是从小到大的
* 遍历过的元素如果结果已经存放完（已经找到右边最近的比它数值大的元素），就可以弹出了
* 然后把当前遍历的元素加到栈里
* 比如[73,74,75],73先加入栈，然后遍历到74，74>73, res[0]=74下标-73下标=1, 73弹出，74入栈
* 假如新的元素比栈口元素小，就加入栈，导致栈里是单调递增
* 这样如果遇到新的比栈口大的元素，就可以将当前遍历元素和最小的栈内元素开始对比，一直对比到大于等于栈口元素
```java
class Solution {
    public int[] dailyTemperatures(int[] temperatures) {
        //也可以用Deque<Integer> st = new LinkedList<>();
        Stack<Integer> st = new Stack<>();
        int[] res = new int[temperatures.length];
        for (int i =0; i<temperatures.length;i++){
            //这里要记住，push要在whileloop以外
            //如果栈里还有元素，那就要一直和栈口比较
            while(!st.isEmpty() && temperatures[i]>temperatures[st.peek()]){
                int prevIndex = st.pop();
                res[prevIndex] = i-prevIndex;
            }
            st.push(i);
        }
        return res;
    }
}
```
## 496. Next Greater Element I
* https://leetcode.com/problems/next-greater-element-i/description/
* 文章：https://programmercarl.com/0496.%E4%B8%8B%E4%B8%80%E4%B8%AA%E6%9B%B4%E5%A4%A7%E5%85%83%E7%B4%A0I.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 视频：https://programmercarl.com/0496.%E4%B8%8B%E4%B8%80%E4%B8%AA%E6%9B%B4%E5%A4%A7%E5%85%83%E7%B4%A0I.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 跟着上一题的思路写出来了，先写一个helper function返回一个新数组，记录nums2中每一个元素的下一个bigger元素的数值
* 然后再两层for循环嵌套，寻找nums1[i] == nums2[j]的i，j值
* 时间复杂度是：O(m*n + n)
* 
```java
class Solution {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int[] ans = new int[nums1.length];
        int[] values = nextGreaterElementNums2(nums2);
        for (int i = 0; i<nums1.length;i++){
            for (int j=0; j<nums2.length;j++){
                if (nums1[i]==nums2[j]){
                    ans[i] = values[j];
                    break;
                }
            }
        }
        return ans;
    }
    private int[] nextGreaterElementNums2(int[] nums2){
        int[] nextGreaterElement = new int[nums2.length];
        Arrays.fill(nextGreaterElement,-1);
        Stack<Integer> st = new Stack<>();
        for (int i =0; i<nums2.length;i++){
            while(!st.isEmpty()&&nums2[i]>nums2[st.peek()]){
                int lastIndex = st.pop();
                nextGreaterElement[lastIndex]=nums2[i];
            }
            st.push(i);
        }
        return nextGreaterElement;
    }
}
```
### 优化成O(nums1.length + nums2.length)
```java
class Solution {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int[] ans = new int[nums1.length];
        int[] values = nextGreaterElementNums2(nums2);
        //使用hashmap储存已经用过的
        Map<Integer,Integer> nums2map = new HashMap<>();
        for (int i=0;i<nums2.length;i++){
            nums2map.put(nums2[i],i);
        }
        for (int j=0;j<nums1.length;j++){
            ans[j] = values[nums2map.get(nums1[j])];
        }
        return ans;
    }
    private int[] nextGreaterElementNums2(int[] nums2){
        int[] nextGreaterElement = new int[nums2.length];
        Arrays.fill(nextGreaterElement,-1);
        Stack<Integer> st = new Stack<>();
        for (int i =0; i<nums2.length;i++){
            while(!st.isEmpty()&&nums2[i]>nums2[st.peek()]){
                int lastIndex = st.pop();
                nextGreaterElement[lastIndex]=nums2[i];
            }
            st.push(i);
        }
        return nextGreaterElement;
    }
}
```
### leetcode上非常优雅的解法
* map直接储存nums2中每个数字右边最近的bigger number的值
* 遍历nums1的时候直接找map中有没有储存，没有的话default成-1
```java
class Solution {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Stack<Integer> stack = new Stack<>();
        Map<Integer, Integer> map = new HashMap<>();

        // Step 1: Prepare map of next greater elements from nums2
        for (int num : nums2) {
            while (!stack.isEmpty() && stack.peek() < num) {
                map.put(stack.pop(), num);
            }
            stack.push(num);
        }

        // Step 2: Build result using map for elements in nums1
        int[] result = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            result[i] = map.getOrDefault(nums1[i], -1);
        }

        return result;
        
    }
}
```
## 503. Next Greater Element II
* https://leetcode.com/problems/next-greater-element-ii/description/
* 文章：https://programmercarl.com/0503.%E4%B8%8B%E4%B8%80%E4%B8%AA%E6%9B%B4%E5%A4%A7%E5%85%83%E7%B4%A0II.html
* 视频：https://www.bilibili.com/video/BV15y4y1o7Dw?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
### 开辟新数组
* O(2n)
* 相当于成环了,那我们把原来的数组复制两遍，ans也做成两倍的长度，最后只返回一半就行
```java
class Solution {
    public int[] nextGreaterElements(int[] nums) {
        //原数组复制成而被长度长度，模拟成环
        int[] nums2 = new int[nums.length*2];
        for (int i =0; i<nums.length;i++){
            nums2[i] = nums[i];
            nums2[i + nums.length] = nums[i];
        }
        //注意ans一开始要定义二倍长度
        int[] ans = new int[nums.length*2];
        //初始化成，一开始全部默认后面没有更大的
        Arrays.fill(ans,-1);
        Stack<Integer> st = new Stack<>();
        for (int i =0; i<nums.length*2;i++){
            while (!st.isEmpty()&&nums2[i]>nums2[st.peek()]){
                int lastIndex = st.pop();
                ans[lastIndex] = nums2[i];
            }
            //这里一定记住我们存的是下标
            st.push(i);
        }
        System.out.print(Arrays.toString(ans));
        return Arrays.copyOfRange(ans,0,nums.length);
    }
}
```
### 在原数组基础上模拟成环
* 取模的方式模拟转圈
* 遍历的时候还是按照二倍数组遍历（i<numsize*2)，如何防止越界》
* 我们在取i的时候，取模（i%num.size),得到下标在原数组里进行移动
```java
class Solution {
    public int[] nextGreaterElements(int[] nums) {

        if (nums==null || nums.length<=1){
            return new int[] {-1};
        }
        int[] ans = new int[nums.length];
        //初始化成，一开始全部默认后面没有更大的
        Arrays.fill(ans,-1);
        Stack<Integer> st = new Stack<>();
        //因为要转两圈，i还是*2
        for (int i =0; i<nums.length*2;i++){
            while (!st.isEmpty()&&nums[i%nums.length]>nums[st.peek()]){
                int lastIndex = st.pop();
                ans[lastIndex] = nums[i%nums.length];
            }
            //这里一定记住我们存的是下标
            st.push(i%nums.length);
        }
        System.out.print(Arrays.toString(ans));
        return ans;
    }
}
```
