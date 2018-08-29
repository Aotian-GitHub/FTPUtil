package com.aotian.util.ftputil;

/**
 * 传输文件信息
 * @auth aotian
 */
public class FileInfo {

    /**
     * 文件本地路径
     */
    private String localPath;
    /**
     * 文件服务器路径
     */
    private String serverPath;
    /**
     * 本地文件名
     */
    private String localFileName;
    /**
     * 服务器文件名
     */
    private String serverFileName;
    /**
     * 文件编码格式
     */
    private String encoding;

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getServerPath() {
        return serverPath;
    }

    public void setServerPath(String serverPath) {
        this.serverPath = serverPath;
    }

    public String getLocalFileName() {
        return localFileName;
    }

    public void setLocalFileName(String localFileName) {
        this.localFileName = localFileName;
    }

    public String getServerFileName() {
        return serverFileName;
    }

    public void setServerFileName(String serverFileName) {
        this.serverFileName = serverFileName;
    }

}
