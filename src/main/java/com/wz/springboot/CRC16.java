package com.wz.springboot;

import java.util.ArrayList;
import java.util.List;

public class CRC16 {
    /**
     * CRC16-XMODEM算法（四字节）
     * @param bytes
     * @return
     */
    public static int crc16_ccitt_xmodem(byte[] bytes) {
        return crc16_ccitt_xmodem(bytes,0,bytes.length);
    }

    /**
     * CRC16-XMODEM算法（四字节）
     * @param bytes
     * @param offset
     * @param count
     * @return
     */
    public static int crc16_ccitt_xmodem(byte[] bytes,int offset,int count) {
        int crc = 0x0000; // initial value
        int polynomial = 0x1021; // poly value
        for (int index = offset; index < count; index++) {
            byte b = bytes[index];
            for (int i = 0; i < 8; i++) {
                boolean bit = ((b >> (7 - i) & 1) == 1);
                boolean c15 = ((crc >> 15 & 1) == 1);
                crc <<= 1;
                if (c15 ^ bit)
                    crc ^= polynomial;
            }
        }
        crc &= 0xffff;
        return crc;
    }


    public static byte[] hexStrToByteArray(String str)
    {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return new byte[0];
        }
        if (str.contains(" ")) {
            str = str.replaceAll(" ","");
        }
        byte[] byteArray = new byte[str.length() / 2];
        for (int i = 0; i < byteArray.length; i++){
            String subStr = str.substring(2 * i, 2 * i + 2);
            byteArray[i] = ((byte)Integer.parseInt(subStr, 16));
        }
        return byteArray;
    }


    public static String byteArrayToHexStr(byte[] byteArray) {
        if (byteArray == null){
            return null;
        }
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[byteArray.length * 2];
        for (int j = 0; j < byteArray.length; j++) {
            int v = byteArray[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static void main(String[] args) {

        List<String> list = genHex99x99();
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + ":" + list.get(i));
        }

    }

    /**
     * 生成CRC校验位码
     *
     * @param hexStr 电明协议的地址码+源地址码+指令码
     */
    public static String genCRC(String hexStr) {
        String cs = Integer.toHexString(crc16_ccitt_xmodem(hexStrToByteArray(hexStr)));
        if (cs.length() % 2 == 1) {
            cs = "0" + cs;
        }
        if (cs.length() == 2) {
            cs = "00" + cs;
        }
        return cs;
    }

    public static List<String> genHex99() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i <= 9; i++) {
            for (int j = 0; j <= 9; j++) {
                String content = "3" + i + "3" + j + "33383239";
                content += genCRC(content);
                list.add("02" + content + "03");
            }
        }
        return list;
    }

    public static List<String> genHex99x99() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i <= 9; i++) {
            for (int j = 0; j <= 9; j++) {
                for (int k = 0; k <= 9; k++) {
                    for (int l = 0; l <= 9; l++) {
                        String content = "3" + k + "3" + l + "3"+ i +"3"+ j +"3239";
                        content += genCRC(content);
                        list.add("02" + content + "03");
                    }
                }
            }
        }
        return list;
    }
}
