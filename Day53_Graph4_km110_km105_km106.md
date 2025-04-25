## km110. 字符串接龙
* https://kamacoder.com/problempage.php?pid=1183
* 文章：https://www.programmercarl.com/kamacoder/0110.%E5%AD%97%E7%AC%A6%E4%B8%B2%E6%8E%A5%E9%BE%99.html#%E5%85%B6%E4%BB%96%E8%AF%AD%E8%A8%80%E7%89%88%E6%9C%AC
### bfs遍历最短路径最合适
* 使用adjacent list来储存每一个string可以到达的下一个string（即链接）
* 单独写一个function判断两者之间能不能链接
* 记住：bfs遍历了一个string的全部邻居格子，才能算走了一步
```java
import java.util.*;

public class Main{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        //读取数字以后换行
        sc.nextLine();
        //新的一行读取字符串
        String input = sc.nextLine();
        //trim去除前后的空格，split("\\s+"去除字符串中间的一个或者多个空格（+表示一个或者多个）， \\s表示空格字符)
        String[] splitInput = input.trim().split("\\s+");
        String beginStr = splitInput[0];
        String endStr = splitInput[1];
        //储存strList到一个hashset里
        Set<String> strList = new HashSet<>();
        for (int i=0; i<n;i++){
            String str = sc.nextLine();
            strList.add(str);
        }
        strList.add(beginStr);
        strList.add(endStr);
        Map<String,ArrayList<String>> graphmap = new HashMap<>();
        for (String word1: strList){
            for (String word2: strList){
                if (!word1.equals(word2) && changeable(word1,word2)){
                    graphmap.put(word1, graphmap.getOrDefault(word1, new ArrayList<String>()));
                    graphmap.get(word1).add(word2);
                }
            }
        }
        int ans = bfs(beginStr, endStr, graphmap);
        System.out.print(ans);


    }
    public static int bfs(String start, String end, Map<String, ArrayList<String>> graph){
        Queue<String> que = new LinkedList<>();
        //需要visited记录已经走过的字符串
        Set<String> visited = new HashSet<>();

        int steps=1;
        que.offer(start);
        visited.add(start);

        while (!que.isEmpty()){
            int size = que.size();
            //遍历所有的que内的字符串
            for (int i=0; i<size; i++){
                String current = que.poll();
                if (current.equals(end)){
                    return steps;
                }
                //把current的所有未被遍历过的邻居假如que中，标记visited
                //就算current没有连接着的邻居，返回的也是一个空的列表[]，不是一个空字符串"“,所以不用担心检查出错
                for (String neighbor: graph.getOrDefault(current, new ArrayList<>())){
                    if (!visited.contains(neighbor)){
                        visited.add(neighbor);
                        que.offer(neighbor);
                    }
                }
            }
            //遍历完start的所有邻居了，step可以增加1
            steps++;
        }
        //如果没有找到可以走的路，return0
        return 0;
    }
    //判断word1能不能由改变一个字符变成word2
    public static boolean changeable(String word1, String word2){
        if (word1.length() != word2.length()) return false;
        int diff = 0;
        for (int i=0; i<word1.length();i++){
            if (word1.charAt(i) != word2.charAt(i)){
                diff++;
            }
            if (diff>1){
                return false;
            }
        }
        return diff==1;
    }   
}
```
## km105.有向图的完全可达性
* https://kamacoder.com/problempage.php?pid=1177
* 文章：https://www.programmercarl.com/kamacoder/0105.%E6%9C%89%E5%90%91%E5%9B%BE%E7%9A%84%E5%AE%8C%E5%85%A8%E5%8F%AF%E8%BE%BE%E6%80%A7.html#%E6%80%9D%E8%B7%AF
* 代码随想录上的java写法是：用boolean[]存visited，List<LinkedList<Integer>>存adjacent list，但是因为都是0-index,所以其实不能保留题里给的节点编号。好在题并不在意节点的具体编号，只是在意从节点1开始走能不能走完全部的图。我还是会觉得会有点绕。
### dfs 
```java
import java.util.*;

public class Main{
    static List<LinkedList<Integer>> adjList = new LinkedList<>();

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        
        for (int i=0; i<n;i++){
            adjList.add(new LinkedList<Integer>());
        }
        for (int i=0;i<k;i++){
            int s = sc.nextInt();
            int t = sc.nextInt();
            //这里注意adjList外层下标是0-n-1,所以我们给内容的下标也要标成n-1
            adjList.get(s-1).add(t-1);
        }
        boolean[] visited = new boolean[n];
        //遍历一次
        dfs(visited,0);
        //如果有没遍历到的节点，那就return-1
        for (int i=0; i<n;i++){
            if (!visited[i]){
                System.out.print(-1);
                return;
            }
        }
        System.out.print(1);
    }
    //dfs的意思是：从key遍历起，遍历整个图，把整个图都标记成“visited”true/false
    public static void dfs(boolean[] visited, int key){
        if (visited[key]){
            return;
        }
        visited[key] = true;
        List<Integer> nextKeys = adjList.get(key);
        for (int nextKey: nextKeys){
            dfs(visited,nextKey);
        }
    }
}
```
### bfs
```java
import java.util.*;

public class Main{
    static List<LinkedList<Integer>> adjList = new LinkedList<>();

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        
        for (int i=0; i<n;i++){
            adjList.add(new LinkedList<Integer>());
        }
        for (int i=0;i<k;i++){
            int s = sc.nextInt();
            int t = sc.nextInt();
            //这里注意adjList外层下标是0-n-1,所以我们给内容的下标也要标成n-1
            adjList.get(s-1).add(t-1);
        }
        boolean[] visited = new boolean[n];
        //遍历一次
        bfs(visited,0);
        //如果有没遍历到的节点，那就return-1
        for (int i=0; i<n;i++){
            if (!visited[i]){
                System.out.print(-1);
                return;
            }
        }
        System.out.print(1);
    }
    //bfs的意思也是：从key遍历起，遍历整个图，把整个图都标记成“visited”true/false
    public static void bfs(boolean[] visited, int key){
        Queue<Integer> que = new LinkedList<Integer>();
        que.add(key);
        visited[key] = true;
        while (!que.isEmpty()){
            int curKey = que.poll();
            List<Integer> list = adjList.get(curKey);
            for (int nextKey: list){
                if (!visited[nextKey]){
                    visited[nextKey] = true;
                    que.add(nextKey);
                }
            }
            
        }
    }
}
```

