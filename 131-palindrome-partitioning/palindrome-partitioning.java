// class Solution {
//     List<List<String>> res = new LinkedList<>();
//     List<String> path = new LinkedList<>();

//     public List<List<String>> partition(String s) {
//         StringBuilder sb = new StringBuilder();
//         backtrack(s,sb,0);
//         return res;

//     }
//     private void backtrack(String s, StringBuilder sb, int startIndex){
//         if (startIndex == s.length()){
//             res.add(new LinkedList<>(path));
//             return;
//         }
//         for (int i = startIndex; i <s.length();i++){
//             sb.append(s.charAt(i));
//             if (isPalindrome(sb)){
//                 //是的话，path里就要加上了
//                 path.add(sb.toString());
//                 backtrack(s,new StringBuilder(),i+1);
//                 path.remove(path.size()-1);
//             }
//             sb.deleteCharAt(sb.length()-1);
//         }
        
//     }

//     private boolean isPalindrome(StringBuilder sb){
//         for (int i = 0; i< sb.length()/2;i++){
//             if (sb.charAt(i) != sb.charAt(sb.length()-1-i)) return false;
//         }
//         return true;
//     }
// }
class Solution {
  List<List<String>> res = new ArrayList<>();
  List<String> path = new ArrayList<>();
  public List<List<String>> partition(String s) {
      backtracking(s, 0, new StringBuilder());
      return res;
  }

  private void backtracking(String s, int startIndex, StringBuilder sb){
      if (startIndex == s.length()){
          res.add(new ArrayList<>(path));
      }
      for (int i = startIndex; i<s.length();i++){
          sb.append(s.charAt(i));
          if (isPalindrome(sb)){
              path.add(sb.toString());
              //遇到了合适的结果才递归
              backtracking(s,i+1,new StringBuilder());
              //这里撤销意味着这条path的内容已经满了，回溯把path撤回空的状态，好找下一种切割方式切出来path的结果
              path.remove(path.size()-1);
          }
      }

  }

  private boolean isPalindrome(StringBuilder sb){
      for(int i = 0; i<sb.length()/ 2; i++){
          if (sb.charAt(i)!= sb.charAt(sb.length()-1-i)){return false;}
      }
      return true;
  }
}