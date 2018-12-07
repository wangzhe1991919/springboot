package com.wz.springboot;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 情报板控制命令
 *
 */
public class InfoboardOrder {
    /****************************读取配置文件***********************/
    //上位机地址（30 30） 广播地址
    public static final String UPPER_ADDRESS = "30 30";
    //下位机之地（30 31[遂宁西的入口前面那块板]），读取配置文件获取
    public static final String LOWER_ADDRESS = "30 31";

    //协议起始指令
    public static final String STX = "02";
    //协议结束指令
    public static final String ETX = "03";
    /********************************结束***************************/



    public static void main(String[] args) {
        //commonSend(SEND_PLAYLIST);
        commonSend(READ_CURRENT_CONTENT);

        //SocketClientLong.send(genInfoboardOrder(READ_PLAYLIST));
    }



    public static String TMP = " 5B 50 4C 41 59 4C 49 53 54 5D 0D 0A 49 54 45 4D 5F 4E 4F 3D 30 30 31 0D 0A 49 54 45 4D 30 30 30 3D 33 30 2C 30 2C 30 2C 30 2C 30 2C 5C 43 30 30 30 30 30 30 5C 42 30 30 30 5C 43 30 34 38 30 30 30 5C 46 73 33 32 33 32 5C 54 32 35 35 32 35 35 30 30 30 30 30 30 5C 4B 30 30 30 30 30 30 30 30 30 30 30 30 5C 57 B0 B2 C8 AB B5 DA D2 BB 20 D4 A4 B7 C0 CE AA D6 F7";



    //总状态检测
    public static final String ALL_STATUS = "30 31";
    //设备详细状态
    public static final String DEVICE_DETAIL_STATUS = "30 33";
    //连接状态监测
    public static final String CONNECTION_STATUS = "32 39";
    //坏点监测-返回上次监测结果
    public static final String BAD_POINT_PREVIEW = "36 39 30";
    //坏点监测-立即监测
    public static final String BAD_POINT_IMMEDIATELY = "36 39 31";
    //打开指定风扇
    public static final String OPEN_FAN = "30 39 31";
    //关闭指定风扇
    public static final String CLOSE_FAN = "30 39 30";
    //打开可变信息标识
    public static final String OPEN_VARIABLE_INFORMATION = "31 31 31";
    //关闭可变信息标识
    public static final String CLOSE_VARIABLE_INFORMATION = "31 31 30";
    //打开指定颜色灯
    public static final String OPEN_LAMP_COLOR = "31 33 31";
    //关闭指定颜色灯
    public static final String CLOSE_LAMP_COLOR = "31 33 30";
    //获取亮度调节方式和当前显示亮度
    public static final String GET_BRIGHTNESS = "32 31";
    //设置亮度信息
    public static final String SET_BRIGHTNESS = "32 33";
    //显示指定列表
    public static final String DISPLAY_SPECIFIED_LIST = "34 37 70 6C 61 79 30 31 2E 6C 73 74";
    //获取当前显示列表编号
    public static final String GET_CURRENT_LIST = "34 39";
    //发送播放列表（非立即显示）
    public static final String SEND_PLAYLIST = "33 39 2B 30 30 30 30 30 30 30 30 70 6C 61 79 30 32 2E 6C 73 74" + TMP;
    //发送播放列表（立即显示）
    public static final String SEND_PLAYLIST_IMMEDIATELY = "37 31";
    //读取当前显示内容
    public static final String READ_CURRENT_CONTENT = "37 33";
    //读取播放列表
    public static final String READ_PLAYLIST = "35 37 30 30 30 30 30 30 30 30 70 6C 61 79 30 31 2E 6C 73 74";
    //同步下位机时间
    public static final String SYNCHRONIZATION_TIME = "31 35";
    //读取下位机时间
    public static final String READ_TIME = "31 37";
    //获取最后一次启动时间
    public static final String GET_LAST_BOOT_TIME = "31 39";
    //版本检测
    public static final String VERSION_CHECK = "33 31";

