# 并查集理论基础Disjoint set 
* 在计算机科学中，并查集（英文：Disjoint-set data structure，直译为不交集数据结构）是一种数据结构，用于处理一些不交集（Disjoint sets，一系列没有重复元素的集合）的合并及查询问题。
## 并查集解决的问题
* 连通性问题：判断两个元素是否在同一个集合的时候
* 两个主要功能
1. 将两个元素添加到一个集合中
2. 判断两个元素在不在同一个集合
## 原理讲解
* 例子：
* 比如说，我们有 10 个学生（编号 1~10），我们想把他们分成几组，每组互相是朋友。
* 第 1 组：1, 2, 3
* 第 2 组：4, 5
* 第 3 组：6, 7, 8, 9
* 第 4 组：10
* 那么我们怎么“表示”这些分组关系呢？
  * 初学者：用set储存每组。但是万一我们有上百个组，上千个学生，那就没可能hard coding了
  * 并查集的意思是：
  * 我不手动去维护这些组，而是：每个元素都指向它所在集合的“代表元素”（也叫根节点、祖先）。
  * 这个数组叫parent/father
  * 表示的意思是：
  * 1→2→3：所以 1、2、3 属于同一个集合，根是 3
  * 4→5：所以 4、5 在一起，根是 5
  * 6/7/8/9 的根都是 6
  * 10 自己是自己的根（单独一组）

```
father[1] = 2
father[2] = 3
father[3] = 3

father[4] = 5
father[5] = 5

father[6] = 6
father[7] = 6
father[8] = 6
father[9] = 6

father[10] = 10

```
* 其实就是一个有向联通图
### 代码实现：v，u的边加入并查集，也就是说把v的father设置成u
```
// 将v，u 这条边加入并查集
void join(int u, int v) {
    u = find(u); // 寻找u的根
    v = find(v); // 寻找v的根
    if (u == v) return; // 如果发现根相同，则说明在一个集合，不用两个节点相连直接返回
    father[v] = u;
}
```
* 如果两者根相同，那么说明在一个集合，已经有链接了，再次链接可能成环出错
* 如果根不相同，把v的father/root设置成u
### find函数
* 递归寻找这个元素最后的根是什么
```
// 并查集里寻根的过程
int find(int u) {
    if (u == father[u]) return u; // 如果根就是自己，直接返回
    else return find(father[u]); // 如果根不是自己，就根据数组下标一层一层向下找
}
```
### 并查集初始化
*  假如我们知道father[a] = b, father[b]=c
* 如何表示 C 也在同一个集合里呢？ 我们需要 father[C] = C，即C的根也为C，这样就方便表示 A，B，C 都在同一个集合里了。
* 所以father数组初始化的时候要 father[i] = i，默认自己指向自己。
```
// 并查集初始化
void init() {
    for (int i = 0; i < n; ++i) {
        father[i] = i;
    }
}
```
### 判断两个元素是否在同一个集合里
* 如果属于同一个根，那就是同一个集合
```
// 判断 u 和 v是否找到同一个根
bool isSame(int u, int v) {
    u = find(u);
    v = find(v);
    return u == v;
}
```
## 路径压缩
* find函数一直递归的话就是不断获取father数组下标对应的数值，最终找到这个集合的根。如果树的高度很深，那递归就需要很久
* 如果除了根节点以外，其他所有节点都挂载根节点下，这样我们在寻根的时候就很快，只需要一步，
* 想要达到这种效果就需要路径压缩
* 代码实现：我们只需要在递归的过程中，让 father[u] 接住 递归函数 find(father[u]) 的返回结果。
* 因为 find 函数向上寻找根节点，father[u] 表述 u 的父节点，那么让 father[u] 直接获取 find函数 返回的根节点，这样就让节点 u 的父节点 变成根节点。
```
// 并查集里寻根的过程
int find(int u) {
    if (u == father[u]) return u;
    else return father[u] = find(father[u]); // 路径压缩
}
```
* 是指把已经构建好的多层多叉树，压缩成单层多叉树，这样所有叶子都指向最底部的根节点
* 这样压缩了一次，之后每次寻根就可以减少递归次数
## 并查集代码模版：java
```java
public class UnionFind {
    int n = 1005;
    int[] father;

    // 初始化函数
    public init(int n) {
        this.n = n;
        father = new int[n];
        init();
    }

    // 初始化father数组，每个元素自成一个集合
    private void init() {
        for (int i = 0; i < n; i++) {
            father[i] = i;
        }
    }

    // 路径压缩的 find 函数
    public int find(int u) {
        if (father[u] != u) {
            father[u] = find(father[u]); // 路径压缩
        }
        return father[u];
    }

    // 判断两个节点是否属于同一个集合
    public boolean isSame(int u, int v) {
        return find(u) == find(v);
    }

    // 合并两个集合
    public void join(int u, int v) {
        int rootU = find(u);
        int rootV = find(v);
        if (rootU != rootV) {
            father[rootV] = rootU; // 把 v 的根指向 u 的根
        }
    }
}
```
* 通过模板，我们可以知道，并查集主要有三个功能。
1. 寻找根节点，函数：find(int u)，也就是判断这个节点的祖先节点是哪个
2. 将两个节点接入到同一个集合，函数：join(int u, int v)，将两个节点连在同一个根节点上
3. 判断两个节点是否在同一个集合，函数：isSame(int u, int v)，就是判断两个节点是不是同一个根节点

