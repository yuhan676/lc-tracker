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
