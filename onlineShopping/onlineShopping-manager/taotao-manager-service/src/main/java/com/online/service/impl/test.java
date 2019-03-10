package com.online.service.impl;

public class test {
    public static void main(String[] args){
        int a = 3;
        int b = 18;
        int c = 0;
        //  i = a 赋值  //  判断i是否小于b   //i每循环一次加1
        for(int i = a ;    i<b;           i++){
            c = c + a;
            System.out.println("i"+"="+i+"\t"+"c"+"="+c);
        }
    }
}
