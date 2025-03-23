## 39. Combination Sum
* https://leetcode.com/problems/combination-sum/description/
* 文章：https://programmercarl.com/0039.%E7%BB%84%E5%90%88%E6%80%BB%E5%92%8C.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 视频：https://www.bilibili.com/video/BV1KT4y1M7HJ?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 难点：
  * 这道题说是可以使用重复的数字，但是本质并不是从组合变成排列了，依然需要用startindex来保证不会出现排列的结果
  * 比如2，3，5，我们想要target7，[2,5]搜索以后就不该搜索5，2了
  * 怎么样在写代码的时候控制startindex，是个好问题，有点被绕晕
  * 每一个节点的递归树中包含的元素其实是从自己开始到子集结束，但不包括前面的元素
  * ![Screenshot 2025-03-21 at 22 11 26](https://github.com/user-attachments/assets/a09ead99-e2a5-471b-acdb-72ceb0f4152c)

* 递归三部曲
  * 新建全局二维数组res，一维数组path，int sum
  * 参数：candidates，target，startindex（也有人会把sum放这里）
  * 返回值void
  * 终止条件
    * 如果元素和大于target，return
    * 如果等于target，收割结果进res，return
  * 单层搜索逻辑
    * for循环（i=start index, i<collection.length,i++)取每一个元素的过程
      * 首先把元素放入path：path.add(candidate[i])
      * 统计和： sum += candidate[i]
      * 递归：backtracking（candidate,target,startIndex) //还是可以从startindex本身开始，2->2,3,5，在组合问题里这里是i+1
      * 回溯：path.remove(path.size()-1); sum -= candidate[i]
  ### 一开始我的解法： 有bug
  ```java
  class Solution {
    int sum = 0;
    List<List<Integer>> res =new ArrayList<>();
    List<Integer> path = new ArrayList<>();
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        if (candidates.length==0) return res;
        backtracking(candidates, target, 0);
        return res;
    }
    public void backtracking (int[] candidate, int target, int startIndex){
        if (sum==target){
            res.add(new ArrayList<>(path));
            return;
        }
        if (sum > target) return;
        for(int i = startIndex; i<candidate.length;i++){
            path.add(candidate[i]);
            sum += candidate[i];
            backtracking(candidate, target, startIndex);
            path.remove(path.size()-1);
            sum -= candidate[i];
            //没搞懂这里为什么要手动startindex++
            startIndex ++;
        }
    }
}
```
* 其实是因为写错了backtracking的参数，应该是i而不是startindex
* debug以后
```java
class Solution {
    int sum = 0;
    List<List<Integer>> res =new ArrayList<>();
    List<Integer> path = new ArrayList<>();
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        if (candidates.length==0) return res;
        backtracking(candidates, target, 0);
        return res;
    }
    public void backtracking (int[] candidate, int target, int startIndex){
        if (sum==target){
            res.add(new ArrayList<>(path));
            return;
        }
        if (sum > target) return;
        for(int i = startIndex; i<candidate.length;i++){
            path.add(candidate[i]);
            sum += candidate[i];
            backtracking(candidate, target, i);
            path.remove(path.size()-1);
            sum -= candidate[i];
        }
    }
}
```
### for循环剪枝：给candidate排序，检查sum+candidate[i]是否大于target，如果是的话那之后的candidate[i++]都不需要检查了
```java
class Solution {
    int sum = 0;
    List<List<Integer>> res =new ArrayList<>();
    List<Integer> path = new ArrayList<>();
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        if (candidates.length==0) return res;
        //对candidate进行排序方便之后剪枝
        Arrays.sort(candidates);
        backtracking(candidates, target, 0);
        return res;
    }
    public void backtracking (int[] candidate, int target, int startIndex){
        if (sum==target){
            res.add(new ArrayList<>(path));
            return;
        }
        //因为candidate已经排序，如果sum+candidate[i]已经超过target，那之后的i久都没有必要遍历了
        for(int i = startIndex; i<candidate.length && sum + candidate[i]<=target;i++){
            path.add(candidate[i]);
            sum += candidate[i];
            backtracking(candidate, target, i);
            path.remove(path.size()-1);
            sum -= candidate[i];
        }
    }
}
```
* 时间和空间复杂度
* 时间复杂度: O(n * 2^n)，注意这只是复杂度的上界，因为剪枝的存在，真实的时间复杂度远小于此
* 空间复杂度: O(target)

