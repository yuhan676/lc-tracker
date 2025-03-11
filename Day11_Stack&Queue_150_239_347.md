# Stack and Queue 2
## 150. Evaluate Reverse Polish Notation
* https://leetcode.com/problems/evaluate-reverse-polish-notation/description/
* 文章： https://programmercarl.com/0150.%E9%80%86%E6%B3%A2%E5%85%B0%E8%A1%A8%E8%BE%BE%E5%BC%8F%E6%B1%82%E5%80%BC.html
* 视频：https://programmercarl.com/0150.%E9%80%86%E6%B3%A2%E5%85%B0%E8%A1%A8%E8%BE%BE%E5%BC%8F%E6%B1%82%E5%80%BC.html
* reverse polition notation：/后缀表达式/后续表达式
* 二叉树的遍历方式是左右中
* ![Screenshot 2025-03-11 at 12 02 04](https://github.com/user-attachments/assets/b441bf3b-ade6-40f3-af2e-6563932c0714)
* 后缀表达式不需要任何括号
* 遇见数字就加入栈里，遇到操作符就取出栈顶两个数字做计算，结果再加入到栈里
* ![Screenshot 2025-03-11 at 12 41 51](https://github.com/user-attachments/assets/3b307864-1349-4992-be44-c0f8e933a7c4)

```java
class Solution {
    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();

        for (int i =0; i< tokens.length; i++){
            int res;
            if (tokens[i].equals ("+")){//这里不能用== 要用.equals()! == 比较的是对象的引用，另外一定要用双引号，因为“+”是string，‘+’是char
                res = stack.pop() + stack.pop();
                stack.push(res);
            }else if (tokens[i].equals ("-")){
                //这里要注意操作顺序，前一个减后一个，不是直接stack.pop() - stack.pop()
                int b = stack.pop();
                int a = stack.pop();
                res = a-b;
                stack.push(res);
            }else if (tokens[i].equals ("*")){
                res = stack.pop() * stack.pop();
                stack.push(res);
            }else if (tokens[i].equals ("/")){
                //这里也要注意操作顺序，前一个除以后一个
                int b = stack.pop();
                int a = stack.pop();
                res = a/b;
                stack.push(res);
            }
            else{
                stack.push(Integer.parseInt(tokens[i]));//string转换成integer要用Integer.parseInt()!
                //也可以写作 stack.push(Integer.valueOf(tokens[i]))
            }
        }
        return stack.pop();
    }
}
```
## 239. Sliding Window Maximum 单调队列monotonic queue的应用：求滑动窗口里的最大值
* https://leetcode.com/problems/sliding-window-maximum/description/
* 文章：https://programmercarl.com/0239.%E6%BB%91%E5%8A%A8%E7%AA%97%E5%8F%A3%E6%9C%80%E5%A4%A7%E5%80%BC.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 视频： https://www.bilibili.com/video/BV1XS4y1p7qj?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 单调队列（Monotonic Queue）
* 📌  定义： 单调队列是一种特殊的队列，它在维护元素的同时，保持队列内的元素单调有序（递增或递减）。

* 📌 分类：

* 单调递增队列（Monotonic Increasing Queue）：队列内元素从前到后递增。
* 单调递减队列（Monotonic Decreasing Queue）：队列内元素从前到后递减。
* 📌 作用：
* 快速获取区间最大/最小值（比如滑动窗口最大值问题）
* 优化动态规划（比如单调队列优化 DP）
* 单调递增：monotonic increasing/ non-decreasing
  * 严格单调递增（Strictly Increasing）：后面的元素必须严格大于前面的元素 a_i < a_{i+1}
  * 非严格单调递增（Non-Decreasing）：后面的元素可以和前面的元素相等 a_i ≤ a_{i+1}
* 单调递减： monotinic decreasing/ non increasing
* 为什么优先队列priority queue不能高效实现
  * priority queue通常使用最大堆max heap来维护窗口内最大值
  * 两个问题：删除过期元素效率低，无法直接删除人一元素，只能删除堆顶元素（最大值）
  * 获取最大值的时间复杂度是o1,但是我们需要不断更新维护，复杂度会增加
  * 删除操作时Ologk, 整体复杂度是O nlogk
* ![Screenshot 2025-03-11 at 13 24 50](https://github.com/user-attachments/assets/0c7a11b9-339d-4342-b050-c1a06f6aeb86)
* ![Screenshot 2025-03-11 at 13 25 05](https://github.com/user-attachments/assets/30fbbd2b-ea56-4083-8360-726154aa93e0)


### 会超时的暴力解法：一个一个遍历
* 主要是用来熟悉arraylist到array的操作转换
* 暴力解法：n*k
```java
import java.util.Arrays;
class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        ArrayList<Integer> res = new ArrayList<>();
        for (int i =0; i <= nums.length - k; i++){
            //这样写比temp ={nums[i], nums[i+1], nums[i+2]};要合适，因为硬编码无法处理大小不同的k，而且可以处理越界问题
            int[] temp =Arrays.copyOfRange(nums, i, i + k);
            //max()返回是optionalint而不是int
            int resInt = Arrays.stream(temp).max().getAsInt();
            res.add(resInt);
        }
        //这里直接toArray()会返回object【】而不是int【】，因为arraylist使用包装类Integer，我们需要1）将arraylist转换为流，然后integer转换为int，最后int转换为int[]数组
        return res.stream().mapToInt(i->i).toArray();
    }
}
```
### 自定义单调队列解法
* **二刷重新看，无法自己独立写出来**
* 相对于暴力解法，单调队列能高效维护滑动窗口的最大值（如果后加进来的比前面加入队列的大，就删除前面的），减少了不必要的比较和遍历，不用每个窗口都遍历和比较一遍
* 暴力解法有大量重复计算，即使只移动了一个位置，还是需要遍历整个窗口
* 单调队列让每个元素仅出队和入队一次，实现O1复杂度地维护窗口最大值
* 每个元素最多进出 deque 一次：
* 入队时：最多 O(1) 次操作（入队或移除较小元素）。
* 出队时：最多 O(1) 次操作（窗口滑动时移除元素）。
* 窗口移动 N-K+1 次，每次 O(1) → 总共 O(N)。
* ![Screenshot 2025-03-11 at 15 27 15](https://github.com/user-attachments/assets/c79eab17-85cd-4237-94a4-c39e1414bfeb)
```java
class MyQueue{
    Deque<Integer> deque = new LinkedList<>();

    //弹出元素时比较要弹出的数值是否等于队列出口处的数值，如果相等则弹出
    //同时判断队列当前是否为空，如果是空的就不弹出
    void poll (int val){
        if(!deque.isEmpty() && val == deque.peek()){
            deque.poll();//弹出队头数字
        }
    }

    //添加元素的时候，要保证元素单调递减
    //如果要添加的元素大于入口处的元素，就将入口处元素（队尾）弹出
    //比如，队里有3,1，现在2要入队，2比1大，弹出1
    void add (int val){
        //这里用while是因为可能队伍里是1,1,3
        //弹出最后一个1以后得到1，3
        //还得继续弹出，直到3在最左边
        while (!deque.isEmpty() && val > deque.getLast()){
            deque.removeLast();//弹出队尾元素
        }
        deque.add(val);
    }
    //队列顶端始（队头）终为最大值
    int peek(){
        return deque.peek();
    }
}
class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums.length ==1){
            return nums;
        }
        //计算结果数组需要多少个占位
        int len = nums.length -k +1;
        //存放结果
        int [] res = new int[len];
        //num指向res里需要填充的位置
        int num =0;
        MyQueue myQueue = new MyQueue();
        //第一个大小为k的窗口需要初始化，找到其中最大值，形成单调递减的序列
        for (int i=0; i<k;i++){
            myQueue.add(nums[i]);
        }
        //这里的意思就是res[num] = myQueue.peek(), num++，存第一个窗口的最大值进入res
        res[num++] = myQueue.peek();
        //后续的窗口：在现有窗口的基础上，移除最左侧元素，并加入一个新元素，保持窗口大小为k
        //i是右边的边界
        for (int i=k; i<nums.length; i++){
            myQueue.poll(nums[i-k]);
            myQueue.add(nums[i]);
            res[num++] = myQueue.peek();
        }
        return res;


    }
}
```
## 347. Top K Frequent Elements
* **二刷的时候看，这次只是跟着gpt写了一遍，捋顺了逻辑，对堆的操作还是不熟悉**
* https://leetcode.com/problems/top-k-frequent-elements/description/
* 文章：https://programmercarl.com/0347.%E5%89%8DK%E4%B8%AA%E9%AB%98%E9%A2%91%E5%85%83%E7%B4%A0.html
* 视频： https://www.bilibili.com/video/BV1Xg41167Lz?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* map用来储存key-frequency
* 暴力解法，每个元素遍历一遍
* 堆每次加入一个元素就要pop一个元素，整理顺序的时间复杂度是log k（因为堆维护k个元素）
* 用大顶堆max heap和小顶堆min heap（二叉树，大顶堆即根节点root>children branch, 小顶堆即children branch是最大的，root是比较小的）
* 但是堆的pop是从堆顶（root），所以用大顶堆容易把值比较大的数字先pop出去，但是我们想要维护的是数值更大的那些元素
* 所以用小顶堆更合适
* 堆用value来组织，最后输出key（数值），value是频率
### 暴力排序
* 统计每个元素出现的次数，用 HashMap<Integer, Integer> 记录每个数的频率。
* 把所有元素按出现次数排序，排序后取前 k 个高频元素
```java
class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        //暴力遍历
        //制造map统计频率,记得hashmap是无序的
        Map<Integer, Integer> countMap = new HashMap<>();
        for (int num: nums){
            countMap.put(num, countMap.getOrDefault(num,0)+1);
        }

        //转换为list并排序
        //list中的每一个元素都是一个map.entry，biaoshi key-value组合，每一个entry可以进行entry.getKey()和entry.getValue()的操作
        List<Map.Entry<Integer,Integer>> list = new ArrayList<>(countMap.entrySet());
        //lambda表达式对list进行按value降序排序
        //如果 b 的 value 比 a 大，就返回 正数，表示 b 应该排在 a 前面（即降序）。
        list.sort((a,b) -> b.getValue() - a.getValue());

        //取前k个
        int[] res = new int[k];
        for (int i=0; i<k; i++){
            res[i] = list.get(i).getKey();
        }
        return res;
    }
        
}
```
* ![Screenshot 2025-03-11 at 17 31 27](https://github.com/user-attachments/assets/c63eff1f-3d4b-4ee1-9cc4-5fd6f38ae2a4)

### 使用小顶堆
* 统计频率（同上）。
* 用最小堆（PriorityQueue）存储 k 个频率最高的元素：
* 堆的大小始终保持为 k，每次遇到更大频率的元素，就弹出堆顶的最小元素。
* 最终堆中剩下的就是前 k 个频率最高的元素。
* 
```java
class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        //priority queue min heap
        Map<Integer, Integer> countMap = new HashMap<>();
        for (int num:nums){
            countMap.put(num, countMap.getOrDefault(num,0) +1);
        }

        //小顶堆，按顺序排序
        //priority queue是内部用heap来管理元素顺序的，最小值在堆顶
        //Comparator.comparingInt(Map.Entry::getValue)比较器，用来按照value生序排序
        PriorityQueue<Map.Entry<Integer,Integer>> minHeap = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));

        //维护大小为k的小顶堆
        for (Map.Entry<Integer,Integer> entry: countMap.entrySet()){
            minHeap.offer(entry);//把元素加入到堆顶然后自动调整顺序
            if (minHeap.size()>k){
                minHeap.poll();//把最小的value从堆顶弹出去
            }
        }

        //取出堆中的前k个元素
        //记得min heap会优先弹出较小的元素，所以要倒叙加入res数组
        int[] res = new int[k];
        for (int i=k-1; i>=0; i--){
            res[i] = minHeap.poll().getKey();
        }

        return res;
    }
}
```
* ![Screenshot 2025-03-11 at 17 30 52](https://github.com/user-attachments/assets/8ea43238-999c-4d51-aa7f-d53a2c4baa9c)
* ![Screenshot 2025-03-11 at 17 33 10](https://github.com/user-attachments/assets/c02e0e8f-5e92-4967-acbb-87990fca8094)

## 代码随想录总结
* https://programmercarl.com/%E6%A0%88%E4%B8%8E%E9%98%9F%E5%88%97%E6%80%BB%E7%BB%93.html
