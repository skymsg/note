package com.aliyun.tianchi.filter;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FilterLauncher
 */
public class FilterLauncher {
    private static final Logger logger = LoggerFactory.getLogger(FilterLauncher.class.getName());


    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new ApiVerticle());
        vertx.deployVerticle(new DataFetchVerticle(),
                new DeploymentOptions()
                        .setWorker(true)
                        .setWorkerPoolName("data-fetch")
                        .setInstances(1)
                        .setWorkerPoolSize(10)
        );
        logger.debug("vertx launched !!!");
    }
}
