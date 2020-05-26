package com.aliyun.tianchi.filter;

import com.aliyun.tianchi.filter.utils.BatchConfig;
import com.aliyun.tianchi.filter.utils.ConfigUtil;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.FileSystem;
import io.vertx.core.file.OpenOptions;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataFetchVerticle extends AbstractVerticle {

    private static final Logger logger = LoggerFactory.getLogger(FilterLauncher.class.getName());
    private WebClient webClient;
    private FileSystem fileSystem;
    @Override
    public void start() {
        webClient = WebClient.create(vertx);
        fileSystem = vertx.fileSystem();
        EventBus eventBus = vertx.eventBus();
        eventBus.consumer("DataFetchVerticle").handler(this::dataSourceFetch);
    }


    private void dataSourceFetch(Message<Object> rangeMsg){
        String rangeStr = (String) rangeMsg.body();
        int slashIndex = rangeStr.indexOf("/");
        long contentLength = Long.parseLong(rangeStr.substring(0,slashIndex));
        long rangePos = Long.parseLong(rangeStr.substring(slashIndex+1));
        long startIndex = rangePos* BatchConfig.getBatchBytesSize();
        long endIndex = startIndex+BatchConfig.getBatchBytesSize();
        logger.debug(String.format("rangePost:%s startIndex:%s endIndex:%s", rangePos,startIndex,endIndex));
        if(endIndex>=contentLength){
            endIndex=contentLength-1;
        }
        String rangeHeader = String.format("bytes=%s-%s", startIndex,endIndex);
        logger.debug(String.format("Content-Length:%s rangePos:%s", contentLength,rangePos));
        webClient.get(ConfigUtil.getDataSourcePort(),"localhost","/"+ConfigUtil.getDataSourceFileName())
                .putHeader("Range",rangeHeader)
                .send(ar->{
                    if(ar.succeeded()){
                        HttpResponse<Buffer> result = ar.result();
                        rangeMsg.reply(result.bodyAsBuffer());
//                        downloadToFile(startIndex, result);
                    }else {
                        logger.error("拉取文件range失败： "+rangeHeader);
                        ar.cause().printStackTrace();
                    }
                });
    }


    private void downloadToFile(long startIndex, HttpResponse<Buffer> result) {
        Buffer buffer = result.bodyAsBuffer();
        fileSystem.open("./"+ ConfigUtil.getDataSourceFileName(), new OpenOptions().setWrite(true).setRead(false), openFileAsyncRes->{
          if(openFileAsyncRes.succeeded()){
              AsyncFile asyncFile = openFileAsyncRes.result();
              asyncFile.write(buffer,startIndex,writeAr->{
                  if(writeAr.succeeded()){
                      logger.debug("读取文件range并且写入文件成功： "+startIndex);
                  }else{
                      logger.debug("读取文件range成功 打开文件成功 写入文件失败： "+startIndex);
                  }
              });
          }else{
              logger.error("读取文件range成功,打开文件失败： "+startIndex);
              openFileAsyncRes.cause().printStackTrace();
          }
        });
    }
}
