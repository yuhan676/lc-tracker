# 回溯
## 理论基础
* 视频：https://www.bilibili.com/video/BV1cy4y167mM/?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5
* 文章：https://programmercarl.com/%E5%9B%9E%E6%BA%AF%E7%AE%97%E6%B3%95%E7%90%86%E8%AE%BA%E5%9F%BA%E7%A1%80.html#%E7%90%86%E8%AE%BA%E5%9F%BA%E7%A1%80
* 回溯是一种纯暴力搜索，但是可以解决for循环解决不了的问题
### 解决的问题
  * 组合：给定一个数组，返回大小为2的子数组。**组合是不强调顺序的，12，21算是一种组合**
  * 切割：给定一个字符串，有几种切割的方式？或加特殊条件：有几种切割方式让子串都是回文字符串
  * 子集：给定1234，子集可能是1，12，123，2，23....用for循环枚举会很困难
  * 排列：**排列限制顺序，12，21是两种排列**
  * 棋盘：n皇后，解数独
### 如何理解回溯法
* 为了清晰的了解，抽象成一个树形结构（n叉树）会让人更容易理解，不容易绕进去
* 回溯就是递归的过程：递归一定有终止，并且会一层一层向上返回
* n叉树的宽度就是回溯法中处理的集合的大小（一般用for来遍历所有子集中的元素）/平行
* 深度就是递归的深度（用递归处理）/嵌套
![Screenshot 2025-03-20 at 16 55 15](https://github.com/user-attachments/assets/be6fce3d-9cfb-4b11-8ba9-59fc2c3a8d37)
![Screenshot 2025-03-20 at 17 05 17](https://github.com/user-attachments/assets/64a76479-8fe2-4ef3-866a-8ebd1a099a0a)

* 定义回溯函数： 模版
  * 名称backtracking
  * 返回值：void
  * 参数：一般情况下是比较多的，按题而定
  * 终止条件：if (终止条件），{收集结果，放进结果集，return}
  * 单层搜索的逻辑：
    * for (集合的元素集）{处理节点; 递归函数;回溯操作（撤销处理节点的情况）}//遍历集合里的每一个元素
    * 为什么要撤销节点呢？比如我们找到了组合12，我们要做回溯的操作把2弹出，把3加进来，才能得到13的新组合
    * for循环处理完，return
![Screenshot 2025-03-20 at 17 04 58](https://github.com/user-attachments/assets/c3555b71-1cb6-4615-a8af-ab96950e401b)

## 77. Combinations
* https://leetcode.com/problems/combinations/description/
* 文章：https://programmercarl.com/0077.%E7%BB%84%E5%90%88.html#%E6%80%9D%E8%B7%AF
* 视频：https://www.bilibili.com/video/BV1ti4y1L7cv?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 难点：
  * 如果k是2，就需要两层for循环，k是100，总不能写一百层for循环吧？
  * 怎样保证得到的是组合而不是排列？（12记录以后就不能记录21了）
* 解法：
 * 用递归来模拟k层for循环，就不用手动写for循环了
 * 用一个指针startindex来记录现在遍历到的数字，这个数字之前的数字就不用遍历了，比如遍历到2，就只遍历23，24，不遍历21 （因为在1的时候已经遍历过12）
 * 定义全局变量一维数组path
 * 定义全局变量二维数组result
 * 写一个递归函数backtrack
 * 回溯三部曲  
  * 确定参数和返回值：参数：n，k,startindex （传入起始位置）返回值：void
  * 终止条件：if path.size()==k, 收割结果（复制path的内容到新的list里，新的list加入res， return！
  * 单层递归/搜索的逻辑
   *  第二层的每一个节点就是一个for循环，单层搜索
   *  每到一个i，就把元素i放进path
   *  递归：自己调用自己：告诉下一层递归的起始位置：i+1，下一层for循环就从i+1开始
   *  回溯：把path最后一个元素弹出去，就可以返回树的上一层节点，好收集不同的path
   * ![Screenshot 2025-03-21 at 16 52 20](https://github.com/user-attachments/assets/dab8bb44-40f1-4fce-8897-a9e927745ff2)
     

### 代码随想录解法：未剪枝优化
* 时间复杂度O(k⋅C(n,k))
 ![Screenshot 2025-03-21 at 16 34 54](https://github.com/user-attachments/assets/d91a214a-19bd-4174-8f4c-a10f31f1bcdb)
* 空间复杂度O(k+k⋅C(n,k))
![Screenshot 2025-03-21 at 16 35 23](https://github.com/user-attachments/assets/fa818e2f-caf7-41bb-b0a3-766bc8952905)

```java
class Solution {
    //初始化result
    List<List<Integer>> res = new ArrayList<>();
    //每一个path也要在全局初始化，因为它会随着回溯和增加节点不停变化
    List<Integer> path = new ArrayList<>();
    //这里也可以用LinkedList<Integer> path = newLinkedList<>();
    //之后回溯就可以用path.removeLast();
    //LinkedList 由于节点额外存储指针，会增加额外的内存开销，但在 removeFirst() 等操作中才会有更明显的优势，而这里我们只在尾部进行操作，ArrayList 仍然是 O(1)。LinkedList 没有实际优势。
    public List<List<Integer>> combine(int n, int k) {
        backtrack(n,k,1);
        return res;
        }
        
    //回溯递归函数
    private void backtrack(int n, int k, int startIndex){
        //如果path的大小到k，我们就可以收割结果到res
        if (path.size() == k){
            //这里另起一个新的arraylist作为path目前状态的快照
            //不能直接传入path，因为path会不断变化，回溯到最后可能什么都没有
            res.add(new ArrayList<>(path));
            return //这里写return便于理解如何从backtrack 到回溯path.remove()的
        }
        //在回溯里包括一个for循环来遍历集合中的元素，使用startindex来避免出现重复组合(比如12,21)
        for (int i = startIndex; i<n+1; i++){
            path.add(i);
            //这里开始下一个startindex的深度递归
            backtrack(n,k,i+1);
            //回溯，回到上一个节点，然后马上执行下一个for循环（i++以后）
            path.remove(path.size()-1);
        }
    }
}
```
### 代码随想录：剪枝优化
* 如果for循环选择的起始位置之后的元素个数已经小雨我们需要的元素个数了，那么就不用搜索了
* 这一行中：for (int i = startIndex; i <= n; i++)
* i<= n可以优化成i <= n-(k-path.size()) + 1,遍历到这里可以保证最后一位i的遍历也是有valid answer的
* 还需要的元素个数为: k - path.size();
* 为什么有个+1呢，因为包括起始位置，我们要是一个左闭的集合。
```java
class Solution {
    List<List<Integer>> res = new ArrayList<>();
    List<Integer> path = new ArrayList<>();
    
    public List<List<Integer>> combine(int n, int k) {
        backtrack(n,k,1);
        return res;
        }
        
    private void backtrack(int n, int k, int startIndex){
        if (path.size() == k){
            res.add(new ArrayList<>(path));
            return;
        }
        //这里进行剪枝
        for (int i = startIndex; i<= n- (k-path.size())+1; i++){
            path.add(i);
            backtrack(n,k,i+1);
            path.remove(path.size()-1);
        }
    }
}
```
## 216. Combination Sum III
* https://leetcode.com/problems/combination-sum-iii/description/
* 文章：https://programmercarl.com/0216.%E7%BB%84%E5%90%88%E6%80%BB%E5%92%8CIII.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 视频：https://www.bilibili.com/video/BV1wg411873x?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 我的设想：
* 和77组合很像，但是终止条件不一样
* 全局二维list res， 全局一维list path, 全局int sum
* 回溯三部曲：
 * 返回void，参数k,n,start index（避免重复）
 * 终止条件：
 *if path.size == k{
 * if (sum == n) res.add path, return;
 * ekse return;}
 * 单层逻辑
  * for (i=startindex; i<=9; i++){
  * path.add(i);
  * sum += i;
  * 递归k,n,i+1
  * 回溯：path.remove(path.size()-1), sum -= i
### 根据77的逻辑写出来的解法
```java
class Solution {
    List<List<Integer>> res = new ArrayList<>();
    List<Integer> path = new ArrayList<>();
    int sum = 0;
    public List<List<Integer>> combinationSum3(int k, int n) {
        backtrack(k,n,1);
        return res;
    }

    private void backtrack(int k, int n, int startIndex){
        if (path.size() == k){
            if (sum==n) {
                res.add(new ArrayList<>(path));
                return;
            }
            else return;
        }
        for (int i=startIndex; i <= 9; i ++){
            path.add(i);
            sum += i;

            backtrack(k,n,i+1);

            path.remove(path.size()-1);
            sum -= i;
        }
    }
}
```
### 剪枝优化以后
```java
class Solution {
    List<List<Integer>> res = new ArrayList<>();
    List<Integer> path = new ArrayList<>();
    int sum = 0;
    public List<List<Integer>> combinationSum3(int k, int n) {
        backtrack(k,n,1);
        return res;
    }

    private void backtrack(int k, int n, int startIndex){
        if (path.size() == k){
            if (sum==n) {
                res.add(new ArrayList<>(path));
                return;
            }
            else return;
        }
        //这里进行剪枝，比如k=4,pathsize=0,9-4+1=5, 6789为最后一个可以被验证的，大小为4的组合
        for (int i=startIndex; i <= 9 - (k-path.size()) + 1; i ++){
            path.add(i);
            sum += i;

            backtrack(k,n,i+1);

            path.remove(path.size()-1);
            sum -= i;
        }
    }
}
```
## 17. Letter Combinations of a Phone Number
* https://leetcode.com/problems/letter-combinations-of-a-phone-number/description/
* 文章：https://programmercarl.com/0017.%E7%94%B5%E8%AF%9D%E5%8F%B7%E7%A0%81%E7%9A%84%E5%AD%97%E6%AF%8D%E7%BB%84%E5%90%88.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 视频：https://www.bilibili.com/video/BV1yV4y1V7Ug?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
###我想出来的题解
* 回溯函数参数：digits，index
* 返回void
* 终止条件：path和digits一样长了就收割path，return
* 单层循环逻辑：
* digits转换成对应的字母
* path加入字母，递归（digits，index+1）
* 回溯

```java
class Solution {
    List<String> res = new ArrayList<>();
    StringBuilder path = new StringBuilder();
    Map<String, List<String>> map = new HashMap<>();
    //注意这里不能直接map.put("2",["a","b","c"]);
    //可以这样
    //并且可以吧初始化放在{静态代码块}里，或者使用一个initialiseMap()函数来构造
    {map.put("2", Arrays.asList("a", "b", "c"));
    map.put("3",Arrays.asList("d","e","f"));
    map.put("4",Arrays.asList("g","h","i"));
    map.put("5",Arrays.asList("j","k","l"));
    map.put("6",Arrays.asList("m","n","o"));
    map.put("7", Arrays.asList("p","q","r","s"));
    map.put("8",Arrays.asList("t","u","v"));
    map.put("9",Arrays.asList("w","x","y","z"));}

    public List<String> letterCombinations(String digits) {
        //这里直接返回res就会在digits为空的时候返回[]而不是[""]
        if (digits.isEmpty()) return res;
        traverse(digits, 0);
        return res;
    }
    //回溯函数
    private void traverse(String digits, int index){
            //单个path大小和digits一样长了就回收结果
            if (path.length() == digits.length()){
                res.add(path.toString());
                return;
            }
            //转换digit为一个string list
            String dig = Character.toString(digits.charAt(index));
            List<String> candidateList = map.get(dig);
            //append上一个，检查下一个能不能append，不能就回溯
            for (String e: candidateList){
                path.append(e);
                traverse(digits, index+1);
                path.deleteCharAt(path.length() - 1);
            }
    }

}
```
* 学习的点
 * hashmap：静态代码块初始化hashmap{}
 * 如果digits为空就直接返回res，就不会得到["]，而是得到正确的[]（因为为空我们就不需要往里面添加空字符串了
 * 没处理的问题：digit为1，*，#的异常状况：这个用map会比用二维数组更方便
 * map定义的不同方式：不用hashmap而用二维数组
```java
const string letterMap[10] = {
    "", // 0
    "", // 1
    "abc", // 2
    "def", // 3
    "ghi", // 4
    "jkl", // 5
    "mno", // 6
    "pqrs", // 7
    "tuv", // 8
    "wxyz", // 9
};
```
### 代码随想录的解法
```java
class Solution {
    List<String> list = new ArrayList<>();
    StringBuilder temp = new StringBuilder();

    public List<String> letterCombinations(String digits) {
        if (digits == null || digits.length() == 0) {
            return list;
        }
        //使用二维数组做map，但是没有处理异常输入比如*#
        String[] numString = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        backTracking(digits, numString, 0);
        return list;
    }

    public void backTracking(String digits, String[] numString, int num) {
        //这里用num==digits.length()比我写的好，因为num就代表的是digits中的指针，以及递归的深度，到length()说明已经到头了
        if (num == digits.length()) {
            list.add(temp.toString());
            return;
        }
        //这里两个char相减得到一个int
        String str = numString[digits.charAt(num) - '0']; // 直接用字符索引查找
        for (int i = 0; i < str.length(); i++) {
            temp.append(str.charAt(i));
            backTracking(digits, numString, num + 1);
            temp.deleteCharAt(temp.length() - 1);
        }
    }
}
```
