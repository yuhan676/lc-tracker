## 93. Restore IP Addresses
* https://leetcode.com/problems/restore-ip-addresses/
* 文章： https://programmercarl.com/0093.%E5%A4%8D%E5%8E%9FIP%E5%9C%B0%E5%9D%80.html
* 视频：https://leetcode.com/problems/restore-ip-addresses/
* 我的思路：
  * 和回文子串有点像的切割问题：先用[startIndex,i]找到所有的分隔方式，如果判断符合的话就加入path，分割成四份了就res.add(path)收割结果
  * 难点：如何保证每个path里一定是有连贯性的切割的结果呢？答案：用递归（startindex+1)就可以保证不重复分割
  * ![image](https://github.com/user-attachments/assets/57fd7bb3-5e5a-478f-a84d-0469c18c441d)
  * 难点：如何保证一定分割成4个int呢？答案：var pointnum 记录添加逗点的数量，==3就意味着分割成四份了
  * 难点：单层逻辑处理是否符合条件：如果符合才继续递归。因为如果当前切割方式不符合，那就没必要继续递归了，如果继续递归也只能产出不符合条件的结果
  * 难点，加入和减去逗点之后如何处理递归中startindex和i的位置
  * 难点：什么时候只用全局变量res，什么时候使用全局变量res和stringbuilder（如果想直接进行string.substring() + "."处理res可以不用stringbuilder全局变量）
  * 学习点:String.substring(startindex, endindex +1),here start & end are inclusive; also endindex is optional 
* 递归三部曲
  * 返回值：void
  * 参数：string s，startindex， pointNum（记录已经加入的逗点的个数）
  * 递归终止条件：和分割回文子串不一样，不需要startindex=string.lengt(),而是逗点有三个就说明分割成四段了。这个时候需要检查最后一段的合法性，如果合理则把加入了3个逗点的string加入path
  * 单层搜索逻辑：单层搜索for (i=startindex; i<s.size(); i++)中判断[startIndex, i]是否合法，合法就在字符串后面加上符号表示已经分割，pointNum++，不合法就结束本层循环，比如图中剪掉的分支
  * ![image](https://github.com/user-attachments/assets/553f1115-7369-410a-a180-88b4c7ae9c70)
  * 如果使用string，不用stringbuilder，下一层的startindex要从i+2开始（因为刚才加入了逗点）
  * 判断子串是否合法
    * 段位以0开头且不为0，不合法
    * 其余两个需要一个一个遍历s.charat（i）来判断（int num=0; for (int i=start; i <= end; i++)
    * 段位里有非正整数字符不合法 if (s.charAt(i) >'9' || s.charAt(i) <'0'
      * 如果 s.charAt(i) > '9'，说明字符比 '9' 大，例如 'A', 'b', '@', '{' 等。
      * 如果 s.charAt(i) < '0'，说明字符比 '0' 小，例如 ' ', '.', '-', '(' 等。
    * 段位大于255，不合法  { num= num*10 + (s.charAt(i)-'0')}. if num>255, return false
  ### 代码随想录解法1:直接更改string，不使用stringbuilder
  ```java
  class Solution {
    List<String> res = new ArrayList<>();

    public List<String> restoreIpAddresses(String s) {
        if (s.length()>12) return res; //剪枝，每个段落最大只能有3个digit，一共四个段落
        backtrack(s, 0, 0);
        return res;
    }
    private void backtrack(String s, int startIndex, int dotCount){
        if (dotCount==3){
            if (isValid(s,startIndex, s.length()-1)){
                res.add(s);//这里s是已经加入了3个逗点的被分割成4段，且每段都合法的结果
            }
            return; //这里记得一定要return，防止多余的递归
        }
        for (int i = startIndex; i<s.length(); i++){
            //如果当前检查的子串合法，我们只是加入逗点，并不对res进行操作
            if (isValid(s,startIndex,i)){
                s = s.substring(0,i+1) + "." + s.substring(i+1);//这里s还没有变化，所以后半段的起始位置仍然是i+1
                dotCount++;
                backtrack(s,i+2,dotCount);//现在已经加入逗点，所以要从逗点后一位开始递归
                //回溯
                s = s.substring(0,i+1) + s.substring(i+2);//删除逗点（记得画图理解）
                dotCount--;
            } else{break;}//这里记得处理如果不符合，就终止当前层循环
        }
    }
    private boolean isValid(String s, int start, int end){
        if (start > end) return false;//这个条件如果不加上会导致越界
        //开头为0但数值不为0，不合法
        //这里注意start!=end比较的是指针位置
        if (s.charAt(start)=='0' && start!=end) return false;
        int num = 0;
        for (int i = start; i<=end; i++){
            if (s.charAt(i) < '0' || s.charAt(i)> '9') return false;
            num = num * 10 + (s.charAt(i) - '0');
        }
        if (num > 255) return false;
        return true;
    }
}

### 使用stringbuilder的解法
* 优化了时间和空间复杂度
* 插入字符的时候无需复制整个字符串，减少了操作的时间复杂度
* 不用开新空间存substring，减少了空间复杂度
```java
class Solution {
    List<String> res = new ArrayList<>();
    
    public List<String> restoreIpAddresses(String s) {
        //复制s成为stringbuilder
        StringBuilder sb = new StringBuilder(s);
        backtrack(sb, 0, 0);
        return res;
    }
    private void backtrack (StringBuilder s, int startIndex, int dotCount){
        if (dotCount == 3){
            if (isValid(s,startIndex,s.length()-1)){
                res.add(s.toString());
            }
            return;
        }
        for (int i = startIndex; i<s.length();i++){
            if (isValid(s,startIndex,i)){
                //这里注意insert是在位置上加入一个char
                s.insert(i+1,'.');
                //这里传入当前层的count+1，导致每一层都维护了自己的dotcount数值，因此回溯的时候不用dotcount--
                backtrack(s, i+2,dotCount+1);
                //这里学习一下stringbuilder的删除操作
                s.deleteCharAt(i+1);
            }else{break;}
        }
    }
    private boolean isValid(StringBuilder s, int start, int end){
        if(start > end)
            return false;
        if(s.charAt(start) == '0' && start != end)
            return false;
        int num = 0;
        for(int i = start; i <= end; i++){
            int digit = s.charAt(i) - '0';
            num = num * 10 + digit;
            if(num > 255)
                return false;
        }
        return true;
    }
}
```
### 代码随想录：使用stringbuilder但是更优化剪枝
```java
class Solution {
    List<String> result = new ArrayList<String>();
	StringBuilder stringBuilder = new StringBuilder();

	public List<String> restoreIpAddresses(String s) {
		restoreIpAddressesHandler(s, 0, 0);
		return result;
	}

	// number表示stringbuilder中ip段的数量
	public void restoreIpAddressesHandler(String s, int start, int number) {
		// 如果start等于s的长度并且ip段的数量是4，则加入结果集，并返回
		if (start == s.length() && number == 4) {
			result.add(stringBuilder.toString());
			return;
		}
		// 如果start等于s的长度但是ip段的数量不为4，或者ip段的数量为4但是start小于s的长度，则直接返回
		if (start == s.length() || number == 4) {
			return;
		}
		// 剪枝：ip段的长度最大是3，并且ip段处于[0,255]
		for (int i = start; i < s.length() && i - start < 3 && Integer.parseInt(s.substring(start, i + 1)) >= 0
				&& Integer.parseInt(s.substring(start, i + 1)) <= 255; i++) {
			if (i + 1 - start > 1 && s.charAt(start) - '0' == 0) {
				break;
			}
			stringBuilder.append(s.substring(start, i + 1));
			// 当stringBuilder里的网段数量小于3时，才会加点；如果等于3，说明已经有3段了，最后一段不需要再加点
			if (number < 3) {
				stringBuilder.append(".");
			}
			number++;
			restoreIpAddressesHandler(s, i + 1, number);
			number--;
			// 删除当前stringBuilder最后一个网段，注意考虑点的数量的问题
			stringBuilder.delete(start + number, i + number + 2);
		}
	}
}
```