## 常见误区
* 模板中的 join 函数里的这段代码：
```
u = find(u); // 寻找u的根
v = find(v); // 寻找v的根
if (u == v) return ; // 如果发现根相同，则说明在一个集合，不用两个节点相连直接返回

```
* 与 isSame 函数的实现是不是重复了？ 如果抽象一下呢，代码如下：
```
// 判断 u 和 v是否找到同一个根
bool isSame(int u, int v) {
    u = find(u);
    v = find(v);
    return u == v;
}

// 将v->u 这条边加入并查集
void join(int u, int v) {
    if (isSame(u, v)) return ; // 如果发现根相同，则说明在一个集合，不用两个节点相连直接返回
    father[v] = u;
}
```
* 这样写可以吗？ 好像看出去没问题，而且代码更精简了。
* 其实这么写是有问题的，在join函数中 我们需要寻找 u 和 v 的根，然后再进行连线在一起，而不是直接 用 u 和 v 连线在一起。
* 注意老版的join是把两者的root先找到，再把root连起来
* 如果直接把v的root设置成u，而不关心它们的根节点，会破坏并查集的核心结构，导致集合无法正确地合并，进而影响后续操作的正确性。
* 学会模版以后根据代码模版多画一画图（根指向叶子，不要忘了find(v)的时候找的是终极根节点），就会更清楚

## 拓展：按秩合并union by rank
* 合并时，让矮的树挂到高的树下面，减少新树高度 （谁的rank高，谁当root）
* 在 join (合并) 的时候用
* 本质：控制树的增长，避免树高太大
* 如果rank不一样：只是挂过去，rank不变。
* 如果rank一样：挂过去后，新的根rank要加1。
* 和path compression的区别
 * pathcompression：查找时，把路径上的所有节点直接挂到根节点，缩短路径
 * 在 find (查找) 的时候用
 * 	把树变扁平，下次查找更快
### 按秩合并模版
```java
import java.util.Arrays;

public class UnionFind {
    int n = 1005; // n根据题目而定
    int[] father = new int[n];
    int[] rank = new int[n];

    // 初始化
    public void init() {
        for (int i = 0; i < n; i++) {
            father[i] = i;
            rank[i] = 1; // 每棵树初始高度是1
        }
    }

    // 寻找u的根节点（不做路径压缩）
    public int find(int u) {
        if (u == father[u]) {
            return u;
        } else {
            return find(father[u]);
        }
    }

    // 判断u和v是否属于同一个集合
    public boolean isSame(int u, int v) {
        return find(u) == find(v);
    }

    // 将u和v所在的集合合并
    public void join(int u, int v) {
        u = find(u); // 找到u的根
        v = find(v); // 找到v的根
        if (u == v) return; // 如果已经在同一个集合，就不用合并

        if (rank[u] <= rank[v]) {
            father[u] = v; // 小树挂到大树
        } else {
            father[v] = u;
        }

        if (rank[u] == rank[v] && u != v) {
            rank[v]++; // 如果高度相同，合并后高度+1（v作为新的根）
        }
    }
}

```
* 这里推荐大家直接使用路径压缩的并查集模板就好，但按秩合并的优化思路我依然给大家讲清楚，有助于更深一步理解并查集的优化过程。


