## km99. 岛屿数量
* https://kamacoder.com/problempage.php?pid=1171
* 题目中每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。斜角度链接是不算的
### 深度搜索解法
* 视频：https://www.bilibili.com/video/BV18PRGYcEiB?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 文章：https://programmercarl.com/kamacoder/0099.%E5%B2%9B%E5%B1%BF%E7%9A%84%E6%95%B0%E9%87%8F%E6%B7%B1%E6%90%9C.html#%E6%80%9D%E8%B7%AF
* 深度搜索的解法的意思是，从当前格子可以往上下左右检查是不是陆地，如果是的话，就继续调用dfs
* 需要用一个visited二维数组记录当前格子是否已经被遍历过。如果遍历过，就不能在它的基础上再调用dfs了
* 整体思路：
1. 保存图
2. 用visited记录是否格子被遍历
3. dfs的作用是让一个新岛屿的所有格子都被标记成遍历。所以这以后找到的格子如果是陆地且没有被遍历过，就一定是新岛屿
* ![Screenshot 2025-04-22 at 17 34 09](https://github.com/user-attachments/assets/013784e5-e061-4f77-8300-29a73589e461)
* ![Screenshot 2025-04-22 at 17 34 29](https://github.com/user-attachments/assets/d3f09516-8b04-4c2f-80e8-5cf22cc1edd8)
* ![Screenshot 2025-04-22 at 17 35 28](https://github.com/user-attachments/assets/5255cdf0-da6a-4331-bd96-33a36b0e43e1)
* **为什么用adjacent matrix而不是adjacent list表示图**
  * 因为我们要模拟的更接近地图，而不是两个节点（grid[i][j]==1）之间的连接，所以直接用二维数组表达起来看起来更直观
  *  构建邻接表需要额外预处理（ 你得先把所有的格子转换成 node，构建边 → 额外一轮遍历 + map + string 处理）
  *  二维网格天然就是隐式图，你不需要保存边，只需要写四个方向（上下左右）直接走就行，省空间又简单
  *   邻接表的好处在于边是稀疏和动态的
  *   而这里地图是“固定”的，边的结构不变，没有动态删边/加边的场景
  * adjacentlist上下左右邻接的陆地格子是他的邻接节点，所以我们可以先遍历整个 grid，把每个陆地节点加入邻接表，并建立连接关系
  * 比如：![Screenshot 2025-04-22 at 17 48 17](https://github.com/user-attachments/assets/f145d306-5a5f-4582-8607-61624410c835)

```java
import java.util.Scanner;

public class Main{
    //储存方向操作
    static int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        //储存图到grid=n行m列，所以造出来的grid要和输入一样（代码随想录造出m行n列）
        int[][] grid = new int[n][m];
        for (int i=0; i<n;i++){
            for(int j=0;j<m;j++){
                grid[i][j] = sc.nextInt();
            }
        }

        //visited数组记录已经到过的位置
        boolean[][] visited = new boolean[n][m];
        int ans = 0;
        //开始寻找第一个
        for (int i=0;i<n;i++){
            for (int j=0;j<m;j++){
                //这里其实就是终止条件，我们不去遍历不符合条件的格子（海水格子或者已经遍历过的陆地格子），因为我们一定要找到1.从未遍历的格子，2.是陆地，才能保证这个这个岛屿是新的，ans++
                if (!visited[i][j] && grid[i][j]==1){
                    ans ++;
                    visited[i][j] = true;
                    dfs(grid, visited, i,j);
                }
            }
        }
        System.out.print(ans);
    }
    //这个函数的作用是遍历一个岛屿的所有格子并且都标记为“已经到达过”
    public static void dfs(int[][] grid, boolean[][] visited, int x, int y){
        for (int i=0; i<4;i++){
            int nextX = x + dir[i][0];
            int nextY = y + dir[i][1];
            //这里注意，x是行，y是列，所以x不能超过行数（n，grid.length),y不能超过列数（m, grid[0].length)
            if (nextX<0 || nextY<0 || nextX>=grid.length || nextY>=grid[0].length){
            continue;
            }
            if (!visited[nextX][nextY] && grid[nextX][nextY]==1){
                visited[nextX][nextY] = true;
                dfs(grid,visited,nextX,nextY);
            }
        }
        
    }
}
```
### bfs解法
* 文章：https://programmercarl.com/kamacoder/0099.%E5%B2%9B%E5%B1%BF%E7%9A%84%E6%95%B0%E9%87%8F%E5%B9%BF%E6%90%9C.html#%E6%80%9D%E8%B7%AF
* 视频： https://www.bilibili.com/video/BV12QQqYWE6x?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 入队的时候就得标记已经访问，否则会重复访问
* ![image](https://github.com/user-attachments/assets/e91aebfc-9e35-4349-81a6-1a6857e25d0b)
```java
import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;


public class Main{

    public static int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[][] grid = new int[n][m];
        for (int i=0;i<n;i++){
            for (int j=0;j<m;j++){
                grid[i][j] = sc.nextInt();
            }
        }
        boolean[][] visited = new boolean[n][m];
        int ans = 0;
        for (int i=0; i<n; i++){
            for (int j=0;j<m;j++){
                //找到了未访问的陆地格子就找到了一个新的岛屿
                //因为已访问过的陆地格子，一定是之前已经访问过的岛屿陆地
                if (!visited[i][j] && grid[i][j]==1){
                    ans++;
                    //遍历这个新岛屿的所有陆地
                    bfs(grid,visited,i,j);
                }
            }
        }
        System.out.print(ans);

    }
    //目标：找到一个新岛屿以后标记处和它土壤相接的所有格子为已访问
    public static void bfs(int[][] grid, boolean[][] visited, int x, int y){
        //把需要检查的坐标储存在队列里
        Queue<int[]> que= new LinkedList<>();
        que.add(new int[]{x,y});
        //一入队就要标记visited避免重复访问
        visited[x][y] = true;
        while(!que.isEmpty()){
            //取出队里的格子
            int[] curCoordinate = que.poll();
            int curX = curCoordinate[0];
            int curY = curCoordinate[1];
            //顺时针访问它四个方向的临近格子
            for (int i=0;i<4;i++){
                int nextX = curX + dir[i][0];
                int nextY = curY + dir[i][1];
                //如果越界就不会入队
                if (nextX<0 || nextY<0 || nextX>=grid.length || nextY >= grid[0].length){
                    continue;
                }
                //如果不越界，而且是陆地，就入队然后标记访问
                if (!visited[nextX][nextY] && grid[nextX][nextY] ==1){
                    que.add(new int[]{nextX,nextY});
                    visited[nextX][nextY] = true;
                }
            }
        }
    }
}
```
## km100. 岛屿的最大面积
* https://kamacoder.com/problempage.php?pid=1172
* 文章：https://programmercarl.com/kamacoder/0100.%E5%B2%9B%E5%B1%BF%E7%9A%84%E6%9C%80%E5%A4%A7%E9%9D%A2%E7%A7%AF.html#%E6%80%9D%E8%B7%AF
* 视频：https://www.bilibili.com/video/BV1FzoyY5EXH?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 和前两题一样
### dfs解法
```java
import java.util.Scanner;

public class Main{
    static final int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};
    static int maxA = 0;
    static int curA =0;
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[][] grid = new int[n][m];
        for (int i=0; i<n;i++){
            for (int j=0;j<m;j++){
                grid[i][j] = sc.nextInt();
            }
        }
        boolean[][] visited = new boolean[n][m];
        for (int i=0; i<n;i++){
            for (int j=0;j<m;j++){
                //这里是通过for循环搜索和标记新岛屿的陆地
                if (!visited[i][j] && grid[i][j]==1){
                    curA =1;
                    visited[i][j] = true;
                    dfs(grid,visited,i,j);
                    if (curA>maxA){maxA=curA;}

                }
            }
        }
        System.out.print(maxA);
    }
    public static void dfs(int[][] grid, boolean[][] visited, int x, int y){
        for (int i=0;i<4;i++){
            int nextX = x + dir[i][0];
            int nextY = y + dir[i][1];
            if (nextX<0 || nextY <0 || nextX>=grid.length || nextY >= grid[0].length){
            continue;
            }
            //记住这里是搜索和标记同一片岛屿上的陆地
            if (!visited[nextX][nextY] && grid[nextX][nextY]==1){
                visited[nextX][nextY] = true;
                curA++;
                dfs(grid,visited,nextX,nextY);
            }
        }
    }
}
```
### bfs解法
```java
import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;


public class Main{
    static final int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};
    static int maxA = 0;
    static int curA =0;
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[][] grid = new int[n][m];
        for (int i=0; i<n;i++){
            for (int j=0;j<m;j++){
                grid[i][j] = sc.nextInt();
            }
        }
        boolean[][] visited = new boolean[n][m];
        for (int i=0; i<n;i++){
            for (int j=0;j<m;j++){
                //这里是通过for循环搜索和标记新岛屿的陆地
                if (!visited[i][j] && grid[i][j]==1){
                    curA =1;
                    visited[i][j] = true;
                    bfs(grid,visited,i,j);
                    //更新maxA改到已经遍历完当前岛屿所有格子的时候再说
                    //只要我们还在一个岛，bfs和dfs就都不会被curA = 1;覆盖
                    maxA = Math.max(maxA, curA);
                }
            }
        }
        System.out.print(maxA);
    }
    public static void bfs(int[][] grid, boolean[][] visited, int x, int y){
        Queue<int[]> que = new LinkedList<>();
        que.add(new int[]{x,y});
        //一入队马上标记，不要poll的时候再标记访问
        visited[x][y] = true;
        while (!que.isEmpty()){
            int[] curXY = que.poll();
            int curX = curXY[0];
            int curY = curXY[1];
            for (int i=0;i<4;i++){
                int nextX = curX + dir[i][0];
                int nextY = curY + dir[i][1];
                if (nextX <0 || nextY <0 || nextX>=grid.length || nextY >= grid[0].length){
                    //越界的临近格子直接跳过
                    continue;
                }
                if (!visited[nextX][nextY] && grid[nextX][nextY]==1){
                    //如果临近格子符合标准，只要，入队，标记，更新currA就可以，不需要像dfs那样recur。dfs是找到一个符合的格子就从那里开始递归，bfs因为我们一上来就会检查当前格子的四个角落是否能入队，就不需要一条路走到黑，而是走一个格子转一圈
                    que.add(new int[]{nextX, nextY});
                    visited[nextX][nextY] = true;
                    curA++;
                    
                }
            }
        }
        
    }
}
```
