# 图论理论基础
## 图的基本概念
* 二维坐标中，两点可以连成线，多个点连成的线就构成了图。单个节点也可以构成图，0个节点也可以（空图）
## 图的种类和概念
### 有向图（directed graph/ digraph)
* ![image](https://github.com/user-attachments/assets/5bb5e1d4-5241-47e7-a2a2-c198ccc20ec1)
* 边有方向
### 无向图（undirected graph)
* 边没有方向
* ![image](https://github.com/user-attachments/assets/64da9b4a-8f72-4a63-8993-91b0d872ba23)
### 加权有向图 （weighted graph)
* 图中边有权值
*![image](https://github.com/user-attachments/assets/4b7f8ae2-8d57-4dc6-a994-502ca0351e93)
### 度（degree)
* 无向图中有几条边链接盖点，该节点就有几度
### 出度和入度 （out-degree & in-degree)
* 有向图中
* 入度；指向该节点的边的个数
* 出度：从该节点出发的边的个数
### 连通性
* 无向图：如果任何两个节点都是可以到达的，我们称之为**连通图 （connected graph）**
* ![image](https://github.com/user-attachments/assets/e5a93696-7286-42f4-a9a7-f9bdfa97918c)
* 如果有节点不能到达其他节点，则为**非连通图  （disconnected graph）**
* ![image](https://github.com/user-attachments/assets/eac03d40-a532-4f78-b9b1-21dce614120e)
### 强连通图 strongly connected graph
* 有向图中，任何两个节点之间可以互相到达，那就是强连通图，比如下面这个图
* ![image](https://github.com/user-attachments/assets/3ce11f85-e055-40e0-95e2-90e45ad30ae7)
* 下图不是强连通图，因为节点5不能到节点1
* ![image](https://github.com/user-attachments/assets/158d9034-6b42-4d79-aab4-f8463de6313d)
### 连通分量 connected component
* 无向图中的极大连通子图称之为该图的一个连通分量
* ![image](https://github.com/user-attachments/assets/572d7845-557f-428f-b7be-b48367fef507)
* 连通分量：节点1、节点2、节点5 构成的子图，节点3、节点4、节点6 构成的子图
* 不是连通分量： 节点3 、节点4 构成的子图。 原因：必须是极大联通子图才能是连通分量
* 连通分量对岛屿问题来说是一个很重要的概念
### 强连通分量 strongly connected component
* 有向图中极大连通子图称之为该图的强连通分量
* ![image](https://github.com/user-attachments/assets/e590307a-dea2-442a-be12-57927b6c3910)
## 图的构造
* 如何用代码表示一个图？用领结表，领接矩阵或者用自定义的graph class类
* <img width="835" alt="Screenshot 2025-04-21 at 15 24 41" src="https://github.com/user-attachments/assets/a67e6ada-f3f9-4065-847e-83ac662cae2c" />
* 代码随想录：主要用朴素存储、邻接表和邻接矩阵。
* 稠密图和稀疏图：
* 若一张图的边数远小于其点数的平方，那么它是一张稀疏图(sparse graph)。 若一张图的边数接近其点数的平方，那么它是一张稠密图(dense graph)。
### 朴素储存：存所有边到一个二维数组，第一个数字是起始节点，第二个数字是终止节点
* 朴素储存是他自创的名字，意思是把所有边存下来。比如：
* ![image](https://github.com/user-attachments/assets/980a220a-9b75-4116-836f-79364dea7706)
* 图中有8条边，我们就定义8*2的二维数组，即有n条边就申请n * 2，这么大的数组
* ![image](https://github.com/user-attachments/assets/5ca0d609-bd8d-493d-8e64-7d8aef873d52)
* 数组第一行：6 7，就表示节点6 指向 节点7，以此类推。
* 当然可以不用数组，用map，或者用 类 到可以表示出 这种边的关系。
* 这种表示方式的好处就是直观，把节点与节点之间关系很容易展现出来。
* 但如果我们想知道 节点1 和 节点6 是否相连，我们就需要把存储空间都枚举一遍才行。
* 这是明显的缺点，同时，我们在深搜和广搜的时候，都不会使用这种存储方式。因为 搜索中，需要知道 节点与其他节点的链接情况，而这种朴素存储，都需要全部枚举才知道链接情况。
### 领接矩阵 adjacent matrix
* 使用 二维数组来表示图结构。 邻接矩阵是从节点的角度来表示图，有多少节点就申请多大的二维数组。在一个 n （节点数）为8 的图中，就需要申请 8 * 8 这么大的空间。
* 例如： grid[2][5] = 6，表示 节点 2 连接 节点5 为有向图，节点2 指向 节点5，边的权值为6。
* 如果想表示无向图，即：grid[2][5] = 6，grid[5][2] = 6，表示节点2 与 节点5 相互连通，权值为6。
* ![image](https://github.com/user-attachments/assets/6bafc885-2027-4bbb-a47d-429582e0274c)
* **缺点**
  * 这种表达方式（邻接矩阵） 在 边少，节点多的情况下，会导致申请过大的二维数组，造成空间浪费。
  * 而且在寻找节点连接情况的时候，需要遍历整个矩阵，即 n * n 的时间复杂度，同样造成时间浪费。
* **优点**
  * 表达方式简单，易于理解
  * 检查任意两个顶点间是否存在边的操作非常快
  * 适合稠密图（ dense graph, 顺便稀疏图是sparse graph），在边数接近顶点数平方的图中，邻接矩阵是一种空间效率较高的表示方法。

### 领接表adjacent list
* 邻接表 使用 数组 + 链表的方式来表示。 邻接表是从边的数量来表示图，有多少边 才会申请对应大小的链表。
* ![image](https://github.com/user-attachments/assets/cb1fa9ab-2d6d-4242-9457-b2bc6d327398)
* 这里表达的图是：
  * 节点1 指向 节点3 和 节点5
  * 节点2 指向 节点4、节点3、节点5
  * 节点3 指向 节点4
  * 节点4指向节点1
* 有多少边 邻接表才会申请多少个对应的链表节点。从图中可以直观看出 使用 数组 + 链表 来表达 边的连接情况 。
* **优点**
  * 对于稀疏图的存储，只需要存储边，空间利用率高
  * 遍历节点连接情况相对容易
* **缺点**
  * 检查任意两个节点间是否存在边，效率相对低，需要 O(V)时间，V表示某节点连接其他节点的数量。
  * 实现相对复杂，不易理解
## 图的遍历方式
* 基本是两大类：dfs（深度优先搜索）和bfs（广度优先搜索）
* 在讲解二叉树章节的时候，其实就已经讲过这两种遍历方式。
* 二叉树的递归遍历，是dfs 在二叉树上的遍历方式。
* 二叉树的层序遍历，是bfs 在二叉树上的遍历方式。
* dfs 和 bfs 一种搜索算法，可以在不同的数据结构上进行搜索，在二叉树章节里是在二叉树这样的数据结构上搜索。
* 而在图论章节，则是在图（邻接表或邻接矩阵）上进行搜索。

# 深度搜索dfs理论基础
## 和bfs的区别
* dfs：一个方向搜到底，搜到头了再换方向（换方向的过程涉及到回溯）。怎么定义搜到头了呢？1.找到了目标节点， 2.找到了我们已经遍历过的节点
* bfs：把本节点所连接的所有节点遍历一遍，走到下一个节点的时候再把链接节点的所有节点遍历一遍，搜索方向更像是广度，四面八方的搜索过程
## dfs搜索过程
* 如图一，是一个无向图，我们要搜索从节点1到节点6的所有路径。
* ![image](https://github.com/user-attachments/assets/2cd091ac-f580-49a8-83da-be0c0fa18ddf)
* 那么dfs搜索的第一条路径是这样的： （假设第一次延默认方向，就找到了节点6），图二
* ![image](https://github.com/user-attachments/assets/8a0a626b-6eaa-4832-8735-7728b55480e6)
* 此时我们找到了节点6，（遇到黄河了，是不是应该回头了），那么应该再去搜索其他方向了。 如图三：
* ![image](https://github.com/user-attachments/assets/40180395-e600-4c77-9dc3-99af50c5f08b)
* 路径2撤销了，改变了方向，走路径3（红色线）， 接着也找到终点6。 那么撤销路径2，改为路径3，在dfs中其实就是回溯的过程（这一点很重要，很多录友不理解dfs代码中回溯是用来干什么的）
* 又找到了一条从节点1到节点6的路径，又到黄河了，此时再回头，下图图四中，路径4撤销（回溯的过程），改为路径5。
* ![image](https://github.com/user-attachments/assets/f6aa194b-56e5-4ef9-b6c7-6e1cbbfaf851)
* 又找到了一条从节点1到节点6的路径，又到黄河了，此时再回头，下图图五，路径6撤销（回溯的过程），改为路径7，路径8 和 路径7，路径9， 结果发现死路一条，都走到了自己走过的节点。
* ![image](https://github.com/user-attachments/assets/dad173ec-4a30-4d6d-9498-c2084e1f93ad)
* 那么节点2所连接路径和节点3所链接的路径 都走过了，撤销路径只能向上回退，去选择撤销当初节点4的选择，也就是撤销路径5，改为路径10 。 如图图六：
* ![image](https://github.com/user-attachments/assets/9a93d118-8bee-4233-8e9d-fd3f502de753)
* 上图演示中，其实我并没有把 所有的 从节点1 到节点6的dfs（深度优先搜索）的过程都画出来，那样太冗余了，但 已经把dfs 关键的地方都涉及到了，关键就两点：
  * 搜索方向，是认准一个方向搜，直到碰壁之后再换方向
  * 换方向是撤销原路径，改为节点链接的下一个路径，回溯的过程
## 代码框架
* 正是因为dfs搜索可一个方向，并需要回溯，所以用递归的方式来实现是最方便的。
* 有递归的地方就有回溯，那么回溯在哪里呢？
* 就递归函数的下面，例如如下代码：
```
void dfs(参数) {
    处理节点
    dfs(图，选择的节点); // 递归
    回溯，撤销处理结果
}
```
* 回顾一下回溯算法的代码框架
```
void backtracking(para){
  if (stop condition) {
    store result;
    return;
  }
  for (选择：本层集合中元素（树中节点孩子的数量就是集合的大小）){
    处理节点；
    backtracking(路径，选择列表)；//递归
    回溯，撤销处理结果；
  }
}
```
* 回溯其实就是dfs的过程，dfs的代码框架：
```
void dfs(参数) {
    if (终止条件) {
        存放结果;
        return;
    }

    for (选择：本节点所连接的其他节点) {
        处理节点;
        dfs(图，选择的节点); // 递归
        回溯，撤销处理结果
    }
}
```
### 深搜三部曲分析dfs代码框架
* 在 二叉树递归讲解中，给出了递归三部曲。回溯算法讲解中，给出了 回溯三部曲。
* 其实深搜也是一样的，深搜三部曲如下：
1. 确认递归函数参数
* 通常我们递归的时候，我们递归搜索需要了解哪些参数，其实也可以在写递归函数的时候，发现需要什么参数，再去补充就可以。
* 一般情况，深搜需要 二维数组数组结构保存所有路径，需要一维数组保存单一路径，这种保存结果的数组，我们可以定义一个全局变量，避免让我们的函数参数过多。
```
vector<vector<int>> result; // 保存符合条件的所有路径
vector<int> path; // 起点到终点的路径
void dfs (图，目前搜索的节点)
```
2. 确认终止条件
* 终止条件很重要，很多同学写dfs的时候，之所以容易死循环，栈溢出等等这些问题，都是因为终止条件没有想清楚。
* 终止添加不仅是结束本层递归，同时也是我们收获结果的时候。
* 另外，其实很多dfs写法，没有写终止条件，其实终止条件写在了， 隐藏在下面dfs递归的逻辑里了，也就是不符合条件，直接不会向下递归。
```
if (终止条件) {
    存放结果;
    return;
}
```
3. 处理目前搜索节点出发的路径
* 一般这里就是一个for循环的操作，去遍历 目前搜索节点 所能到的所有节点。
```
for (选择：本节点所连接的其他节点) {
    处理节点;
    dfs(图，选择的节点); // 递归
    回溯，撤销处理结果
}
```
* 不少录友疑惑的地方，都是 dfs代码框架中for循环里分明已经处理节点了，那么 dfs函数下面 为什么还要撤销的呢。
* 如图七所示， 路径2 已经走到了 目的地节点6，那么 路径2 是如何撤销，然后改为 路径3呢？ 其实这就是 回溯的过程，撤销路径2，走换下一个方向。
* ![image](https://github.com/user-attachments/assets/13822616-7311-4394-92b2-980b40f1067c)

## km98. 所有可达路径
* https://kamacoder.com/problempage.php?pid=1170
### adjacent matrix
* 深搜三部曲
* 首先我们要自己构造graph
1. 确认递归函数和参数
* dfs参数：一个储存好的图，当前节点x，终点节点n，这样就可以判断当前节点==n的时候终止遍历。因为不成环，不需要处理‘走到已经遍历过的节点’的情况
* 所有符合的路径储存在res（List<List<Integer>>)，当前正在构造的路径储存在path(List<Integer>)，两个全局变量
* dfs返回值：void，因为只需要处理而且结果被存在全局变量里了
2. 确定终止条件
* 找到路径的意思就是当目前遍历的节点 为 最后一个节点 n 的时候 就找到了一条 从出发点到终止点的路径。
3. 处理目前搜索节点出发的路径（递归和回溯）
* 接下来要走当前遍历节点x的下一个节点
* 首先需要确定x节点能走到哪（指向了哪些节点），就得遍历所有节点，看看储存的图中有没有说x可以到达这个节点 (if graph[x][i] ==1)
* 然后把这个节点i加入路径path
* 然后递归：dfs(graph, i,n)
* 递归return回来以后需要改道，就得把path的最后一个元素移除，方便新的i进来
* 需要注意的点：打印结果的时候最后一个元素后面不能有空格哦
* 适合稠密图，容易检查边是否存在，但是容易浪费空间
```java
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main{
    //这两个变量用static是因为main和dfs都是static方法，static方法只能访问static 变量。static方法属于这个class，不需要新起一个instance，可以直接调动Main.dfs();
    static List<List<Integer>> res = new ArrayList<>();//收集符合条件的路径
    static List<Integer> path = new ArrayList<>();//1节点到终点的路径

    //java程序的执行入口必须是main方法
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        //节点数
        int n = sc.nextInt();
        //边数
        int m = sc.nextInt();

        //用adjacent matrix 储存，节点从1开始，所以申请n+1*n+1的二维数组
        int[][] graph = new int [n+1][n+1];
        //遍历边的起始和终结
        for (int i=0; i<m;i++){
            //start
            int s = sc.nextInt();
            //terminal
            int t = sc.nextInt();
            //1代表有链接
            graph[s][t] = 1;
        }
        //path永远从1出发
        path.add(1);
        dfs(graph,1,n);

        //加入res为空输出-1
        if (res.isEmpty()) System.out.println(-1);
        for (List<Integer> pa:res){
            //除了最后一个元素 前面的元素打印以后都要加空格
            for (int i=0; i<pa.size()-1;i++){
                System.out.print(pa.get(i)+" ");
            }
            //最后一个元素打印以后没有空格，直接加回车（.println())
            System.out.println(pa.get(pa.size()-1));
        }
    }

    public static void dfs(int[][] graph, int x, int n){
        //当前遍历的节点x，到达节点n，为终止条件
        //我们要找的是，x节点开始可以往哪里走
        if (x==n){//到达就可以收割这条有效路径了
            res.add(new ArrayList(path));
            //回溯
            return;
        }
        //i是terminal，终止节点，我们找从x到i有没有路径存在
        for (int i=1; i<=n; i++){
            if (graph[x][i]==1){//x可以到达i
                path.add(i);
                dfs(graph,i,n);//进入下一层递归，从i节点开始走
                //递归完毕，回来回溯就是把最后一个加入的节点删除，好检查新的i
                path.remove(path.size()-1);
            }
        }
    }
}
```
### adjacent list写法
* 适合稀疏图
* 节省空间，但是检查是否存在边，会慢
```
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Scanner;

public class Main{
    static List<List<Integer>> res = new ArrayList<>();//收集符合条件的路径
    static List<Integer> path = new ArrayList<>();//1节点到终点的路径

    
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();

        //节点编号从1到n，所以申请n+1这么大的数组（list）
        //用LinkedList储存terminal是因为它用来频繁增加/删除元素的性能更好
        List<LinkedList<Integer>> graph = new ArrayList<>(n+1);
        //外层list就是每一个节点
        for (int i=0; i<=n;i++){
            graph.add(new LinkedList<>());
        }
        //构造内层list：每个节点能到达的节点
        //下面这行的意思：只要m大于0，就运行，然后m--
        while (m-- >0){
            int s = sc.nextInt();
            int t = sc.nextInt();
            graph.get(s).add(t);
        }

        path.add(1);
        dfs(graph, 1, n);
        
        if (res.isEmpty()) System.out.println(-1);
        for (List<Integer> pa: res){
            for (int i=0; i<pa.size()-1;i++){
                System.out.print(pa.get(i)+" ");
            }
            System.out.println(pa.get(pa.size()-1));
        }


        //加入res为空输出-1
        if (res.isEmpty()) System.out.println(-1);
        for (List<Integer> pa:res){
            //除了最后一个元素 前面的元素打印以后都要加空格
            for (int i=0; i<pa.size()-1;i++){
                System.out.print(pa.get(i)+" ");
            }
            //最后一个元素打印以后没有空格，直接加回车（.println())
            System.out.println(pa.get(pa.size()-1));
        }
    }

    public static void dfs(List<LinkedList<Integer>> graph, int x, int n){
        if (x==n){
            res.add(new ArrayList<>(path));
            return;
        }
        for (int i:graph.get(x)){
            //这里i直接就是x可以到达的节点了；
            path.add(i);
            //递归
            dfs(graph,i,n);
            //回溯
            path.remove(path.size()-1);
        }
    }
}
```
# 广度优先搜索理论
* 视频：https://www.bilibili.com/video/BV1M19iY4EL9?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 文章：https://programmercarl.com/kamacoder/%E5%9B%BE%E8%AE%BA%E5%B9%BF%E6%90%9C%E7%90%86%E8%AE%BA%E5%9F%BA%E7%A1%80.html#%E4%BB%A3%E7%A0%81%E6%A1%86%E6%9E%B6
* 广搜（bfs）是一圈一圈的搜索过程
## 使用的场景
* **适合于解决两个点之间的最短路径问题。**因为bfs一层层从开始节点向外扩展，一旦 BFS 找到终点，那它一定是“最先被访问的那条路径”，也就是“最短的路径”。
* 深度搜索：适合寻找所有路径的问题， 图的连通性、环检测，拓扑排序（有向无环图）
* 当然，也有一些问题是广搜 和 深搜都可以解决的，例如岛屿问题，这类问题的特征就是不涉及具体的遍历方式，只要能把相邻且相同属性的节点标记上就行。 （我们会在具体题目讲解中详细来说
## bfs过程
* 上面我们提过，BFS是一圈一圈的搜索过程，但具体是怎么一圈一圈来搜呢。
* 我们用一个方格地图，假如每次搜索的方向为 上下左右（不包含斜上方），那么给出一个start起始位置，那么BFS就是从四个方向走出第一步。
* ![image](https://github.com/user-attachments/assets/7a92ca38-5214-4af1-83ec-96bdd81fe283)
* 如果加上一个end终止位置，那么使用BFS的搜索过程如图所示：
* ![image](https://github.com/user-attachments/assets/465fd297-a500-4dee-9fe1-d72b4343146b)
* 这样处理的地图还可以有障碍
* ![image](https://github.com/user-attachments/assets/990844e0-f12b-42b7-ab01-f7cc1bc6675a)
* 从图中可以看出，如果添加了障碍，我们是第六步才能走到end终点。
## 代码框架
* 大家应该好奇，这一圈一圈的搜索过程是怎么做到的，是放在什么容器里，才能这样去遍历。很多网上的资料都是直接说用队列来实现。
* 其实，我们**仅仅需要一个容器，能保存我们要遍历过的元素就可以**，那么用队列，还是用栈，甚至用数组，都是可以的。
1. 用队列的话，就是保证每一圈都是一个方向去转，例如统一顺时针或者逆时针。因为队列是先进先出，加入元素和弹出元素的顺序是没有改变的。
2. 如果用栈的话，就是第一圈顺时针遍历，第二圈逆时针遍历，第三圈有顺时针遍历。因为栈是先进后出，加入元素和弹出元素的顺序改变了。
  * 那么广搜需要注意 转圈搜索的顺序吗？ 不需要！所以用队列，还是用栈都是可以的，但大家都习惯用队列了，所以下面的讲解用我也用队列来讲，只不过要给大家说清楚，并不是非要用队列，用栈也可以。
### 模版（c++伪代码） 和领接矩阵（视频展示）
* 第一步：确认bfs函数参数：图，visited记录已经遍历过的节点，当前节点的x,y坐标
* 第二步: 确定全局变量。每个节点要搜索四个方向，就要定义一个二维数组，把xy的变化存下来{0,1,1,0,-1,0,0,-1)
* 第三步，定义一个队列，起点要一开始加入到队列里。才可以开始广度搜索。
* 第一个节点入队（准备开始可以处理它），然后在visited里标记成已经到过这个节点
* while循环开始遍历
```
  //定义bfs和参数
dir[4][2] = {0,1,1,0,-1,0,0,-1}
void bfs (grid[][],visited[],x,y){
    //队列里面放x,y
    que<pair<int,int>> que;
    //起点坐标传进来入队
    que.push({x,y});
    //我们要标记我们已经到过这个节点了
    visited[x][y] = 1;
    //队列不为空，就一直去遍历。空了的意思就是地图遍历完了，因为每个节点相邻的节点都会加入queue，不会落下任何节点
    while (!que.isEmpty()){
        //把首元素取出来，才能开始上下左右遍历
        cur = que.front(); //记得这是一个pair(x,y)
        //记得弹出
        que.pop();
        //把首元素的上下左右也加入到队列里，就能继续广度搜索的过程
        //一个for循环的i，就取出一个方向的操作
        for (int i=0; i<4;i++){
            //curr的第一个和第二个元素就是当前坐标的xy
            //xy加减的操作，储存在四个方向里面（x是加1还是-1还是+0...)
            nextX =cur.first + dir[i][0];
            nextY = cur.second + dir[i][i];
            //需要检查新坐标是不是越界
            if (nextX , nextY 越界){
                continue;//跳过这个， 取其他元素
            }
            //检查新节点是不是已经遍历过,没遍历过才能继续
            if (visited[nextX][nextY]==0){
                //找到了符合条件上下左右条件的nextX,nextY，就可以加入队列了
                que.push({nextX,nextY});
                //标记已经遍历过这个节点
                visited[nextX][nextY]=1;
            }

        }
    }
}
```
### 模版（c++） 和领接表
```c++
int dir[4][2] = {0, 1, 1, 0, -1, 0, 0, -1}; // 表示四个方向
// grid 是地图，也就是一个二维数组
// visited标记访问过的节点，不要重复访问
// x,y 表示开始搜索节点的下标
void bfs(vector<vector<char>>& grid, vector<vector<bool>>& visited, int x, int y) {
    queue<pair<int, int>> que; // 定义队列
    que.push({x, y}); // 起始节点加入队列
    visited[x][y] = true; // 只要加入队列，立刻标记为访问过的节点
    while(!que.empty()) { // 开始遍历队列里的元素
        pair<int ,int> cur = que.front(); que.pop(); // 从队列取元素
        int curx = cur.first;
        int cury = cur.second; // 当前节点坐标
        for (int i = 0; i < 4; i++) { // 开始想当前节点的四个方向左右上下去遍历
            int nextx = curx + dir[i][0];
            int nexty = cury + dir[i][1]; // 获取周边四个方向的坐标
            if (nextx < 0 || nextx >= grid.size() || nexty < 0 || nexty >= grid[0].size()) continue;  // 坐标越界了，直接跳过
            if (!visited[nextx][nexty]) { // 如果节点没被访问过
                que.push({nextx, nexty});  // 队列添加该节点为下一轮要遍历的节点
                visited[nextx][nexty] = true; // 只要加入队列立刻标记，避免重复访问
            }
        }
    }

}
```
### 模版（java）
```
import java.util.*;

public class BFSExample {
    // 表示四个方向：右、下、上、左
    static int[][] dir = {{0,1}, {1,0}, {-1,0}, {0,-1}};

    // grid 表示地图，visited 表示访问标记，x 和 y 是起始点坐标
    public static void bfs(char[][] grid, boolean[][] visited, int x, int y) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{x, y}); // 加入起始坐标
        visited[x][y] = true;

        while (!queue.isEmpty()) {
            int[] curr = queue.poll(); // 当前坐标
            int curX = curr[0];
            int curY = curr[1];

            for (int i = 0; i < 4; i++) {
                int nextX = curX + dir[i][0];
                int nextY = curY + dir[i][1];

                // 越界判断
                if (nextX < 0 || nextX >= grid.length || nextY < 0 || nextY >= grid[0].length) {
                    continue;
                }

                // 如果没访问过，加入队列
                if (!visited[nextX][nextY]) {
                    queue.offer(new int[]{nextX, nextY});
                    visited[nextX][nextY] = true; // 加入队列就标记
                }
            }
        }
    }
}
```
### java 应用
* 假如你有一个grid和visited
```java
char[][] grid = {
    {'1', '1', '0'},
    {'0', '1', '0'},
    {'0', '0', '1'}
};
boolean[][] visited = new boolean[grid.length][grid[0].length];

// 对每个格子都判断是否是 '1' 且没访问过
for (int i = 0; i < grid.length; i++) {
    for (int j = 0; j < grid[0].length; j++) {
        if (grid[i][j] == '1' && !visited[i][j]) {
            BFSExample.bfs(grid, visited, i, j);
            // 每次 bfs 就代表找到一个连通块
        }
    }
}
```





