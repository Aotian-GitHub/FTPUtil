package com.aotian.util.ftputil;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * FTP工具类
 * @auth aotian
 */
public class FTPUtil {

    private static final String  INFOSEPARATOR = ";";

    /**
     * 批量上传文件
      * @param loginInfo
     * @param fileInfoList
     * @throws IOException
     */
    public void sendFileBatch(LoginInfo loginInfo, List<FileInfo> fileInfoList) throws IOException {
        FTPClient ftpClient = login(loginInfo);
        for (FileInfo fileInfo : fileInfoList) {
            send(ftpClient, fileInfo);
        }
        ftpClient.logout();
    }

    /**
     * 上传文件
     */
    public void sendFile(LoginInfo loginInfo, FileInfo fileInfo) throws IOException {
        FTPClient ftpClient = login(loginInfo);
        send(ftpClient, fileInfo);
        ftpClient.logout();
    }

    /**
     * 下载文件
     */
    public void downloadFile(LoginInfo loginInfo, FileInfo fileInfo) throws IOException {
        FTPClient ftpClient = login(loginInfo);

        ftpClient.changeWorkingDirectory(fileInfo.getServerPath());

        File file = new File(fileInfo.getLocalPath() + File.separator + fileInfo.getLocalFileName());
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ftpClient.retrieveFile(fileInfo.getServerFileName(), fileOutputStream);

        fileOutputStream.close();
        ftpClient.logout();
    }

    /**
     * 登录FTP服务器
     * @return
     */
    private FTPClient login(LoginInfo loginInfo) throws IOException {

        // 验证登录信息
        checkLoginInfo(loginInfo);

        // 获取登录信息
        String server = loginInfo.getServer();
        String username = loginInfo.getUsername();
        String password = loginInfo.getPassword();
        Integer port = loginInfo.getPort();

        // 登录
        FTPClient ftpClient = new FTPClient();
        if (port == null) {
            ftpClient.connect(server);
        } else {
            ftpClient.connect(server, port);
        }
        ftpClient.login(username, password);

        // 验证登录情况
        int replyCode = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(replyCode)) {
            throw new RuntimeException("登录FTP服务器失败，错误代码：" + replyCode);
        }

        ftpClient.enterLocalPassiveMode();

        return ftpClient;
    }

    /**
     * 获取文件输入流
     * @param fileInfo
     * @return
     * @throws FileNotFoundException
     */
    private FileInputStream getFileInputStream(FileInfo fileInfo) throws FileNotFoundException {
        String localFilePath = fileInfo.getLocalPath();
        String localFileName = fileInfo.getLocalFileName();

        String filePath;
        if (StringUtils.isBlank(localFilePath)) {
            filePath = fileInfo.getLocalFileName();
        } else {
            filePath = localFilePath + File.separator + localFileName;
        }
        return new FileInputStream(new File(filePath));
    }

    /**
     * 发送文件
     * @param ftpClient
     * @param fileInfo
     * @throws IOException
     */
    private void send(FTPClient ftpClient, FileInfo fileInfo) throws IOException {

        // 检查参数是否
        checkSendInfo(fileInfo);

        FileInputStream fileInputStream = getFileInputStream(fileInfo);

        // 创建服务器目录并切换
        String serverPath = fileInfo.getServerPath();
        ftpClient.makeDirectory(serverPath);
        boolean isChanged = ftpClient.changeWorkingDirectory(serverPath);
        if (!isChanged) {
            throw new RuntimeException("切换工作目录失败，请确定目标服务器上存在目录："
                    + serverPath
                    + "，当前不支持创建多层级目录。");
        }

        // 上传文件
        ftpClient.storeFile(fileInfo.getServerFileName(), fileInputStream);
        // 关闭已占用资源
        fileInputStream.close();
    }

    /**
     * 验证登录信息
     * @param loginInfo
     */
    private void checkLoginInfo(LoginInfo loginInfo) {

        List<String> messageList = new ArrayList<>();

        if (loginInfo != null) {
            if (StringUtils.isBlank(loginInfo.getServer())) {
                messageList.add("the paramter server is null or \"\"");
            }

            if (StringUtils.isBlank(loginInfo.getUsername())) {
                messageList.add("the paramter username is null or \"\"");
            }

            if (StringUtils.isBlank(loginInfo.getPassword())) {
                messageList.add("the paramter password is null or \"\"");
            }
        }else{
            messageList.add("The paramter object loginInfo is null");
        }

        if (messageList.size() > 1) {
            throw new RuntimeException(String.join(INFOSEPARATOR, messageList));
        }
    }

    /**
     * 检查上传文件基本信息
     * @param fileInfo
     */
    private void checkSendInfo(FileInfo fileInfo){

        List<String> messageList = new ArrayList<>();

        if (fileInfo != null) {
            if (StringUtils.isBlank(fileInfo.getServerPath())) {
                messageList.add("the paramter serverPath is null or \"\"");
            }

            if (StringUtils.isBlank(fileInfo.getLocalPath())) {
                messageList.add("the paramter localPath is null or \"\"");
            }

            String localFileName = fileInfo.getLocalFileName();
            if (StringUtils.isBlank(localFileName)) {
                messageList.add("the paramter localFileName is null or \"\"");
            } else if (StringUtils.isBlank(fileInfo.getServerFileName())) {
                fileInfo.setServerFileName(localFileName);
            }
        }else{
            messageList.add("The paramter object fileInfo is null");
        }

        if (messageList.size() > 1) {
            throw new RuntimeException(String.join(INFOSEPARATOR, messageList));
        }

    }

}