    /***********************文件下发相关命令**********************/
    //向显示设备发送文件
    public static final String SEND_FILE_DEVICE = "30 35";
    //向显示设备读取文件
    public static final String READ_FILE_DEVICE = "30 37";
    //向显示设备发送亮度控制表文件
    public static final String SEND_BRIGHTNESS_CONTROLLISTFILE_DEVICE = "33 33";
    //向显示设备发送系统配置文件
    public static final String SEND_CONFIG_DEVICE = "33 35";
    //向显示设备发送硬件配置文件
    public static final String SEND_HARDWARE_CONFIG_DEVICE = "33 37";
    //向显示设备发送图片文件
    public static final String SEND_PICTURE_DEVICE = "34 31";
    //向显示设备发送字体文件
    public static final String SEND_FONT_FILE_DEVICE = "34 33";
    //向显示设备发送在线升级文件
    public static final String SEND_ONLINE_UPDATE_DEVICE = "34 35";
    //向显示设备发送动画文件
    public static final String SEND_FLX_DEVICE = "37 37";
    //读取亮度控制表文件
    public static final String READ_BRIGHTNESS_CONTROLLISTFILE_DEVICE = "35 31";
    //读取系统配置文件
    public static final String READ_CONFIG_DEVICE = "35 33";
    //读取硬件配置文件
    public static final String READ_HARDWARE_CONFIG_DEVICE = "35 35";
    //备读取图片文件
    public static final String READ_PICTURE_DEVICE = "35 39";
    //读取在线升级文件
    public static final String READ_ONLINE_UPDATE_DEVICE = "36 33";

    /**
     * 公共发送
     * @param order
     * @return
     */
    public static String commonSend(String order) {
        return sendSocket(genInfoboardOrder(order));
    }

    /**
     * 总状态检测
     * （作为总状态指令，上位机轮询各设备状态，建议1—5分钟论询一次。）
     * @return
     */
    public static String allStatus() {
        //需要解析对应返回数据
        return sendSocket(genInfoboardOrder(ALL_STATUS));
    }

    /**
     * 生成需要发送的协议
     * @param orderCode 指令码
     * @return
     */
    public static byte[] genInfoboardOrder(String orderCode) {
        //用于计算校验码的字符串
        StringBuffer tmp = new StringBuffer();
        tmp.append(UPPER_ADDRESS).append(LOWER_ADDRESS).append(orderCode);
        //计算协议码
        StringBuffer hexOrder = new StringBuffer();
        hexOrder.append(STX);
        hexOrder.append(tmp);
        hexOrder.append(CRC16.genCRC(tmp.toString()));
        hexOrder.append(ETX);
        System.out.println("=======>>send:" + hexOrder.toString());
        return CRC16.hexStrToByteArray(hexOrder.toString());
    }


    /**
     *
     * @param order
     */
    public static String sendSocket(byte[] order) {
        //1.建立TCP连接
        String ip = "10.152.96.36";//服务器端ip地址
        int port = 4001;        //端口号

        //String ip = "127.0.0.1";
        //int port = 60000;

        Socket sck = null;
        try {
            sck = new Socket(ip,port);
            sck.setKeepAlive(true);
            //2.传输内容
            OutputStream os = sck.getOutputStream();   //输出流
            os.write(order);

            InputStream inputStream = sck.getInputStream();
            byte[] b = new byte[1024];

            int len;
            while ((len = inputStream.read(b))  > -1) {
                //inStr.read(b,0,len);
                String str = new String(b,"GBK");
                System.out.println("接收数据---STR----》》" +  str );
                System.out.println("接收数据---HEX----》》" +  CRC16.byteArrayToHexStr(b));

                if (CRC16.byteArrayToHexStr(b).endsWith(ETX)) {
                    break;
                }
            }

            /*for (int i = 0; i < 1000; i++) {
                int r = inputStream.read(b);
                if(r>-1){
                    String str = new String(b,"GBK");
                    System.out.println("接收数据---STR----》》" +  str );
                    System.out.println("接收数据---HEX----》》" +  CRC16.byteArrayToHexStr(b));
                }
            }*/

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //3.关闭连接
            try {
                sck.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
