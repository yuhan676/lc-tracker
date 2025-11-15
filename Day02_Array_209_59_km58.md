# Array Day 2: Sliding window, Spiral, Prefix Sum
## 209. Minimum Size Subarray Sum
https://leetcode.com/problems/minimum-size-subarray-sum/description/
### Initial attempt: Brute Force Solution
* 思路：两个for 循环遍历所有可能的子数组
* Time Complexity: O(n^2)
* Space Complexity: O(1) (no new array is being created)
```java
class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        int res = 0; // Initalise it to 0 will cause res to always be evaluated to 0. use a big value, e.g. Integer.MAX_VALUE
        for (int i = 0; i < nums.length; i++){
            int sum = nums[i];
            for (int j = 0; j < nums.length; j++){// here instead of initialising j to 0, do j = i, as j==i is also a valid window
                sum += nums[j];
                if (sum >= target){
                    res = Math.min (res, (j-i)+1);
                    j = i + 1; //There is no need to add this line as the next iteration of the i for loop will handle it
                    break;
                }
            }
        }
        return res;

    }
}
```
### After fixing the bugs
```java
class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i ++){
            int sum = 0;
            for (int j = i; j < nums.length; j++){
                sum += nums[j];
                if (sum >= target){
                    res = Math.min(res, j - i +1);
                }
            }
        }
        if (res == Integer.MAX_VALUE) {
            return 0;
        }else{
            return res;
        }
    }
}
```
### Sliding Window approach
* Move the righter end of the window, and let the left catch up with it
* the index right end of the window leads the for loop iteration
* Time Complexity: O(n) （Only 1 for loop) 
* Space Complexity: O(1) 
**Attempt**
```java
class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        int sum = 0;
        int res = Integer.MAX_VALUE;
        for (int j = 0; j < nums.length; j++){
            int i = 0;// 不要在这里initialise left或者i，不然每次新循环i都在0，i需要不停的维护变动
            sum += nums[j];
            if (sum >= target){
                res = Math.min(res, j - i + 1);
                i ++;//这里虽然i++了但在这之前sum需要减去nums[i]的值，所以需要sum -= nums[i]
            }
        }
        if (res == Integer.MAX_VALUE){
            return 0;
        }else{
            return res;
        }
    }
}
```
**After fixing the bugs**
```java
class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        int sum = 0, res = Integer.MAX_VALUE, i = 0;//简化initialiser
        for (int j = 0; j < nums.length; j++){
            sum += nums[j];
            while (sum >= target){//换成whileloop更合适
                res = Math.min(res, j - i + 1);
                sum -= nums[i];
                i++;
            }
        }
        return (res == Integer.MAX_VALUE) ? 0:res; 
    }
}
```

** 二刷： 容易搞混什么时候increment i
```java
class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        int minL = Integer.MAX_VALUE;
        int i = 0;
        int sum = 0;
        for (int j = 0; j< nums.length;j++){
            sum += nums[j];
            int len = j-i + 1;
            while (i <= j && sum >= target){
                minL = Math.min(minL, j - i + 1);
                i++;//这里记得不能先increment i，不然容易把已经increment的i index的内容剪掉
                sum -= nums[i];
            }
        }
        return minL == Integer.MAX_VALUE ? 0: minL;
    }
}
```
** 修bug
```java
class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        int minL = Integer.MAX_VALUE;
        int i = 0;
        int sum = 0;
        for (int j = 0; j< nums.length;j++){
            sum += nums[j];
            //这里没必要有len calculation，因为我们在验证了sum 》= target了以后才能确保这个窗口合适
            int len = j-i + 1;
            while (sum >= target){
                minL = Math.min(minL, j - i + 1);
                //先计算剪去了i以后sum有多少，再移动i
                sum -= nums[i];
                i++;
            }
        }
        return minL == Integer.MAX_VALUE ? 0: minL;
    }
}
```
## 59. Spiral Matrix II
<img width="974" alt="Screenshot 2025-02-27 at 22 44 49" src="https://github.com/user-attachments/assets/508cdc01-af06-45e6-9ec8-21a8b4246257" />

