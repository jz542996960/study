package com.jiazhi.study.base;

public class FinallyReturn {

    private  int a = 3;

    public  int finallyRet(){
        try{
            return a;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            a = 1;
        }

        return a;
    }

    public static void main(String[] args) {
        FinallyReturn finallyReturn = new FinallyReturn();
        System.out.println(finallyReturn.finallyRet());
    }
}
