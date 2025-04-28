## km 108. 冗余的边
* https://kamacoder.com/problempage.php?pid=1181
* 文章：https://www.programmercarl.com/kamacoder/0108.%E5%86%97%E4%BD%99%E8%BF%9E%E6%8E%A5.html
* 说是要找数组中最后出现的冗余边，但其实我们只要从前向后遍历并且join，第一个找到的isSame=true的边就是冗余边（因为一共有n个节点和n个边）。如果要找到靠前的冗余边，只要从前向后遍历就可以
### 路径压缩解法(会超时）
* 因为在join的时候只是简单地把根挂在一起，并没有最小化树的高度，所以会超时
* 优化：使用按秩合并最小化树的高度，减少find的时间
```java
import java.util.*;

public class Main{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        DisJoint disJoint = new DisJoint(N+1);
        for (int i = 0; i<N;i++){
            int root = sc.nextInt();
            int leaf = sc.nextInt();
            if (disJoint.isSame(root,leaf)){
                System.out.println(root + " " + leaf);
            }
            disJoint.join(root,leaf);
        }
    }
    
}

class DisJoint{
    private int[] father;

    public DisJoint(int n){
        father = new int[n];
        for (int i=0; i<n;i++){
            father[i] = i;
        }
    }

    public int find(int n){
        //这里注意father[n] = find(father[n]一定要加括号，因为=（赋值运算符）比 ?:（三目运算符）优先级低。想要先赋值，然后把赋值后的值作为整个表达式的结果。
        //如果n不是自己的root，就让 father[n] 指向它祖先节点，同时返回这个祖先节点
        return n==father[n]? n: (father[n] = find(father[n]));
    }

    public void join(int n, int m){
        //找祖先节点
        n = find(n);
        m = find(m);
        //如果root相同，就不用join
        if (n==m) return;
        //如果不相同，就把int m的rootjoin到int n的root上
        father[m] = n;
    }

    public boolean isSame(int n, int m){
        n = find(n);
        m = find(m);
        return n==m;
    }
}
```
### 路径压缩 + 按秩合并的优化
```java
import java.util.*;

public class Main{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        DisJoint disJoint = new DisJoint(N+1);
        for (int i = 0; i<N;i++){
            int root = sc.nextInt();
            int leaf = sc.nextInt();
            if (disJoint.isSame(root,leaf)){
                System.out.println(root + " " + leaf);
            }
            disJoint.join(root,leaf);
        }
    }
    
}

//union by rank按秩合并优化
class DisJoint{
    private int[] father;
    private int[] rank; // 每棵树的高度

    public DisJoint(int n){
        father = new int[n];
        rank = new int[n];
        for (int i=0; i<n;i++){
            father[i] = i;
            //一开始每棵树的高度都是1
            rank[i] = 1;
        }
    }

    public int find(int n){
        return n==father[n]? n: (father[n] = find(father[n]));
    }

    public void join(int n, int m){
        //找祖先节点
        n = find(n);
        m = find(m);
        //如果root相同，就不用join
        if (n==m) return;
        //按rank的大小合并，而不是直接把root合并
        if (rank[n]<rank[m]){
            father[n] = m;
        }else if (rank[m]<rank[n]){
            father[m] = n;
        }else{
            //等高，那就并一起（随便哪个挂到哪个），rank++
            father[m] = n;
            rank[n]++;
        }

    }

    public boolean isSame(int n, int m){
        n = find(n);
        m = find(m);
        return n==m;
    }
}
```
## km109. 冗余的边II
* https://kamacoder.com/problempage.php?pid=1182
* 文章：https://www.programmercarl.com/kamacoder/0107.%E5%AF%BB%E6%89%BE%E5%AD%98%E5%9C%A8%E7%9A%84%E8%B7%AF%E5%BE%84.html#%E5%85%B6%E4%BB%96%E8%AF%AD%E8%A8%80%E7%89%88%E6%9C%AC
* 难点：从无向图变成了有向图，那我们为了判断要删除的边就得看
1. 是不是有一个节点有两个入度
2. 如果有一个节点有两个入度，删除哪个，才能保证剩下的图是一个树（只有一个根节点）
3. 如果没有节点有两个入度，那么哪条边构成了环（并查集搜索哪两个节点属于同一个树）

