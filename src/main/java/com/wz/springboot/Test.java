package com.wz.springboot;

public class Test {

    public static void main(String[] args) {
        String s = "010058+00000000play01.lst[PLAYLIST]" +
                "ITEM_NO=003" +
                "ITEM000=20,0,0,0,1,\\C000000\\Fs1616\\T000255000000\\K000000000000\\W增强安全意识，\\A防汛不容忽视。" +
                "ITEM001=20,0,0,0,1,\\C000000\\Fs1616\\T000255000000\\K000000000000\\W关爱残疾人，\\A办好残运会。" +
                "ITEM002=20,0,0,0,1,\\C000000\\Fs1616\\T000255000000\\K000000000000\\W交通运输服务\\A监督电话：\\A   12328";

        String[] ss = s.split("ITEM");
        //System.out.println(s.split("\\\\W")[1]);
        for (int i = 0; i < ss.length; i++) {
            System.out.println(ss[i]);
        }

    }
}