package io.vertx.howtos.httpclient;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.predicate.ResponsePredicate;
import io.vertx.ext.web.codec.BodyCodec;


public class JokeVerticle extends AbstractVerticle {

    private HttpRequest<String> request;

    @Override
    public void start(){

        request = WebClient.create(vertx)
            .get(443,"www.taptap.com","/app/2301")
            .ssl(true)
            .as(BodyCodec.string())
            .expect(ResponsePredicate.SC_OK);
        request.send(ar -> {
            if(ar.succeeded()){
                HttpResponse<String> result = ar.result();
                System.out.println(result.body());
            }else{
                ar.cause().printStackTrace();
            }
        });

    }

    public static void main(String[] args){
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new JokeVerticle());
    }

}