## km106. 岛屿的周长
* https://kamacoder.com/problempage.php?pid=1178
* 文章：https://www.programmercarl.com/kamacoder/0106.%E5%B2%9B%E5%B1%BF%E7%9A%84%E5%91%A8%E9%95%BF.html#%E6%80%9D%E8%B7%AF
* 思路：遍历岛屿的每一个格子：上下左右方向，如果碰到水或者出界，说明找到一条边，周长++。不然的话就是找到了陆地，走到下一个格子
### dfs
```java
import java.util.*;
public class Main{
    static int n;
    static int m;
    static final int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};
    static int peri = 0;
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();
        int[][] grid = new int[n][m];
        for (int i = 0; i<n;i++){
            for (int j=0;j<m;j++){
                grid[i][j] = sc.nextInt();
            }
        }
        boolean[][] visited = new boolean[n][m];
        for (int i =0;i<n;i++){
            for (int j=0; j<m;j++){
                if (!visited[i][j] && grid[i][j]==1){
                    visited[i][j]=true;
                    dfs(grid,visited,i,j);
                }
            }
        }
        System.out.print(peri);
    }

    public static void dfs (int[][] grid, boolean[][] visited, int x, int y){
        for (int i=0;i<4;i++){
            int nextX = x + dir[i][0];
            int nextY = y + dir[i][1];

            if (nextX <0 || nextY <0 || nextX>=n || nextY >=m || grid[nextX][nextY]==0){
                peri ++;
                continue;
                //这里不能直接return！因为我们还要遍历其他的方向
                //加了continue吗就会直接进入下一个i的值的for循环步骤，不会继续执行下面的if，就放值了越界访问
            }
            if (!visited[nextX][nextY]&&grid[nextX][nextY]==1){
                visited[nextX][nextY]=true;
                dfs(grid,visited,nextX,nextY);
            }
        }
    }
}
```
### bfs
```java
import java.util.*;
public class Main{
    static int n;
    static int m;
    static final int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};
    static int peri = 0;
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();
        int[][] grid = new int[n][m];
        for (int i = 0; i<n;i++){
            for (int j=0;j<m;j++){
                grid[i][j] = sc.nextInt();
            }
        }
        boolean[][] visited = new boolean[n][m];
        for (int i =0;i<n;i++){
            for (int j=0; j<m;j++){
                if (!visited[i][j] && grid[i][j]==1){
                    visited[i][j]=true;
                    bfs(grid,visited,i,j);
                }
            }
        }
        System.out.print(peri);
    }

    public static void bfs (int[][] grid, boolean[][] visited, int x, int y){
        Queue<int[]> que = new LinkedList<>();
        que.add(new int[] {x,y});
        while (!que.isEmpty()){
            int[] curXY = que.poll();
            int currX = curXY[0];
            int currY = curXY[1];
            for (int i=0;i<4;i++){
                int nextX = currX + dir[i][0];
                int nextY = currY + dir[i][1];

                if (nextX <0 || nextY <0 || nextX>=n || nextY >=m || grid[nextX][nextY]==0){
                    peri ++;
                    continue;
                }
                if (!visited[nextX][nextY]&&grid[nextX][nextY]==1){
                    que.add(new int[]{nextX,nextY});
                    visited[nextX][nextY]=true;
                }
            }
        }
        
    }
}
```
