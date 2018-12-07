package com.wz.springboot;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author 某家: 
 * @version 创建时间：2015年8月17日 下午3:04:14 
 * 类说明 
 */
public class SocketClientLong {
    private static final ThreadLocal<Socket> threadConnect = new ThreadLocal<Socket>();

    private static final String HOST = "10.152.96.36";

    private static final int PORT = 4001;

    private static Socket client;

    private static OutputStream outStr = null;

    private static InputStream inStr = null;

    private static Thread tRecv = new Thread(new RecvThread());

    //private static Thread tKeep = new Thread(new KeepThread());

    private static String gbkStr = "";
    private static String hexStr = "";

    public static void connect() throws UnknownHostException, IOException  {
        client = threadConnect.get();
        if(client == null){
            client = new Socket(HOST, PORT);
            threadConnect.set(client);
            //tKeep.start();
            System.out.println("========连接开始！========");
        }
        outStr = client.getOutputStream();
        inStr = client.getInputStream();
    }

    public static void disconnect() {
        try {
            outStr.close();
            inStr.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /*private static class KeepThread implements Runnable {
        public void run() {
            try {
                System.out.println("=====================开始发送心跳包==============");
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    //String str = list.get(i);
                    //i++;
                    String str = "30 30 30 31 37 33";

                    str = "02 " + str + CRC16.genCRC(str) + " 03";


                    byte[] b = CRC16.hexStrToByteArray(str);

                    System.out.println("Send:" + str);

                    outStr.write(b);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }*/

    private static class RecvThread implements Runnable {
        public void run() {
            try {
                System.out.println("==============开始接收数据线程===============");
                while (true) {
                    byte[] b = new byte[1024];
                    int r = inStr.read(b);
                    if(r>-1){
                        //16进制码
                        String tmpHexStr = replace00(CRC16.byteArrayToHexStr(b));
                        //转成的中文字符串
                        String tmpGbkStr = new String(b,"GBK");

                        System.out.println("--HEX-->" + tmpHexStr);
                        System.out.println("--STR-->" + tmpGbkStr);

                        if (tmpHexStr.startsWith(InfoboardOrder.STX)) {
                            gbkStr = tmpGbkStr;
                            hexStr = tmpHexStr;
                        } else {
                            gbkStr += tmpGbkStr;
                            hexStr += tmpHexStr;
                        }
                        if (tmpHexStr.endsWith(InfoboardOrder.ETX)) {
                            //此时需要调用某个回调函数，证明此次返回数据完成
                            System.out.println("----最终HEX---->>" + hexStr);
                            System.out.println("----最终STR---->>" + gbkStr);
                        }

                        //接收到数据后停止
                        //System.exit(1);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {

        try {
            SocketClientLong.connect();
            tRecv.start();

            outStr.write(InfoboardOrder.genInfoboardOrder(InfoboardOrder.READ_PLAYLIST));

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 去除获取到字节码最后的000000000
     * @param str
     * @return
     */
    public static String replace00(String str) {
        return str.replaceAll("00","");
    }
}