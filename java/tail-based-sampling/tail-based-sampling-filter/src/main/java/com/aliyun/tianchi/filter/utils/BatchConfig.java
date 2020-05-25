package com.aliyun.tianchi.filter.utils;

public class BatchConfig {
    public static final int BATCH_SIZE = 2000;
    /**
     * 一批6M数据
     */
    private static final int batchBytesSize = 5 * 1024 * 1024;
    private static final int batchSize = 2000;


    public static int getBatchBytesSize() {
        return batchBytesSize;
    }
    public static int getBatchSize() {
        return batchSize;
    }
}
