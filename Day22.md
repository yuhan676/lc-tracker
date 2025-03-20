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
  * 
