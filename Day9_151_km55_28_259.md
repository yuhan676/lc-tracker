# Day 9 Strings 2
## 151. Reverse Words in a String
* https://leetcode.com/problems/reverse-words-in-a-string/description/
* 文章： https://programmercarl.com/0151.%E7%BF%BB%E8%BD%AC%E5%AD%97%E7%AC%A6%E4%B8%B2%E9%87%8C%E7%9A%84%E5%8D%95%E8%AF%8D.html
* 视频： https://www.bilibili.com/video/BV1uT41177fX?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
### 我的写法
* 学习调用库函数
* 学习多个空格处理：regex \\s+
* 需要额外调用储存空间string buffer，力扣没有要求空间复杂度o1
```java
class Solution {
    public String reverseWords(String s) {
        //用trim来把头一个空格砍掉，用库函数split
        String [] splitS = s.trim().split ("\\s+");//这里使用“ ”就不能处理连续两个空格的情况。这里的regex expression 处理了一个以上的空格情况，\\s就只能处理一个

        //用string buffer来储存结果
        StringBuffer res = new StringBuffer();

        for (int i = splitS.length-1; i >=1; i--){
            res.append(splitS[i]);
            res.append(" ");
        }
        //最后一个不能有空格
        res.append(splitS[0]);
        String result = res.toString();

        return result;
    }
}
```
### 代码随想录
* 思路：先整个字符串反转，再识别单个word，每个word进行自身反转（可使用库函数reverse）
* 如果语言允许string 更改，可以做到空间复杂度o1，不需要生成新的字符串（对java不适用，java必须生成新的，但是至少这样做不会用到stringbuffer）
* 难点：处理多余的空格
```java
class Solution {
    public String reverseWords(String s) {
            //和stringbuffer的区别：string builder适用于单线程但是消耗时间少，但是线程不安全（非synchronised）
            //去除首位及中间多余空格
            StringBuilder sb = removeSpace(s);
            //反转整个字符串
            reverseString(sb, 0, sb.length()-1);
            //反转各个单词
            reverseEachWord(sb);
            return sb.toString();
        }

        private StringBuilder removeSpace(String s){
            int start = 0;
            int end = s.length() -1;
            while (s.charAt(start) == ' ') start++;
            while (s.charAt(end) ==' ')end--;
            StringBuilder sb = new StringBuilder();

            while (start <=end){
                char c = s.charAt(start);
                //如果c不是空格，添加，如果c是空格，只有在前一个不是空格的时候才添加
                if (c!=' ' || sb.charAt(sb.length()-1) !=' '){
                    sb.append(c);
                }
                start++;
            }

            return sb;
        }

        public void reverseString(StringBuilder sb, int start, int end){
            while (start<end){
                char temp = sb.charAt(start);
                sb.setCharAt(start, sb.charAt(end));
                sb.setCharAt(end, temp);
                start ++;
                end --;
            }
        }
        private void reverseEachWord(StringBuilder sb){
            int start = 0;
            int end = 1;
            int n = sb.length();

            while (start < n){
                while (end <n && sb.charAt(end)!= ' '){
                    end++;
                }
                reverseString(sb,start,end-1);
                start = end+1;
                end = start+1;
            }
        }
    }
```
## 卡码网55. 右旋字符串（第八期模拟笔试）
* https://kamacoder.com/problempage.php?pid=1065
* 文章： https://programmercarl.com/kamacoder/0055.%E5%8F%B3%E6%97%8B%E5%AD%97%E7%AC%A6%E4%B8%B2.html

### 我的解法：使用stringbuilder
* java 一定得申请新空间，所以没办法直接在本串上操作 
```java
import java.util.Scanner;

class Main{
    public static void main (String[] args){

        Scanner scanner = new Scanner(System.in);

        int k = scanner.nextInt();

        scanner.nextLine(); // 吸收换行符，防止 nextLine() 读取空字符串

        String s = scanner.nextLine();
        
        StringBuilder res = new StringBuilder();
        int i = s.length()-k;

        for (; i<s.length(); i++){
            res.append(s.charAt(i));
        }

        for (int j = 0; j<s.length()-k; j++){
            res.append(s.charAt(j));
        }

        System.out.println(res.toString());
        scanner.close();
    }
}

```

### 代码随想录解法： 
* 和前一题151一样:先整体反转整个字符串，在反转前k个，再反转后length-k个
```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        //注意这里用了Integer.parseInt，比我的nextInt写法更加优雅
        int n = Integer.parseInt(in.nextLine());
        String s = in.nextLine();

        int len = s.length();  //获取字符串长度
        char[] chars = s.toCharArray();
        reverseString(chars, 0, len - 1);  //反转整个字符串
        reverseString(chars, 0, n - 1);  //反转前一段字符串，此时的字符串首尾尾是0,n - 1
        reverseString(chars, n, len - 1);  //反转后一段字符串，此时的字符串首尾尾是n,len - 1
        
        System.out.println(chars);

    }

    public static void reverseString(char[] ch, int start, int end) {
        //异或法反转字符串，参照题目 344.反转字符串的解释
        while (start < end) {
            ch[start] ^= ch[end];
            ch[end] ^= ch[start];
            ch[start] ^= ch[end];
            start++;
            end--;
        }
    }
}
```
## 28. Find the Index of the First Occurrence in a String （KMP第一题）
 * **二刷时重新过，自己没有写出来**
