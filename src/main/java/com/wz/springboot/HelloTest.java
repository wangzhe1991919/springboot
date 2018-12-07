package com.wz.springboot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
public class HelloTest {

    @Value("${propTestStr}")
    private String propTestStr;

    @Value("${constant.bStr}")
    private String propTestSonStr;

    @RequestMapping("hello")
    public String sayHello() {
        System.out.println("hello springboot---" + propTestStr + "-----" + propTestSonStr);

        return "hello";
    }

    @RequestMapping("/download")
    public String download(String fileName , String filePath, HttpServletRequest request, HttpServletResponse response){

        fileName = "sqljdbc4.jar";
        filePath = "D:\\";

        response.setContentType("text/html;charset=utf-8");
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        java.io.BufferedInputStream bis = null;
        java.io.BufferedOutputStream bos = null;

        String downLoadPath = filePath;  //注意不同系统的分隔符
        //	String downLoadPath =filePath.replaceAll("/", "\\\\\\\\");   //replace replaceAll区别 *****
        System.out.println(downLoadPath);

        try {
            long fileLength = new File(downLoadPath).length();
            response.setContentType("application/x-msdownload;");
            response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
            response.setHeader("Content-Length", String.valueOf(fileLength));
            bis = new BufferedInputStream(new FileInputStream(downLoadPath));
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null)
                try {
                    bis.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            if (bos != null)
                try {
                    bos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
        return null;
    }


}