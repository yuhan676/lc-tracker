## 42. Trapping Rain Water
* https://leetcode.com/problems/trapping-rain-water/description/
* 文章：https://programmercarl.com/0042.%E6%8E%A5%E9%9B%A8%E6%B0%B4.html#%E6%80%9D%E8%B7%AF
* 视频：https://www.bilibili.com/video/BV1uD4y1u75P?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
### 暴力解法：计算每列接住的雨水，高度为左右最高的高度，取较小的那个一个
* 时间复杂度为O(n^2),空间复杂度为O(1)
```java
class Solution {
    public int trap(int[] height) {
        //计数器
        int sum =0;
        for (int i = 0; i<height.length;i++){
            //第一列和最后一列都接不了
            if(i==0 ||i==height.length-1) continue;
            //从i开始往左往右找最高的height
            int rHeight = 0;
            int lHeight = 0;
            for (int r=i+1; r<height.length;r++){
                if (height[r]>rHeight){rHeight=height[r];}
            }
            for (int l=i-1;l>=0;l--){
                if(height[l]>lHeight){lHeight=height[l];}
            }
            int h=Math.min(lHeight,rHeight) - height[i];
            //高度为左右高度比较小的那个，宽度为1，所以如果高度>0的话直接sum +=h
            if (h>0) sum += h;
        }
        return sum;
    }
}
```
### 优化以后的双指针
* 用两个数组储存每个位置上的maxleft和maxright
* 遍历三次
```java
class Solution {
    public int trap(int[] height) {
        int n = height.length;
        if (n<=2) return 0;
        int sum = 0;
        //用两个数组来记录每个位置的maxleft和maxright
        int[] maxLeft = new int[n];
        int[] maxRight = new int[n];
        //初始化两边的数值
        maxLeft[0] = height[0];
        maxRight[n-1] = height[n-1];
        //从左到右照maxleft，从右到左照maxright
        for (int i=1; i<n;i++){
            maxLeft[i] = Math.max(height[i],maxLeft[i-1]);
        }
        for (int j=n-2;j>=0;j--){
            maxRight[j] = Math.max(height[j],maxRight[j+1]);
        }
        //单独求每一列的雨水
        for (int i =1;i<n-1;i++){
            int minHeight = Math.min(maxLeft[i],maxRight[i]) - height[i];
            if (minHeight>0) sum+= minHeight;
        }
        return sum;
    }
}
```
### 空间优化成0(1)的双指针
* 同时进行从左到右和从右到左的遍历
* bottom如果maxLeft比较小，那bottom就是height[left]，不然bottom就是maxright
```
class Solution {
    public int trap(int[] height) {
        if (height.length <= 2) {
            return 0;
        }
        // 从两边向中间寻找最值
        int maxLeft = height[0], maxRight = height[height.length - 1];
        int l = 1, r = height.length - 2;
        int res = 0;
        while (l <= r) {
            // 不确定上一轮是左边移动还是右边移动，所以两边都需更新最值
            maxLeft = Math.max(maxLeft, height[l]);
            maxRight = Math.max(maxRight, height[r]);
            // 最值较小的一边所能装的水量已定，所以移动较小的一边。
            if (maxLeft < maxRight) {
                res += maxLeft - height[l ++];
            } else {
                res += maxRight - height[r --];
            }
        }
        return res;
    }
}
```
### 单调栈解法
* 按照行的方向来计算雨水
* ![image](https://github.com/user-attachments/assets/25cb4b2b-5c00-4b5c-8589-4958ac523c29)
* 求右边第一个比当前大的元素，那就是单调递增栈
* 但是我们也需要知道左边第一个比当前元素大的元素，才能得到雨水面
* 我们对比当前遍历元素和栈口元素，假如当前元素大于栈口元素，就可以收割结果了（当前元素直接就是栈口元素右边最近的更大的元素）
* 但是我们还需要知道，栈口元素左边第一个比它高的元素是什么！它只能在栈里边，因为单调递增所以栈口下面的所有元素都比栈口元素大
* 这个情况下，底部柱子就是栈口元素，左边柱子就是栈口下面的第一个元素（两者值不同），右边珠子就是当前遍历元素
* ![image](https://github.com/user-attachments/assets/619ff2c3-0070-4838-957c-5faab197764b)
* 所以一共三种情况，比较栈顶元素和当前元素
* ![Screenshot 2025-04-19 at 21 32 39](https://github.com/user-attachments/assets/5efffa9e-40d6-4366-b993-2b42328c00da)
* 60，下标0直接入栈（第一个元素不能接水），栈里存放下标
* 遍历到20，20<60,入栈符合单调递增
* 遍历到下一个20，现在栈口和栈顶值相同，是要弹出前一个20假如新的20呢，还是直接把新的20入栈呢？都可以，不过思路不一样。这里我们先不弹出，直接加入栈
* 遍历到10，符合单调递增，加入栈
* 下标4，到30，当前元素>栈口元素，发现凹槽，这个凹槽底部是10（栈顶），左边是20（栈顶第二个元素），右边是30（当前遍历元素）
```
mid = st.peek();
st.pop();//弹出栈顶元素才能拿到左边的高墙
//高度=（左边最高，右边最高）- 凹槽底部mid的高度
h = Math.min(st.peek(), height[i])-mid
//宽度 = 右边-左边-1
w = height[i] - st.peek()-1
//然后我们已经计算了mid作为底部的雨水高度，就可以看栈中的下一个元素了，是20
//用20作为底部，左边是20，右边是30，取最小值还是20，减去底部高度20，就等于0，说明我们刚才遇到第2个20的时候如果弹出再放入新的20，也不会影响最终结果
//遍历到60，底部还是20，右边是30。h= min(60,30)-20 = 10, w = 4-0-1 = 3
```
```java
class Solution {
    public int trap(int[] height) {
        //用当前元素和栈口元素对比，找到右边
        //左边都存在栈里，因为栈里的元素是单调递增，所以会比栈口大
        //栈口是接雨水的底部
        Stack<Integer> st = new Stack<>();
        //栈中存下标
        st.push(0);
        int sum = 0;
        for (int i =1; i<height.length; i++){
            //如果当前元素大于栈口，就找到凹槽了
            //只有当找到凹槽，我们才能pop up all lower vaues
            while (!st.isEmpty()&& height[i]>height[st.peek()]){
                int bottomheight = height[st.peek()];
                st.pop();
                //这里可能出现pop以后空栈的情况，所以要继续检查st.isEmpty()
                if (!st.isEmpty()){
                    int left = st.peek();
                    int right = i;
                    int h = Math.min(height[left],height[right]) - bottomheight;
                    int w = right - left -1;
                    int waterVol = h*w;
                    if (waterVol>0) sum += waterVol;
                }
            }
            //如果当前元素<=栈口元素,直接加入栈
            st.push(i);
        }
        return sum;
    }
}
```
* ![Screenshot 2025-04-19 at 22 19 32](https://github.com/user-attachments/assets/27d32d3d-7ae5-40bf-9cf1-ca54679bcb47)

## 84. Largest Rectangle in Histogram
* https://leetcode.com/problems/largest-rectangle-in-histogram/description/
* 文章：https://programmercarl.com/0084.%E6%9F%B1%E7%8A%B6%E5%9B%BE%E4%B8%AD%E6%9C%80%E5%A4%A7%E7%9A%84%E7%9F%A9%E5%BD%A2.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 视频：https://www.bilibili.com/video/BV1Ns4y1o7uB?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
### 暴力解法（会超时）
* 走到每个元素往左往右找大于等于当前高度的最大区间作为w
* height已经订好了，就是这个区间最小的值（当前值）
* 得出sum，假如大于当前sum就更新
```java
class Solution {
    public int largestRectangleArea(int[] heights) {
        int sum = 0;
        for (int i =0; i<heights.length;i++){
            int left = i;
            int right = i;
            //保证当前height是最小的，往左边和右边找大于等于当前height的，就能继续expand
            //一旦遇到了比当前小的就得停，比如6->2, 5->1，这样可以保证heights只少有当前高度
            for (; left>=0; left--){
                if (heights[left]<heights[i]) break;
            }
            for (; right<heights.length;right++){
                if (heights[right]<heights[i]) break;
            }
            int h = heights[i];
            int w = right-left-1;
            sum = Math.max(sum, h*w);
        }
        return sum;
    }
}
```
### 双指针法
* 这里跳跃寻找左右边界有点难理解
```java
class Solution {
    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        int[] minLeft = new int[n];
        int[] minRight = new int[n];
        //index0的左边界是-1
        minLeft[0] = -1;
        for (int i=1; i<n;i++){
            //i左边的元素
            int t = i-1;
            //假如heights[i]的左边元素heights[t]大于等于heights[i]，那么我们就一路向左找到最左的t的左边界，把它赋值给i的左边界,这样效率比++快得多
            while (t>=0 && heights[t]>=heights[i]) t = minLeft[t];
            minLeft[i] = t;
        }
        //右边界的最后一个元素应该是最后一个元素的index的下一位
        minRight[n-1] = n;
        //从右往左遍历寻找右边界
        for (int i=n-2; i>=0; i--){
            int t = i+1;
            while (t<n && heights[t] >= heights[i]) t = minRight[t];
            minRight[i] = t;
        }

        int res = 0;
        for (int i=0; i<n; i++){
            int sum = heights[i] *(minRight[i]-minLeft[i]-1);
            res = Math.max(res,sum);
        }

        return res;
    }
}
```
### 单调栈思路
* **没有自己写出来，二刷重温**
* 记得我们的单调栈逻辑吗？单调递减单调栈可以帮我们储存左边第一个比我们矮的元素，对比栈口可以帮我们找到右边第一个比我们矮的元素
* 需要数组扩容，在头和尾都增加一个高度为0的元素
```java
class Solution {
    int largestRectangleArea(int[] heights) {
        Stack<Integer> st = new Stack<Integer>();
        
        // 数组扩容，在头和尾各加入一个元素
        int [] newHeights = new int[heights.length + 2];
        newHeights[0] = 0;
        newHeights[newHeights.length - 1] = 0;
        for (int index = 0; index < heights.length; index++){
            newHeights[index + 1] = heights[index];
        }

        heights = newHeights;
        
        st.push(0);
        int result = 0;
        // 第一个元素已经入栈，从下标1开始
        for (int i = 1; i < heights.length; i++) {
            // 注意heights[i] 是和heights[st.top()] 比较 ，st.top()是下标
            if (heights[i] > heights[st.peek()]) {
                st.push(i);
            } else if (heights[i] == heights[st.peek()]) {
                st.pop(); // 这个可以加，可以不加，效果一样，思路不同
                st.push(i);
            } else {
                while (heights[i] < heights[st.peek()]) { // 注意是while
                    int mid = st.peek();
                    st.pop();
                    int left = st.peek();
                    int right = i;
                    int w = right - left - 1;
                    int h = heights[mid];
                    result = Math.max(result, w * h);
                }
                st.push(i);
            }
        }
        return result;
    }
}
```