* 处理左开右闭需要用offset来定义边界
* 如果是n为odd，需要在startX, startY，loop，offset，count更新以后填充最后一个值
*time: O(n^2) (have to visit each index in the 2d array
* space: O(1) (because the array is the output we need so there's no extra space spent)
  
```java
class Solution {
    public int[][] generateMatrix(int n) {
        //需要记住如何用java创建2d数组
        int nums [] [] = new int [n] [n];
        int startX =0, startY = 0, loop = 1;
        //j是指列数，i是指行数
        int i,j;
        int count = 1, offset = 1;
        while (loop <= n/2) {
            //顺时针上边
            for (j = startY;j < n-offset;j++){
                nums[startX][j] = count++;
            }
            //顺时针右边
            for (i=startX; i < n-offset; i++){
                nums[i][j] = count++;
            }
            //底部
            for (; j > startY; j--){//这里掠过j的initialisation因为我们已经知道j是什么了
                nums[i][j] = count++;
            }
            //左边，>startX是因为左开右闭
            for (; i> startX; i--){
                nums[i][j] = count ++;
            }
            startX ++;
            startY ++;
            loop ++;
            offset ++;
        }
        //如果n是基数，填充最后一个中间的值(也是最大值)
        if (n%2 == 1){
            nums[startX][startY] = count;
        }
        return nums;
    }
}
```

## kamacode 58 区间和
* 代码网站：https://kamacoder.com/problempage.php?pid=1070
* 讲解：https://www.programmercarl.com/kamacoder/0058.%E5%8C%BA%E9%97%B4%E5%92%8C.html#%E5%85%B6%E4%BB%96%E8%AF%AD%E8%A8%80%E7%89%88%E6%9C%AC

Time & Space Complexity
<img width="752" alt="Screenshot 2025-02-28 at 18 53 55" src="https://github.com/user-attachments/assets/96df8aea-4f2e-498e-88ac-3ff905e90a2a" />
* q= 查询次数（e.g. “0 1” 为一次查询 “1 3” 为另一次
* n = 输入的数组总长度

### Brute force solution
* usage of **java.util.Scanner**
* pay attention to how Scanners are created and closed
```java
import java.util.Scanner;

class Main{
    public static void main(String[ ] args){
        Scanner scanner = new Scanner(System.in);//创建new scanner用来接受system input

        // 读取数组长度
        int n = scanner.nextInt();
        // 根据第一个input n创建一个长度为n的空数组
        int[] array = new int[n];

        //读取数组元素
        for (int i=0; i<n; i++){
            array[i] = scanner.nextInt();
        }

        //查询并计算区间和
        while (scanner.hasNextInt()){//java 的input可以被回车或者空格分开 所以'0 1'会被视为两个nextInt
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            int sum = 0;

            //暴力计算区间和
            for (int i=a; i <=b; i++){
                sum += array[i];
            }
            System.out.println(sum);
        }
        scanner.close();//注意scanner是一个class不是一个实例，开头为小写
    }
}
```

### 前缀和解法 Prefix sum/ Cumulative sum
<img width="760" alt="Screenshot 2025-02-28 at 17 51 04" src="https://github.com/user-attachments/assets/463699d9-26ec-4e36-baa0-17d5b08c5a49" />

```java
import java.util.Scanner;

class Main{
    public static void main(String[ ] args){
        Scanner scanner = new Scanner(System.in);//创建new scanner用来接受system input

        // 读取数组长度
        int n = scanner.nextInt();
        // 根据第一个input n创建一个长度为n的空数组
        int[] array = new int[n];
        // create a new array of the same length, but this time each will hodl the cumulative sum at each index
        int [] acc = new int[n];

        //读取数组元素
        for (int i=0; i<n; i++){
            array[i] = scanner.nextInt();
            // 一开始的想法” acc[i] += scanner.nextInt();❌这样是不行的，nextInt 不能两次用来读取同一个数字，会出现java.util.NoSuchElementException

            //✅以下才是正确的填充acc的方式
            if (i ==0){
                acc[i] = array[i];
            }else{
                acc[i] = acc[i - 1] + array[i];
            }
        }

        while (scanner.hasNextInt()){
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            int res;
            if(a == 0){
                res = acc[b];
                }
            else{
                res = acc[b] - acc[a-1];
            }
            
            System.out.println(res);
        }
        scanner.close();//注意scanner是一个我们之前创造的实例，开头为小写
    }
}
```
