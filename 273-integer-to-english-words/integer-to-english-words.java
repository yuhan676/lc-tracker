class Solution {

    Map<Integer, String> digit1 = new HashMap<>();
    Map<Integer, String> digit2 = new HashMap<>();
    Map<Integer,String> level = new HashMap<>();
    public String numberToWords(int num) {
        //Special Case
        if (num==0) return "Zero";
        String number = String.valueOf(num);

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

        int counter = 0;
        StringBuilder sb = new StringBuilder();
        StringBuilder numsb = new StringBuilder(number);
        if (number.length() <= 3){
            return concat3digit(num);
        }else{
            while (numsb.length() >3){
                String currSub = number.substring(numsb.length()-3, numsb.length());
                int curr3Digit = Integer.parseInt(currSub);
                //只有在位数不是000的时候才加level名称
                if (curr3Digit != 0){
                    //sb.insert(index,string)新方法
                    String newPhrase = concat3digit(curr3Digit);
                    String levelMeasure = level.get(counter);
                    sb.insert(0, newPhrase + " " + levelMeasure + " ");
                }
                
                //sb.setLength(right index) 新方法，不会返回任何东西
                numsb.setLength(numsb.length()-3);
                counter ++;
            }
            if (numsb.length() == 0) return sb.toString();
            int lastDigits = Integer.parseInt(numsb.toString().strip());
            String LastLevel = level.get(counter);
            String lastPhrase = concat3digit(lastDigits) + " " + LastLevel + " ";
            sb.insert(0, lastPhrase);
            return sb.toString().strip();
        }
    }
    public String concat3digit(int num){
        String number = String.valueOf(num);
        boolean allzero = true;
        for (char c: number.toCharArray()){
            if (c!='0'){
                allzero = false;
                break;
            }
        }
        if (allzero){
            return "";
        }
        int length = number.length();
        if (number.length()==1) return digit1.get(num);
        if (number.length()==2){
            //这里注意char自己有ascii number，所以是相对0来做运算的，直接用valueof是不行的
            int digitOne = number.charAt(0) - '0';
            int digitTwo = number.charAt(1) - '0';
            if (digitOne == 1) return digit1.get(num);
            else{
                if (digitTwo == 0) return digit2.get(digitOne);
                else{
                    System.out.println(digitOne);
                    return digit2.get(digitOne) + " " + digit1.get(digitTwo);
                }
            }
        }
        if (number.length()==3){
            if (number.substring(1).equals("00")){
                return digit1.get(number.charAt(0) - '0') + " " + "Hundred";
            }
            String last2 = concat3digit(Integer.valueOf(number.substring(1)));
            return digit1.get(number.charAt(0) - '0') + " " + "Hundred" + " " + last2;
        }
        return "";
    }
}