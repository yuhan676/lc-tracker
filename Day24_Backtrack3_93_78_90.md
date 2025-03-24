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
## 78. Subsets：
* https://leetcode.com/problems/subsets/description/
* 文章：https://programmercarl.com/0078.%E5%AD%90%E9%9B%86.html#%E7%AE%97%E6%B3%95%E5%85%AC%E5%BC%80%E8%AF%BE
* 视频：https://www.bilibili.com/video/BV1U84y1q7Ci?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 子集问题收集所有节点，但是之前的组合和分割问题收集的是所有叶子节点
* 自己问题本质上也是组合问题，因为12和21算是同样的结果
* 所以每到一个节点，都要paht.add(nums[i])
* 组建res的规律：先res.add(path)（确保第一个空元素也被加进去）,然后每遇到一个节点path都要加上这个节点，然后递归（startindex+1)，回溯
* 回溯三部曲
	 * 全局变量path为子集手机元素，res存放子集组合
	 * 参数：nums, startIndex
	 * 终止条件：剩余集合为空，到达叶子节点，startIndex>=num.size()的时候就可以return（）了，但其实可以不加，因为startIndex >= nums.size()，本层for循环本来也结束了。
 	 * 单层循环逻辑：**子集问题不需要任何剪枝，需要遍历整棵树** for(int i = startIndex; i<num.size(); i++){path.add(num[i]);处理节点 backtrack(nums,i+1)；递归 path.remove(path.size()-1)；回溯
* 时间复杂度: O(n * 2^n)
* 空间复杂度: O(n)
* 没搞懂的地方：为什么终止条件可有可无，而且为什么他的位置放在res.addpath以后会导致错误
```java
class Solution {
    List<List<Integer>> res = new ArrayList<>();
    List<Integer> path = new ArrayList<>();

    public List<List<Integer>> subsets(int[] nums) {
        backtrack(nums,0);
        return res;
    }

    private void backtrack(int[]nums, int startIndex){
        res.add(new ArrayList<>(path));
        if (startIndex >= nums.length) return;//可有可无，但一定要放在上一条后面，否则最后那一层的子集不会加入，导致部分结果丢失


        for (int i = startIndex; i<nums.length; i++){
            path.add(nums[i]);
            backtrack(nums,i+1);
            path.remove(path.size()-1);
        }
    }
}
```
## 90. Subsets II
* https://leetcode.com/problems/subsets-ii/description/
* 文章：https://programmercarl.com/0090.%E5%AD%90%E9%9B%86II.html#%E5%85%B6%E4%BB%96%E8%AF%AD%E8%A8%80%E7%89%88%E6%9C%AC
* 视频： https://www.bilibili.com/video/BV1vm4y1F71J?vd_source=62d2d5517cc65d630d19b32ed3dcf9c5&spm_id_from=333.788.videopod.sections
* 本题和40.组合总和很像，都需要在for循环里去重，但是递归的时候不去重，就能保证每个元素只能被用一次
*![image](https://github.com/user-attachments/assets/0beccc5d-3fcb-43e3-bf84-981047023123)
```java
class Solution {
    List<List<Integer>> res = new ArrayList<>();
    List<Integer> path = new ArrayList<>();
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        backtrack(nums,0);
        return res;
    }
    private void backtrack(int[]nums, int startIndex){
        res.add(new ArrayList<>(path));
        for (int i = startIndex; i<nums.length; i++){
            //这里注意i是大于startindex不是大于0，不然会跳过一些有效的层
            if (i>startIndex && nums[i]==nums[i-1]){continue;}
            path.add(nums[i]);
            backtrack(nums,i+1);
            path.remove(path.size()-1);
        }
    }
}
```