### 复杂度分析
* 路径压缩版并查集分析
* 空间复杂度： O(n) ，申请一个father数组。
* 时间复杂度：O(log n) 和O(1）之间
 * 最坏情况，树退化成链表一样，查找 find(x)，时间复杂度是 O(n)，因为可能一层一层往上走。
 * 路径压缩的意思是：当我们找某个节点的根时，把沿途所有节点直接挂到根节点下面。这样以后再找它们时，都是一步到根，查询超级快。
 * 所以随着find()次数变多，树变得越来越扁。大部分节点到根节点的距离都非常短，只有一两层，甚至直接连接。
 * 一开始树可能还比较高（比如接近log n层），所以最初find()可能还是要跳log n次。但是经过若干次路径压缩后：节点和根直接挂在一起，这时find()几乎只需要1次或2次操作就能找到根。时间复杂度变成O(1）
 * 为什么一开始的时间复杂度是O(log n)：
  * 按秩合并的时候，树的高度是O(log n)
  * 初始时，每个元素是一个单独的集合，rank为 1。每次合并操作：
   * 如果两棵树的rank不一样，小的挂到大的上面，rank不会变化。
   * 如果两棵树rank相同，合并后新的树的rank才 +1。
   * 而且两棵rank相同的树合并时，节点数至少翻倍。
   * rank为k的树，节点数量至少是2^k, 假设一共有n个节点， 最大的rank r 满足 2^r <= n, 也就是说，r <= log(2) n
   * 所以rank的最大值是logn！
   * 所以在只有按秩合并，没有路径压缩的并查集里，单次find()查询复杂度是 O(log n)！

## km 107. 寻找存在的路径
* https://kamacoder.com/problempage.php?pid=1179
* 文章：https://www.programmercarl.com/kamacoder/0107.%E5%AF%BB%E6%89%BE%E5%AD%98%E5%9C%A8%E7%9A%84%E8%B7%AF%E5%BE%84.html#%E6%80%9D%E8%B7%AF
* 判断无向图中亮点是否可达，就是判断他们是不是在同一颗树里
* 记得我们的节点从1开始计数，但是father[]是0-index，所以新开一个DisJoint instance的时候，节点数量要选择vertices count +1
* 第一次做抄了代码随想录，熟悉流程
### 解法
```
import java.util.*;
public class Main{
    public static void main (String[] args){
        Scanner sc = new Scanner(System.in);
        int verticeCount = sc.nextInt();
        int ledgesCount = sc.nextInt();

        //因为节点从1开始，disjoint的father数组要用verticeCount+1大小
        DisJoint disJoint = new DisJoint(verticeCount+1);
        //把边当成root-branch之间的连接，进行join
        for (int i=0; i<ledgesCount;i++){
            //有向图1->2,也就是说1是根，2是树叶子
            //join（根，树叶）
            disJoint.join(sc.nextInt(), sc.nextInt());
        }
        //接下来两个数字：source, destination
        int src = sc.nextInt();
        int des = sc.nextInt();
        if (disJoint.isSame(src, des)){
            //是同一棵树里的，即无向图可达，输出1
            System.out.print("1");
        }else{
            //不是同一棵树里的，无向图也不可达，输出0
            System.out.print("0");
        }

    }

}
//并查集的独立类
class DisJoint{
    //需要一个father数组记录根
    private int[] father;
    //initialiser，每个节点的根首先是它自己
    public DisJoint(int N){
        father = new int [N];
        //++i这里和i++的作用是一样的，不过++i会少占一点点内存，不过区别不大
        for (int i=0; i<N; ++i){
            father[i] = i;
        }
    }
    //寻找root：进行路径压缩以后，只要向上找一次就能找到根节点
    public int find (int n){
        return n == father[n]? n: (father[n] = find(father[n]));
    }
    //如果两者不在同一个树里，就把他们的根连起来
    public void join(int n, int m){
        n = find(n);
        m = find(m);
        if (n==m) return;
        father[m] = n;
    }
    //判断两者的根是不是一样的，如果不是，那就不在一棵树里
    public boolean isSame(int n, int m){
            n = find(n);
            m = find(m);
            return n==m;
        }
}
```


