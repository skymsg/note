package com.aliyun.tianchi.filter.utils;

import java.util.Objects;

public class ConfigUtil {
    /**
     * 通过setParameter获取的数据源端口
     */
    private static int dataSourcePort;

    public static int getDataSourcePort() {
        return dataSourcePort;
    }

    public static void setDataSourcePort(int p) {
        dataSourcePort = p;
    }

    /**
     * 根据从server.port端口号确定拉取的文件名
     *
     * @return 源数据文件名
     */
    public static String getDataSourceFileName() {
        if (8000 == getServerPort()) {
            return "full";
//            return "trace1.data";
        } else {
            return "trace2.data";
        }
    }

    /**
     * 获取监听http请求的端口号  获取失败则抛出NullPointerException
     *
     * @return 端口
     */
    public static int getServerPort() {
        String serverPort = System.getProperty("server.port");
        Objects.requireNonNull(serverPort);
        return Integer.parseInt(serverPort);
    }


}