## 40. Combination Sum II
* https://leetcode.com/problems/combination-sum-ii/description/
* 文章：https://programmercarl.com/0040.%E7%BB%84%E5%90%88%E6%80%BB%E5%92%8CII.html#%E5%85%B6%E4%BB%96%E8%AF%AD%E8%A8%80%E7%89%88%E6%9C%AC
* 视频：https://www.bilibili.com/video/BV12V4y1V73A?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 难点：
  * candidates中有重复元素，但是每个元素只能被使用一次，而且结果只能是组合不能是排列
  * 剪枝理解起来有点困难
  * 解题逻辑：首先排序方便for循环迭代时跳过重复元素，允许递归的时候使用重复的元素（递归i+1)，但是for loop同层的时候跳过所有的相同元素，这就意味着每一个起始节点都是unique的
  * ![Screenshot 2025-03-22 at 20 19 51](https://github.com/user-attachments/assets/0c661d72-45e6-4d66-b927-5f3393771f06)
* 代码随想录的题解提供了一个used 数组来记录一个元素之前有没有被用过，但是不用也可以
* 递归三部曲
  * 二维list res，一维list path，sum=0
  * 参数：candidate, target, startIndex
  * 返回值：void
  * 终止条件：sum> target -> return' sum== target->收割结果path，return
  * 单层递归逻辑：
    * 从startindex开始for循环到starindex== candidates.length-1结束
    * ！！剪枝：for循环内不能有重复的元素，因为每一个分支都应该是unique starting number， if（i> strartIndex && candidates[i] == candidates[i-1]) continue
    * 然后就是平常的：path.add(i), sum += i; 递归(i+1); 回溯
```java
class Solution {
    List<List<Integer>> res = new ArrayList<>();
    List<Integer> path = new ArrayList<>();
    int sum = 0;

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        backtrack(candidates, target, 0);
        return res;
    }
    private void backtrack(int[] candidates, int target, int startIndex){
        if (sum > target) return;
        if (sum == target){
            res.add(new ArrayList<>(path));
            return;
        }
        for (int i = startIndex; i <candidates.length; i++){
            //这里注意条件是i>startindex,不是i>0, 因为i>startindex检查的是我们是不是还在同一个for循环里面（即同层），同一层里面我们不应该将重复的数值加入path，但是下一层递归我们是可以的（即backtrack(candidates, target, i+1)的时候，candidates[i+1]可能等于candidates[i])
            //每一次for循环处理的是同样元素为起始点的path，这样就不会出现比如说[125][125]，因为candidates中的第二个1被continue忽略了；排序以后，往后走也不会遇到前面出现过的元素
            if (i >startIndex && candidates[i]==candidates[i-1]){
                continue;
            }
            path.add(candidates[i]);
            sum += candidates[i];
            backtrack(candidates, target, i+1);
            path.remove(path.size()-1);
            sum -= candidates[i];
        }
    }
}
```
### 剪枝
* for循环剪枝逻辑
```java
for (int i = startIndex; i <candidates.length && sum +candidates[i]<=target; i++){
```
## 131. Palindrome Partitioning
* https://leetcode.com/problems/palindrome-partitioning/
* 文章：https://programmercarl.com/0131.%E5%88%86%E5%89%B2%E5%9B%9E%E6%96%87%E4%B8%B2.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 视频：https://www.bilibili.com/video/BV1c54y1e7k6/?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 树形结构：检查叶子节点给出的子串是否符合回文，不符合就return
* ![Screenshot 2025-03-22 at 21 14 09](https://github.com/user-attachments/assets/acf90a86-f3a1-4599-a318-ee787a8b650e)

* 回溯三部曲
  * 全局变量: 1维数组path：存放单一分割方案
  * 全局变量：2维数组res：储存符合条件的path
  * 参数: string, start index (只会向后切割，不能重复切割，startindex作为右边切割线）,stringbuilder sb (因为每一个节点都要新生成一个stringbuilder，就不放在全局变量了）
  * 终止条件
    * 切割到末尾：startindex = string.size()， res内加入path（为什么直接加入？因为判断回文的逻辑放在了单层递归逻辑，path现在已经找到了所有回文子串 ）
  * 单层逻辑：
    * for循环for (i=startindex; i<string.size();i++) 
    * 如何表示切割的子串是一个难点：startindex是切割线，切割的子串就是startindex和i左闭右闭的区间[startindex,i]
    * i不就是startindex吗？不，i在for循环中会不断++，向后移动每向后移动一位就代表一个子串
    * palindrome函数（string, startindex, i)，左闭右闭区间判断是否回文，如果是回文的，就加入到path数组里，不然的话就直接跳过continue（i直接++）
    * 递归逻辑下一层：backtracking(string,i+1)
    * 回溯
  * palindrome写法：双指针，从头和尾遍历
  * 可以用动态规划优化
  ### 代码随想录解法**没有特别搞懂**
  ```java
  class Solution {
    List<List<String>> res = new ArrayList<>();
    List<String> path = new ArrayList<>();
    public List<List<String>> partition(String s) {
        backtracking(s, 0, new StringBuilder());
        return res;
    }

    private void backtracking(String s, int startIndex, StringBuilder sb){
        if (startIndex == s.length()){
            res.add(new ArrayList<>(path));
        }
        for (int i = startIndex; i<s.length();i++){
            sb.append(s.charAt(i));
            if (isPalindrome(sb)){
                path.add(sb.toString());
                //遇到了合适的结果才递归
                backtracking(s,i+1,new StringBuilder());
                //这里撤销意味着这条path的内容已经满了，回溯把path撤回空的状态，好找下一种切割方式切出来path的结果
                path.remove(path.size()-1);
            }
        }

    }

    private boolean isPalindrome(StringBuilder sb){
        for(int i = 0; i<sb.length()/ 2; i++){
            if (sb.charAt(i)!= sb.charAt(sb.length()-1-i)){return false;}
        }
        return true;
    }
}
```
