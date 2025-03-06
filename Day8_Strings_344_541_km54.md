# Strings 
* 写的中途草稿没有保存下来丢失了，目前只记录我的代码

## 344. Reverse String
* https://leetcode.com/problems/reverse-string/description/
* 文章： https://programmercarl.com/0344.%E5%8F%8D%E8%BD%AC%E5%AD%97%E7%AC%A6%E4%B8%B2.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 视频： https://www.bilibili.com/video/BV1fV4y17748?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 自己写出来了，双指针
```java
class Solution {
    public void reverseString(char[] s) {
        int i = 0;
        int j = s.length -1;
        while ( i < j){
            char temp1 = s[i];
            char temp2 = s[j];
            s[i] = temp2;
            s[j] = temp1;
            i++;
            j--;
        }
    }
}
```
## 541. Reverse String II
* https://leetcode.com/problems/reverse-string-ii/
* 文章：https://programmercarl.com/0541.%E5%8F%8D%E8%BD%AC%E5%AD%97%E7%AC%A6%E4%B8%B2II.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 视频： https://www.bilibili.com/video/BV1dT411j7NN/?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5
### 我的解法
* 时间复杂度，空间复杂度=O(n) 因为需要额外造一个char array
```java
class Solution {
    public String reverseStr(String s, int k) {
        char [] sArray = s.toCharArray();
        //这里学一下迭代非i++的情况
        for (int i =0; i<sArray.length; i += 2*k){
            int j = i;
            int l;
            //根据剩余的数量决定右边指针放哪里
            //k<剩余量<2k其实不用管，因为我们只需要反转每段的前k个
            if (s.length() - i <k){
                l= s.length()-1;
            }else {
                l = j + k - 1;
            }
            while (j<l){
                char temp1 = sArray[j];
                char temp2 = sArray[l];
                sArray[j]=temp2;
                sArray[l]=temp1;
                //记得更新
                j++;
                l--;
            }
        }
        //这里注意不是用sArray.toString();会返回内存地址
        //新建一个string
        return new String(sArray);
    }
}
```
### 代码随想录的解1： 使用string buffer
* stringbuffer就是mutable string
```java
class Solution {
    public String reverseStr(String s, int k) {
        StringBuffer res = new StringBuffer();
        int length = s.length();
        int start = 0;
        while (start < length) {
            // 找到k处和2k处
            StringBuffer temp = new StringBuffer();
            // 与length进行判断，如果大于length了，那就将其置为length
            int firstK = (start + k > length) ? length : start + k;
            int secondK = (start + (2 * k) > length) ? length : start + (2 * k);

            //无论start所处位置，至少会反转一次
            temp.append(s.substring(start, firstK));
            res.append(temp.reverse());

            // 如果firstK到secondK之间有元素，这些元素直接放入res里即可。
            if (firstK < secondK) { //此时剩余长度一定大于k。
                res.append(s.substring(firstK, secondK));
            }
            start += (2 * k);
        }
        return res.toString();
    }
}
```
### 解法2: 使用xor位运算，就不需要temp来储存值了
```java
//解法二（似乎更容易理解点）
//题目的意思其实概括为 每隔2k个反转前k个，尾数不够k个时候全部反转
class Solution {
    public String reverseStr(String s, int k) {
        char[] ch = s.toCharArray();
        for(int i = 0; i < ch.length; i += 2 * k){
            int start = i;
            //这里是判断尾数够不够k个来取决end指针的位置
            int end = Math.min(ch.length - 1, start + k - 1);
            //用异或运算反转 
            while(start < end){
                ch[start] ^= ch[end];
                ch[end] ^= ch[start];
                ch[start] ^= ch[end];
                start++;
                end--;
            }
        }
        return new String(ch);
    }
}
```
### 解法3:自己写一个reverse函数
```java
class Solution {
    public String reverseStr(String s, int k) {
        char[] ch = s.toCharArray();
        // 1. 每隔 2k 个字符的前 k 个字符进行反转
        for (int i = 0; i< ch.length; i += 2 * k) {
            // 2. 剩余字符小于 2k 但大于或等于 k 个，则反转前 k 个字符
            if (i + k <= ch.length) {
                reverse(ch, i, i + k -1);
                continue;
            }
            // 3. 剩余字符少于 k 个，则将剩余字符全部反转
            reverse(ch, i, ch.length - 1);
        }
        return  new String(ch);

    }
    // 定义翻转函数
    public void reverse(char[] ch, int i, int j) {
    for (; i < j; i++, j--) {
        char temp  = ch[i];
        ch[i] = ch[j];
        ch[j] = temp;
    }

    }
}
```

## 卡码网54. 替换数字（第八期模拟笔试）
* https://kamacoder.com/problempage.php?pid=1064
* 文章： https://programmercarl.com/kamacoder/0054.%E6%9B%BF%E6%8D%A2%E6%95%B0%E5%AD%97.html#%E6%80%9D%E8%B7%AF
* 要点： 用string buffer 需要dynamically adjsut the size of the string buffer lowering performance
* 为什么预扩容后从后向前填充：因为我们已经把旧的数组拷贝进了新的空数组，如果从前向后的话就会覆盖，很麻烦
* 太累了没自己做出来 重刷的时候温习
### 代码随想录解法
```java
import java.util.Scanner;
public class Main{
    public static String replaceNumber(String s){
        int count=0;
        int sOldSize = s.length();
        for (int i=0; i<s.length();i++){
            if (Character.isDigit(s.charAt(i))){
                count++;
            }
        }
        // 扩充字符串s的大小，也就是每个空格替换成"number"之后的大小
        char [] newS = new char[s.length() + count*5];
        int sNewSize = newS.length;

        //java提供的驻足拷贝方法
        //// 从后先前将空格替换为"number"
        System.arraycopy(s.toCharArray(),0,newS,0,sOldSize);
        for (int i = sNewSize - 1, j = sOldSize - 1; j < i; j--, i--) {
            if (!Character.isDigit(newS[j])) {
                newS[i] = newS[j];
            } else {
                newS[i] = 'r';
                newS[i - 1] = 'e';
                newS[i - 2] = 'b';
                newS[i - 3] = 'm';
                newS[i - 4] = 'u';
                newS[i - 5] = 'n';
                i -= 5;
            }
        }
        return new String(newS);
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.next();
        System.out.println(replaceNumber(s));
        scanner.close();
    }
}
```
### 使用stringbuffer （gpt）
```java
import java.util.Scanner;

public class Main {
    public static String replaceNumber(String s) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                sb.append("number");
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.next();
        System.out.println(replaceNumber(s));
        scanner.close();
    }
}
```
