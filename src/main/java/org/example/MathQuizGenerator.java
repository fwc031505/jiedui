package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class MathQuizGenerator {
    //生成题目的个数
    public static int count;
    // //题目中数值（自然数、真分数和真分数分母）的范围
    public static int range;
    public static Random random = new Random();
    public static final String[] OPERATORS = {" + ", " - ", " × ", " ÷ "};

    public static void main(String[] args) throws IOException {
        FileWriter writer = new FileWriter("Exercises.txt");


        Scanner sc = new Scanner(System.in);
        count = sc.nextInt();
        range = sc.nextInt();
        for (int i = 0; i < count; i++) {
            writer.write( i+1+". "+ generate()+"\n");

        } writer.close();
      ;

    }

    //生成真分数
    private static String createZhengFenshu() {
        // 随机生成两个操作数，且num2必须大于num1,所以也保证了分母不为0
        int num1 = random.nextInt(range);
        int num2 = random.nextInt(range);
        if (num1 > num2) {
            return num2 + "/" + num1;
        } else {
            return num1 + "/" + num2;
        }

    }

    //生成假分数
    private static String createJiaFenshu() {

        int num1 = random.nextInt(range);
        int num2 = random.nextInt(range);

        int num3 = random.nextInt(range);
        ;
        if (num3 < num2) {
            return num1 + "'" + num3 + "/" + num2;
        }

        return num1 + "'" + num2 + "/" + num3;
    }

    //生成整数
    private static String createZhengshu() {
        return random.nextInt(range) + "";
    }

    private static String addKuohao(String s) {
        return "(" + s + ")";
    }

    //生成数，包括可能的括号
    private static String createNum() {
        //选择是整数/真分数/假分数
        int number1 = random.nextInt(3);
        String num1 = "";
        switch (number1) {
            case 0:
                num1 = createZhengshu();
                break;
            case 1:
                num1 = createZhengFenshu();
                break;
            case 2:
                num1 = createJiaFenshu();
                break;
        }
        return canAddKuohao(num1);
    }

    private static String canAddKuohao(String s) {
        if (random.nextInt(10) == 1) {
            s = addKuohao(s);
        }
        return s;
    }

    //生成运算符
    private static String creanteoperator() {
        // 随机选择运算符：0为加法，1为减法，2为乘法,3为除法
        return OPERATORS[random.nextInt(4)];
    }
    //创建简单表达式，即只有一个运算符

    // 连接2个子表达式，并括号
    private static String link(String a, String b) {
        String operator = creanteoperator();
        return canAddKuohao(a + operator + b);
    }

    private static String link(String a, String b, String operator) {
        return canAddKuohao(a + operator + b);
    }

    //生成题目
    private static String generate() {
        //运算符个数
        int operatorCount = random.nextInt(3) + 1;

        String[] nums = new String[operatorCount + 1];
        for (int j = 0; j < operatorCount + 1; j++) {
            nums[j] = createNum();
        }
      //  System.out.println(Arrays.toString(nums));
        //题目
        String result = nums[0];
        //计算结果
        String out = nums[0];
        for (int i = 0; i < operatorCount; i++) {
            String operator = creanteoperator();
            result = link(result, nums[i + 1], operator);
          /* boolean right = isRight(out, nums[i + 1],operator);
            if (right){
             out=calulate(out,nums[i+1],operator);
                result=link(result,nums[i+1],operator);
            }
        else {
                out=calulate(nums[i+1],out,operator);
                result=link(nums[i+1],result,operator);
           }*/

        }
return result;
    }

    //根据表达式s得出结果
    private static String out(String s) {
        s = s.trim();
        return null;
    }

    private static boolean isRight(String num1, String num2, String operator) {
        if (operator.equals(OPERATORS[1]) && !compare(num1, num2)) {
            return false;
        }
        if (operator.equals(OPERATORS[3]) && compare(num1, num2)) {
            return false;
        }
        return true;
    }

    //比较两个数大小,num1不小于num2返回ture
    private static boolean compare(String num1, String num2) {
        //根据数组大小判断是整数/真分数/假分数,真分数<1,假分数<其中的整数+1
        String[] split1 = num1.split("'|//");
        String[] split2 = num2.split("'|//");
        int[] number1 = new int[split1.length];
        for (int i = 0; i < split1.length; i++) {
            number1[i] = Integer.parseInt(split1[i]);
        }
        int[] number2 = new int[split2.length];
        for (int i = 0; i < split2.length; i++) {
            number2[i] = Integer.parseInt(split2[i]);
        }
        Fraction fraction21 = new Fraction(number1);
        Fraction fraction = new Fraction(number2);
        return fraction21.compare(fraction);
    }
  //计算两个数的运算
    private static String calulate(String num1, String num2, String operator) {
        //根据数组大小判断是整数/真分数/假分数,真分数<1,假分数<其中的整数+1
        String[] split1 = num1.split("'|//");
        String[] split2 = num2.split("'|//");
        int[] number1 = new int[split1.length];
        for (int i = 0; i < split1.length; i++) {
            number1[i] = Integer.parseInt(split1[i]);
        }
        int[] number2 = new int[split2.length];
        for (int i = 0; i < split2.length; i++) {
            number2[i] = Integer.parseInt(split2[i]);
        }
        Fraction fraction21 = new Fraction(number1);
        Fraction fraction = new Fraction(number2);
        if (operator.equals(OPERATORS[0])) {
            return fraction21.add(fraction).toString();
        }
        if (operator.equals(OPERATORS[1])) {
            return fraction21.subtract(fraction).toString();
        }
        if (operator.equals(OPERATORS[2])) {
            return fraction21.multiply(fraction).toString();
        }
        return fraction21.divide(fraction).toString();

    }

}
