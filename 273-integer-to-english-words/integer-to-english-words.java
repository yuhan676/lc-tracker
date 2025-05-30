class Solution {
    //优化成static map
    static final Map<Integer, String> digit1 = new HashMap<>();
    static final Map<Integer, String> digit2 = new HashMap<>();
    static final Map<Integer,String> level = new HashMap<>();
    //static map initialisation
    static{
         //2^31 是billion级别的，所以最多length只能是10
        digit1.put(1, "One");
        digit1.put(2, "Two");
        digit1.put(3, "Three");
        digit1.put(4, "Four");
        digit1.put(5, "Five");
        digit1.put(6, "Six");
        digit1.put(7, "Seven");
        digit1.put(8, "Eight");
        digit1.put(9, "Nine");
        digit1.put(10, "Ten");
        digit1.put(11, "Eleven");
        digit1.put(12, "Twelve");
        digit1.put(13, "Thirteen");
        digit1.put(14, "Fourteen");
        digit1.put(15, "Fifteen");
        digit1.put(16, "Sixteen");
        digit1.put(17, "Seventeen");
        digit1.put(18, "Eighteen");
        digit1.put(19, "Nineteen");
    
        digit2.put(2, "Twenty");
        digit2.put(3, "Thirty");
        digit2.put(4, "Forty");
        digit2.put(5, "Fifty");
        digit2.put(6, "Sixty");
        digit2.put(7, "Seventy");
        digit2.put(8, "Eighty");
        digit2.put(9, "Ninety");
        
        level.put(0, "");
        level.put(1, "Thousand");
        level.put(2, "Million");
        level.put(3, "Billion");
    }
    public String numberToWords(int num) {
        //Special Case
        if (num==0) return "Zero";
        //简化：不用Integer.parseInt()了，直接num%1000, num /= 1000处理
        int counter = 0;
        StringBuilder sb = new StringBuilder();
        while (num >0){
            int curr3digit = num%1000;
            if (curr3digit != 0){
                sb.insert(0,concat3digit(curr3digit) + " " + level.get(counter) + " ");
            }
            counter ++;
            num /= 1000;
        }
        return sb.toString().trim();
        
    }
    public String concat3digit(int num){

        //简化，直接处理三个digit，不递归
        if (num == 0) return "";
        StringBuilder sb = new StringBuilder();
        int hundred = num/100;
        int remainder = num%100;

        if (hundred!=0){
            sb.append(digit1.get(hundred) + " Hundred");
            if (remainder != 0){
                sb.append(" ");
            }
        }
        if (remainder > 0){
            if (remainder <20){
                sb.append(digit1.get(remainder));
            }else{
                int ten = remainder/10;
                int unit = remainder%10;
                sb.append(digit2.get(ten));
                if (unit != 0){
                    sb.append(" ").append(digit1.get(unit));
                }
                
            }
        }
        return sb.toString();

    }
}