* 代码随想录上的写法总体方向是对的，但是代码逻辑很乱（一会儿Disjoint(nodeMap.length), 一会儿Disjoint(nodeMap.length+1)），命名逻辑也很差，我根据它的基础写了注释和名称更清晰的写法⬇️
### 解法
```java
import java.util.*;

public class Main{
    //并查集模版
    static class Disjoint{
        private int[] father;
        private int[] rank;

        public Disjoint(int n){
            father = new int[n];
            rank = new int[n];
            for (int i=0; i<n;i++){
                father[i] = i;
                rank[i] = 1;
            }
        }

        public void join(int n, int m){
            n = find(n);
            m = find(m);
            if (n==m) return;
            if (rank[n]<rank[m]){
                father[n] = m;
            } else if (rank[m]<rank[n]){
                father[m] = n;
            }else{
                father[m] = n;
                rank[n]++;
            }
        }

        public int find(int n){
            return n==father[n]? n:(father[n] = find(father[n]));
        }

        public boolean isSame(int n, int m){
            n = find(n);
            m = find(m);
            return n==m;
        }
    }

    //边类
    static class Edge{
        int s;
        int t;

        public Edge(int s, int t){
            this.s = s;
            this.t = t;
        }
    }

    //节点类，用来处理出度和入度
    static class Node{
        int id;
        int in;
        int out;
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        List<Edge> edges = new ArrayList<>();
        Node[] nodeMap = new Node[n+1];
        //有1到n这么多node，所以nodemap从node==1开始计数
        for (int i=1; i<=n;i++){
            nodeMap[i] = new Node();
        }
        //记录是否有节点有二入度,有的话，就把那个节点的数字记下来
        Integer doubleIn = null;
        for (int i=0; i<n;i++){
            int s = sc.nextInt();
            int t = sc.nextInt();
            nodeMap[t].in++;
            if (nodeMap[t].in ==2) doubleIn = t;
            Edge edge = new Edge(s,t);
            edges.add(edge);
        }
        Edge res = null;
        //假如存在入度==2的节点，就要找到那个节点，消除入度为2 的问题，同时解除可能存在的环
        if (doubleIn!=null){
            List<Edge> doubleInEdges = new ArrayList<>();
            for (Edge edge:edges){
                //检查是那两条边lead to 入度==2的节点，存入doubleInEdges
                if (edge.t == doubleIn) doubleInEdges.add(edge);
                if (doubleInEdges.size() == 2) break;
            }
            //检查其中一条边
            Edge edge = doubleInEdges.get(1);
            //检查去除了这条边，剩下的图是不是一课树
            if (isTreePostExclude(edges, edge,nodeMap)){
                res = edge;
            }else{
                //如果这条边去除了剩下的节点不能组成树，那就返回另一条边
                res = doubleInEdges.get(0);
            }
        }else{
            //如果没有二入度的节点，说明有环，找到那条形成环的边
            res = getEdgeToRemove(edges, nodeMap);
        }
        System.out.print(res.s + " " + res.t);
        
    }
    //检查去除了edgeToExclude这一条边以后，还是不是一棵树（如果成环就会出现isSame(s,t)
    public static boolean isTreePostExclude(List<Edge> edges, Edge edgeToExclude, Node[] nodeMap){
        //注意并查集是0index开始，所以init的时候是nodecount +1
        Disjoint disjoint = new Disjoint(nodeMap.length+1);
        for (Edge edge: edges){
            //跳过我们要exclude的那个边
            if (edge == edgeToExclude) continue;
            //检查其他边中，start和terminal是不是已经属于同一颗树了，如果是，就成环了
            if (disjoint.isSame(edge.s, edge.t)){
                return false;
            }
            disjoint.join(edge.s, edge.t);
        }
        return true;
    }
    //
    public static Edge getEdgeToRemove(List<Edge> edges, Node[] nodeMap){
        int l = nodeMap.length;
        Disjoint disjoint = new Disjoint(l+1);
        for (Edge edge: edges){
            if (disjoint.isSame(edge.s, edge.t)) return edge;
            disjoint.join(edge.s, edge.t);
        }
        return null;
    }
}
```
