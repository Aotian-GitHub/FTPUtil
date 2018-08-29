package com.aotian.util.test;

import com.aotian.util.ftputil.FTPUtil;
import com.aotian.util.ftputil.FileInfo;
import com.aotian.util.ftputil.LoginInfo;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * FTP工具类测试类
 * @auth aotian
 */
public class FTPUtilTest {

    private static final String SERVERADDRESS = "192.168.134.135";
    private static final String USERNAME = "ftpuser";
    private static final String PASSWORD = "123456";

    @Test
    public void testSendFile() {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setServer(SERVERADDRESS);
        loginInfo.setUsername(USERNAME);
        loginInfo.setPassword(PASSWORD);

        FileInfo fileInfo = new FileInfo();
        fileInfo.setLocalPath("C:\\Users\\7610a\\IdeaProjects\\ftputil\\src\\main\\resources");
        fileInfo.setLocalFileName("test.properties");
        fileInfo.setServerPath("/home/ftpuser/test1");
        fileInfo.setEncoding("GBK");

        try {
            new FTPUtil().sendFile(loginInfo, fileInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试批量上传
     */
    @Test
    public void testSendFileBatch() {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setServer(SERVERADDRESS);
        loginInfo.setUsername(USERNAME);
        loginInfo.setPassword(PASSWORD);

        List<FileInfo> list = new ArrayList<>();

        FileInfo fileInfo = new FileInfo();
        fileInfo.setLocalPath("C:\\Users\\7610a\\IdeaProjects\\ftputil\\src\\main\\resources");
        fileInfo.setLocalFileName("test.properties");
        fileInfo.setServerPath("/home/ftpuser/test1");
        fileInfo.setEncoding("GBK");
        list.add(fileInfo);

        FileInfo fileInfoOther = new FileInfo();
        fileInfoOther.setLocalPath("C:\\Users\\7610a\\IdeaProjects\\ftputil\\src\\main\\resources");
        fileInfoOther.setLocalFileName("test2.properties");
        fileInfoOther.setServerPath("/home/ftpuser/test2");
        fileInfoOther.setEncoding("GBK");
        list.add(fileInfoOther);

        try {
            new FTPUtil().sendFileBatch(loginInfo, list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDownloadFile(){
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setServer(SERVERADDRESS);
        loginInfo.setUsername(USERNAME);
        loginInfo.setPassword(PASSWORD);

        FileInfo fileInfo = new FileInfo();
        fileInfo.setLocalPath("E:\\迅雷下载");
        fileInfo.setLocalFileName("test123.properties");
        fileInfo.setServerPath("/home/ftpuser/test1");
        fileInfo.setServerFileName("test.properties");

        try {
            new FTPUtil().downloadFile(loginInfo, fileInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