* https://leetcode.com/problems/find-the-index-of-the-first-occurrence-in-a-string/description/
* 文章： https://programmercarl.com/0028.%E5%AE%9E%E7%8E%B0strStr.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 视频：
  * 代码实现： https://www.bilibili.com/video/BV1M5411j7Xx?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
  * 理论：https://www.bilibili.com/video/BV1PD4y1o7nd?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 注意：如果模式串needle是空字符串，应当返回0
  * 这是由标准库函数的定义决定的，许多语言（包括 C、Java、Python）都这样实现：
  * Java："abcdef".indexOf("") 返回 0。
* 思路：kmp算法：使用前缀表prefix table（所谓的next 数组）找到冲突位置时，模式串前一位的前缀值（最长相等前缀&后缀值），然后将模式串上的比指针移到那个值的位置（e.g.最长前后缀相等是2，那指针就移动到2）
* 前缀表的任务是当前位置匹配失败，找到之前已经匹配上的位置，再重新匹配，此也意味着在某个字符失配时，前缀表会告诉你下一步匹配中，模式串应该跳到哪个位置。
* 什么是前缀表： 记录下标i之前（包括i）的字符串中，有多大长度的相同前缀后缀。
* ![Screenshot 2025-03-08 at 23 26 53](https://github.com/user-attachments/assets/960b02e6-0960-49ca-9d64-771410d233f3)
* next函数与前缀表：next数组就可以是前缀表，但是很多实现都是把前缀表统一减一（右移一位，初始位置为-1）之后作为next数组。一共有两种不同的实现方式
* ![Screenshot 2025-03-08 at 23 29 56](https://github.com/user-attachments/assets/950724ab-6033-4c30-ac53-9e53e822e34b)
* 直接用前缀表，就得找冲突位前一位的前缀表值。如果用了-1右移，那就直接用冲突位的前缀表值就可以啦
* 具体构造getnext函数：直接用前缀表（这里getnext的意思是，get next index of 模式串needle for comparison)
* next数组就是： index = index of 模式串，value = value of 这个位置的前缀表值
* 步骤：
  * 初始化，
  * 处理前后缀不同情况
  * 处理前后缀相同情况
  * 更新前缀表
* 初始化 （直接从长度为2的模式串子串开始初始化，设定i和j，因为字符串长度为1的时候不存在前缀与后缀的概念）
  * j指向前缀末尾（也代表i和i之前的子串的最长相等前后缀的长度），初始化为0
  * i指向后缀末尾
  * next数组初始化： next[0] = 0
  *  i初始化成1，因为我们没必要从index0开始对比i和j，直接从长度为2的模式串子串开始遍历。 for (i=i; i<s.length; i++)
* 处理前后缀不相同的情况
  * 像我们之前讨论过的，如果发生冲突，就查看冲突位前一位的next数组的值然后跳到应该会退的位置 （除非j已经是0了，那就不用回退了，不然会越界）
  * 继续冲突就继续回退，while (j>0 && s[i]! s[j] ) {j = next[j-1]}
* 处理前后缀相同的情况
  * if (s[i] == s[j]_ ) {j++}
* 更新next数组 (记得j是储存了i和i之前子串最长相等前后缀的长度）
  * next[i]  = j
  * 在i的位置，前缀值为j
  * for循环结束
 ### 暴力解法：两个for循环遍历O(m*n),m为模式串长度，n为字符串长度
```java
class Solution {
    /**
	牺牲空间，换取最直白的暴力法
        时间复杂度 O(n * m)
        空间 O(n + m)
     */
    public int strStr(String haystack, String needle) {
        // 获取 haystack 和 needle 的长度
        int n = haystack.length(), m = needle.length();
        // 将字符串转换为字符数组，方便索引操作
        char[] s = haystack.toCharArray(), p = needle.toCharArray();

        // 遍历 haystack 字符串
        for (int i = 0; i < n - m + 1; i++) {
            // 初始化匹配的指针
            int a = i, b = 0;
            // 循环检查 needle 是否在当前位置开始匹配
            while (b < m && s[a] == p[b]) {
                // 如果当前字符匹配，则移动指针
                a++;
                b++;
            }
            // 如果 b 等于 m，说明 needle 已经完全匹配，返回当前位置 i
            if (b == m) return i;
        }

        // 如果遍历完毕仍未找到匹配的子串，则返回 -1
        return -1;
    }
}
```
### 使用前缀表（未减一）作为getnext函数的解法
```java
class Solution {
    //前缀表（不减一）Java实现
    public int strStr(String haystack, String needle) {
        if (needle.length() == 0) return 0;
        int[] next = new int[needle.length()];
        getNext(next, needle);

        int j = 0;
        for (int i = 0; i < haystack.length(); i++) {
            while (j > 0 && needle.charAt(j) != haystack.charAt(i)) 
                j = next[j - 1];
            if (needle.charAt(j) == haystack.charAt(i)) 
                j++;
            if (j == needle.length()) 
                return i - needle.length() + 1;
        }
        return -1;

    }
    
    private void getNext(int[] next, String s) {
        int j = 0;
        next[0] = 0;
        for (int i = 1; i < s.length(); i++) {
            while (j > 0 && s.charAt(j) != s.charAt(i)) 
                j = next[j - 1];
            if (s.charAt(j) == s.charAt(i)) 
                j++;
            next[i] = j; 
        }
    }
}
```
## 459. Repeated Substring Pattern (KMP 第二题）
* **二刷时重新过，自己没有写出来**
* https://leetcode.com/problems/repeated-substring-pattern/
* 文章： https://programmercarl.com/0459.%E9%87%8D%E5%A4%8D%E7%9A%84%E5%AD%90%E5%AD%97%E7%AC%A6%E4%B8%B2.html
* 视频： https://www.bilibili.com/video/BV1cg41127fw?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 暴力解法： 一个for循环找到子串的结束位置，下一个for循环去比较主串，O(n^2)
  * 为什么暴力解法只用一个for循环就能找到子串的开始和结束位置？？不需要两个吗？
  * 因为我们可以默认子串一定从开头开始，子串必须包括最前面，因为字符串是有重复的子串开始的
  * 枚举每一个可能的子串，然后对比它能不能通过重复组成目标串
### gpt暴力解法
* 子串一定size能被目标串size整除
* 子串一定小于目标串/2
```java
class Solution {
    public boolean repeatedSubstringPattern(String s) {
        int n = s.length();
        
        // 遍历所有可能的子串长度 l
        for (int l = 1; l <= n / 2; l++) {
            // 只有当 l 是 s.length() 的因数时，才有可能由重复子串构造
            if (n % l == 0) {
                String sub = s.substring(0, l); // 取长度 l 的前缀作为候选子串
                int repeatCount = n / l;
                StringBuilder sb = new StringBuilder();
                
                // 构造完整字符串
                for (int i = 0; i < repeatCount; i++) {
                    sb.append(sub);
                }

                // 判断是否和 s 相等
                if (sb.toString().equals(s)) {
                    return true;
                }
            }
        }
        
        return false;
    }
}
```
### 移动匹配 + kmp解法
* 思路：如果一个字符串s是由重复子串组成的，那s+s的中间部分必须能构成一个s, 因为一定有相等的前后缀子串，不一定是从中间劈开就相等 (e.g. ababab 的前缀 [0,3] 和后缀 [2,5] 都是abab）
* e.g. abcabc + abcabc = abc **abcabc** abc;
* 这样子的话，只要ss=s+s, 再erase掉ss的第一个char和最后一个char就可以检查s+s中间有没有等于s的子串了（erase是为了不检查到s+s中前一个和后一个s两个本身等于s的子串）
* 然后直接用ss.find(s) 或者ss.contains(s)的库函数就可以啦，或者可以自己用kmp来手搓contains库函数
* 那具体find和contains的库函数是怎么实现的呢？？用kmp算法：判断字符串之中是否contains子串
* **以下是代码随想录的实现方法： 注意它没有直接使用前一题用前缀表直接做getnext的方法，而是初始化j = 0, i = 2, 在二刷的时候可以自己写一遍用前缀表做getnext的方法**
```java
class Solution {
    public boolean repeatedSubstringPattern(String s) {
        if (s.equals("")) return false;

        int len = s.length();
        // 原串加个空格(哨兵)，使下标从1开始，这样j从0开始，也不用初始化了
        s = " " + s;
        char[] chars = s.toCharArray();
        int[] next = new int[len + 1];

        // 构造 next 数组过程，j从0开始(空格)，i从2开始
        for (int i = 2, j = 0; i <= len; i++) {
            // 匹配不成功，j回到前一位置 next 数组所对应的值
            while (j > 0 && chars[i] != chars[j + 1]) j = next[j];
            // 匹配成功，j往后移
            if (chars[i] == chars[j + 1]) j++;
            // 更新 next 数组的值
            next[i] = j;
        }

        // 最后判断是否是重复的子字符串，这里 next[len] 即代表next数组末尾的值
        if (next[len] > 0 && len % (len - next[len]) == 0) {
            return true;
        }
        return false;
    }
}
```

## 字符串总结
* https://programmercarl.com/%E5%AD%97%E7%AC%A6%E4%B8%B2%E6%80%BB%E7%BB%93.html#kmp

## 双指针总结
* https://programmercarl.com/%E5%8F%8C%E6%8C%87%E9%92%88%E6%80%BB%E7%BB%93.html
