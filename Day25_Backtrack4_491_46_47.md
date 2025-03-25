## 491. Non-decreasing Subsequences
* https://leetcode.com/problems/non-decreasing-subsequences/description/
* 文章：https://programmercarl.com/0491.%E9%80%92%E5%A2%9E%E5%AD%90%E5%BA%8F%E5%88%97.html
* 视频：https://www.bilibili.com/video/BV1EG4y1h78v/
* 注意这里不能直接sort nums，不然会导致结果出错（假如大的数字排在前面的话）
* 因为要记录所有节点，所以没有所谓的返回情况，只有要求在path大于等于2的时候记录path于res
### 我一开始的想法：有bug
```java
class Solution {
    List<List<Integer>> res = new ArrayList<>();
    List<Integer> path = new ArrayList<>();
    public List<List<Integer>> findSubsequences(int[] nums) {
        backtrack(nums,0);
        return res;
    }
    private void backtrack(int[]nums, int startIndex){
        //这里通过检查path是否已经存在res里进行去重，但是会超时
        if (path.size()>=2 && !res.contains(new ArrayList<>(path))) {res.add(new ArrayList<>(path));}
        for (int i = startIndex; i<nums.length;i++){
            if (!path.isEmpty() && nums[i] < path.get(path.size()-1)){
                continue;
            }
            path.add(nums[i]);
            backtrack(nums, i+1);
            path.remove(path.size()-1);
        }
    }
}
```
* 如果不进行去重，就会出现这种情况
* <img width="702" alt="Screenshot 2025-03-25 at 18 23 43" src="https://github.com/user-attachments/assets/82ab7d83-a19f-416b-ada6-439361baf007" />
* 一直以来我们的去重方式都是：给nums排序，然后if(nums[i]==nums[i-1]){continue}，但是这次我们不能给nums排序了，该怎么办呢
* 从这个逻辑可以看出来，统一层for循环中不能使用重复的元素
* ![image](https://github.com/user-attachments/assets/79b5ce95-4d4f-44e4-a4e1-1571218b03c1)
* 那我们有两种处理方法
1. 每一层for循环之前（每一次递归以后）新建一个hashset用来储存元素，检查是否有使用过，如果有的话就跳过
2. 映射1:也可以用int[201] usednum (因为题目说-100 <= nums[i] <= 100）
3. 映射2:也可以用hashmap，把没存过的getOrDefault成（0）然后存的时候+1
### hashset去重解法
* 时间复杂度:
* HashSet 的 add() 和 contains() 操作是 O(1) 均摊时间复杂度，但由于递归树的规模，整体复杂度接近 O(2ⁿ)。
* 空间复杂度:
* O(n) 递归调用栈 + O(n) 递归过程中创建的 HashSet（每一层 for 循环新建一个 HashSet）。
* 总空间复杂度 ≈ O(n²)。
```java
class Solution {
    List<List<Integer>> res = new ArrayList<>();
    List<Integer> path = new ArrayList<>();
    public List<List<Integer>> findSubsequences(int[] nums) {
        backtrack(nums,0);
        return res;
    }
    private void backtrack(int[]nums, int startIndex){
        if (path.size()>=2) {res.add(new ArrayList<>(path));}
        //每次进入一个新的backtrack递归（新一层），就另起一个hashset来储存不重复的元素
        HashSet<Integer> hs = new HashSet<>();
        for (int i = startIndex; i<nums.length;i++){
            if (!path.isEmpty() && nums[i] < path.get(path.size()-1)|| hs.contains(nums[i])){
                continue;
            }
            //hs只用于每个for循环，所以不用hs.remove
            hs.add(nums[i]);
            path.add(nums[i]);
            backtrack(nums, i+1);
            path.remove(path.size()-1);
        }
    }
}
```
### 数组usednum去重
* 数组 used 的索引访问比 HashSet 查找更快（O(1) vs. O(log n)）。
* 避免负数索引问题：由于 nums[i] 的取值范围是 [-100, 100]，我们将 nums[i] 平移 +100 变为索引范围 [0, 200]，这样可以直接用数组存储。
* ![Screenshot 2025-03-25 at 18 43 55](https://github.com/user-attachments/assets/4fe9d380-a433-4d76-90e0-b43cb5c6a40c)
* 时间复杂度:
* 由于 used[nums[i] + 100] = 1 也是 O(1)，查询 used 也是 O(1)，所以不影响原有的 O(2ⁿ) 复杂度。
* 空间复杂度:
* O(n) 递归调用栈 + O(1) 额外空间（数组 used[201] 大小是 固定的）。
* 总空间复杂度 ≈ O(n)（最优）。



```java
class Solution {
    List<List<Integer>> res = new ArrayList<>();
    List<Integer> path = new ArrayList<>();
    public List<List<Integer>> findSubsequences(int[] nums) {
        backtrack(nums,0);
        return res;
    }
    private void backtrack(int[]nums, int startIndex){
        if (path.size()>=2) {res.add(new ArrayList<>(path));}
        //[-100,100]一共有201个数字
        int[] used = new int[201];
        for (int i = startIndex; i<nums.length;i++){
            //这里检查储存的那个数字是否已经被储存了
            if (!path.isEmpty() && nums[i] < path.get(path.size()-1)|| used[nums[i]+100]==1){
                continue;
            }
            used[nums[i]+100]=1;
            path.add(nums[i]);
            backtrack(nums, i+1);
            path.remove(path.size()-1);
        }
    }
}
```
### hashmap去重
```java
class Solution {
    List<List<Integer>> res = new ArrayList<>();
    List<Integer> path = new ArrayList<>();
    public List<List<Integer>> findSubsequences(int[] nums) {
        backtrack(nums,0);
        return res;
    }
    private void backtrack(int[]nums, int startIndex){
        if (path.size()>=2) {res.add(new ArrayList<>(path));}
        HashMap<Integer,Integer> map = new HashMap<>();
        for (int i = startIndex; i<nums.length;i++){
            //记住getordefault会return一个东西
            if (!path.isEmpty() && nums[i] < path.get(path.size()-1)|| map.getOrDefault(nums[i],0)>=1){
                continue;
            }
            map.put(nums[i],map.getOrDefault(nums[i],0)+1);
            path.add(nums[i]);
            backtrack(nums, i+1);
            path.remove(path.size()-1);
        }
    }
}
```
* 时间复杂度:
* HashMap 的 put() 和 get() 操作 理论上是 O(1)，但由于哈希冲突，最坏情况可能退化到 O(n)。
* 递归树规模仍是 O(2ⁿ)，所以整体时间复杂度和 HashSet 方案相似，为 O(2ⁿ)。
* 空间复杂度:
* O(n) 递归调用栈 + O(n) HashMap 的存储（每一层递归创建一个 HashMap）。
* 总空间复杂度 ≈ O(n²)（与 HashSet 类似）。
* ![Screenshot 2025-03-25 at 18 51 02](https://github.com/user-attachments/assets/cd5d0c26-cd8c-47c7-8863-27f92c16a813)
## 46. Permutations
* https://leetcode.com/problems/permutations/description/
* 文章：https://programmercarl.com/0046.%E5%85%A8%E6%8E%92%E5%88%97.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 视频：https://www.bilibili.com/video/BV19v4y1S79W?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 做完组合问题我们终于来到了排列问题
* 排列问题不需要startindex，因为12加入res了以后21也需要加入res，不需要keep track of where the start index is, always start from nums[0]
* 收割叶子节点
* 遍历的时候，for循环要去重掉已经加入了path的元素
### 使用path.contain()去重
```java
class Solution {
    List<List<Integer>> res = new ArrayList<>();
    List<Integer> path = new ArrayList<>();

    public List<List<Integer>> permute(int[] nums) {
        backtrack(nums);
        return res;
    }
    private void backtrack(int[] nums){
        //本质上是修剪叶子节点，所以等path和nums一样长的时候在收割
        if (path.size()==nums.length){
            res.add(new ArrayList<>(path));
            return;
        }
        //for循环控制path开头的第一个数字
        //从结果的[1,2,3]到[1,3,2],是已经回溯掉了后两个元素，然后i++从2到3加入3，再开始backtrack，查重去除1，3得到的加入2
        for (int i = 0; i<nums.length; i++){
            //这里的去重至关重要，我们不想要3，1，2，3类似的结果
            if (path.contains(nums[i])){continue;}
            path.add(nums[i]);
            backtrack(nums);
            path.remove(path.size()-1);
        }
    }
}
```
### 使用数组去重
* 一开始我的想法❌❌❌：每层for循环去重,所以在for循环开始之前初始化数组
* -10 <= nums[i] <= 10
* 平移到[0,20]， 创建 int[21] used
* 只按单层中的元素进行去重，这样会miss掉因为递归加进path的重复项
* ✅正确的解法：用boolean[]
* ![image](https://github.com/user-attachments/assets/85acc88b-5419-4173-a94b-f37ce146461a)
* 使用全局变量used的逻辑：深度搜索意味着递归到底返回以后会回到没运行完的for循环位置中，直到循环完毕才返回
```java
class Solution {
    List<List<Integer>> res = new ArrayList<>();
    List<Integer> path = new ArrayList<>();
    //深度搜索使用全局used数组记录nums中i位置的数值是否已经被用过（因为nums中只有unique number）
    boolean [] used;

    public List<List<Integer>> permute(int[] nums) {
        //在这里初始化used，因为它的大小必须与nums相等
        used = new boolean[nums.length];
        backtrack(nums);
        return res;
    }
    private void backtrack(int[] nums){
        if (path.size()==nums.length){
            res.add(new ArrayList<>(path));
            return;
        }
        for (int i = 0; i<nums.length; i++){
            if (used[i] == true) {continue;}
            //标记i位置为使用过
            used[i]= true;
            path.add(nums[i]);
            backtrack(nums);
            //回溯撤回i使用过的标签
            used[i] = false;
            path.remove(path.size()-1);
        }
    }
}
```
* 时间/空间复杂度分析
* ![Screenshot 2025-03-25 at 19 53 12](https://github.com/user-attachments/assets/a47fb181-8596-4d90-adab-21f53c54f721)
* ![Screenshot 2025-03-25 at 20 05 34](https://github.com/user-attachments/assets/3e56dd29-5f79-4ca5-bb5d-1acef2762855)
* ![Screenshot 2025-03-25 at 20 05 45](https://github.com/user-attachments/assets/16d49d31-58c2-4e48-9d67-fc3feebf19b5)
## 47. Permutations II
* https://leetcode.com/problems/permutations-ii/description/
* 文章：https://programmercarl.com/0047.%E5%85%A8%E6%8E%92%E5%88%97II.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 视频：https://www.bilibili.com/video/BV1R84y1i7Tm?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
### 我一开始的想法：在46题的基础上排序，加上每一层的数组的去重
* 基础想法没问题（层序去重），但是去重逻辑出了问题
* 问题：把层序去重和递归去重和在一了，正确的方式见代码随想录解法
```java
class Solution {
    List<List<Integer>> res = new ArrayList<>();
    List<Integer> path = new ArrayList<>();
    boolean [] used;


    public List<List<Integer>> permuteUnique(int[] nums) {
        used = new boolean[nums.length];
        Arrays.sort(nums);
        backtrack(nums);
        return res;
    }
    private void backtrack(int[] nums){
        if (path.size()==nums.length){
            res.add(new ArrayList(path));
            return;
        }
        for (int i = 0; i<nums.length; i++){
            if (i> 0 && nums[i] == nums[i-1] || used[i]){
                continue;
            }
            path.add(nums[i]);
            used[i] = true;
            backtrack(nums);
            path.remove(path.size()-1);
            used[i] = false;
        }
    }
}
```
### 和m想出来的方法：res用hashset储存，最后转变成二维list
*缺点：太慢了
```java
class Solution {
    HashSet<List<Integer>> res = new HashSet<>();
    List<Integer> path = new ArrayList<>();
    boolean [] used;


    public List<List<Integer>> permuteUnique(int[] nums) {
        used = new boolean[nums.length];
        Arrays.sort(nums);
        backtrack(nums);
        return new ArrayList<>(res);
    }
    private void backtrack(int[] nums){
        if (path.size()==nums.length){
            res.add(new ArrayList(path));
            return;
        }
        for (int i = 0; i<nums.length; i++){
            if (used[i]){
                continue;
            }
            path.add(nums[i]);
            used[i] = true;
            backtrack(nums);
            path.remove(path.size()-1);
            used[i] = false;
        }
    }
}
```
* 代码随想录思路
* input = 1,1,2，46的思路去求会得到两个1，1，2
* 排列的去重：used数组+数层去重
  * 还是需要used数组来避免重复使用同位置的元素
  * 数层去重：排序，让相同数字挨在一起，便于去重
  * 树枝上（不同层）可以重复取同样的值但是不同位置的元素
* used[i-1] == false的逻辑有一点难理解，但是看了下图就会发现去重是在第一个数字被mark used之前就发生了的
*![image](https://github.com/user-attachments/assets/a69a67bf-b83f-4b01-a7fa-ccafed2b7715)
```java
class Solution {
    List<List<Integer>> res = new ArrayList<>();
    List<Integer> path = new ArrayList<>();
    boolean [] used;


    public List<List<Integer>> permuteUnique(int[] nums) {
        used = new boolean[nums.length];
        Arrays.sort(nums);
        backtrack(nums);
        return res;
    }
    private void backtrack(int[] nums){
        if (path.size()==nums.length){
            res.add(new ArrayList(path));
            return;
        }
        for (int i = 0; i<nums.length; i++){
            //层序去重：注意去重的时候重复值的第一个还没用上，所以是used[i-1]
            if (i>0 && nums[i] == nums[i-1] && !used[i-1]){
                continue;
            }
            //递归去重，确认同样位置的元素不被过多使用
            if (used[i]){continue;}
            path.add(nums[i]);
            used[i] = true;
            backtrack(nums);
            path.remove(path.size()-1);
            used[i] = false;
        }
    }
}
```
## 一刷保留题目，可跳过
### 332.重新安排行程（可跳过） 
本题很难，一刷的录友刷起来 比较费力，可以留给二刷的时候再去解决。
本题没有录制视频，当初录视频是按照 《代码随想录》出版的目录来的，当时没有这道题所以就没有录制。
https://programmercarl.com/0332.%E9%87%8D%E6%96%B0%E5%AE%89%E6%8E%92%E8%A1%8C%E7%A8%8B.html  

### 51.N皇后（适当跳过） 
N皇后这道题目还是很经典的，一刷的录友们建议看看视频了解了解大体思路 就可以 （如果没时间本次就直接跳过） ，先有个印象，二刷的时候重点解决。  

https://programmercarl.com/0051.N%E7%9A%87%E5%90%8E.html   
视频讲解：https://www.bilibili.com/video/BV1Rd4y1c7Bq 

### 37.解数独（适当跳过）  
同样，一刷的录友们建议看看视频了解了解大体思路（如果没时间本次就直接跳过），先有个印象，二刷的时候重点解决。 
。
https://programmercarl.com/0037.%E8%A7%A3%E6%95%B0%E7%8B%AC.html   
视频讲解：https://www.bilibili.com/video/BV1TW4y1471V

总结  
刷了这么多回溯算法的题目，可以做一做总结了！
https://programmercarl.com/%E5%9B%9E%E6%BA%AF%E6%80%BB%E7%BB%93.html 


