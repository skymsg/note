package com.aliyun.tianchi.filter;

import com.aliyun.tianchi.filter.utils.BatchConfig;
import com.aliyun.tianchi.filter.utils.ConfigUtil;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class ApiVerticle extends AbstractVerticle {

    private static final Logger logger = LoggerFactory.getLogger(FilterLauncher.class.getName());
    private WebClient webClient;

    @Override
    public void start() {
        Router router = Router.router(vertx);
        webClient = WebClient.create(vertx);
        router.head("/ready").handler(this::ready);
        router.get("/setParameter").handler(this::setParameter);
        vertx.createHttpServer().requestHandler(router).listen(ConfigUtil.getServerPort());
    }

    private void ready(RoutingContext ctx) {
        ctx.response().setStatusCode(200).end();
    }

    private void setParameter(RoutingContext ctx) {
        String port = ctx.request().getParam("port");
        if(Objects.isNull(port)||port.length()==0){
            logger.error("获取数据源端口失败");
            ctx.response().setStatusCode(400).end("port parameter is required!");
            return;
        }else{
            ConfigUtil.setDataSourcePort(Integer.parseInt(port));
        }
        ctx.response().end("OK", asyncResult -> {
            if (asyncResult.succeeded()) {
                webClient.head(ConfigUtil.getDataSourcePort(),"localhost","/"+ConfigUtil.getDataSourceFileName())
                        .send(ar -> {
                    if (ar.succeeded()) {
                        HttpResponse<Buffer> result = ar.result();
                        String contentLengthHeader = result.headers().get("Content-Length");
                        if (Objects.isNull(contentLengthHeader)) {
                            logger.error("get content length of the datasource failed!");
                        } else {
                            long length = Long.parseLong(contentLengthHeader);
                            long rangeCount = length / BatchConfig.getBatchBytesSize()
                                    + (length % BatchConfig.getBatchBytesSize() > 0 ? 1 : 0);
                            List<String> rangeList = LongStream.range(0, rangeCount)
                                    .mapToObj(x -> contentLengthHeader + "/" + x)
                                    .collect(Collectors.toList());
                            vertx.eventBus().send("DataProcessVerticle",rangeList);
                        }
                    } else {
                        ar.cause().printStackTrace();
                    }
                });
            } else {
                asyncResult.cause().printStackTrace();
            }

        });
    }
}
