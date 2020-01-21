package io.vertx.howtos.httpclient;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

public class Server extends AbstractVerticle {


    @Override
    public void start() {
        vertx.createHttpServer().requestHandler(req -> {
            req.bodyHandler(buffer -> {
                System.out.println(" Receiving user\n" + buffer.toString() + " from client");
                req.response().end();
            });

        }).listen(8080, listenResult -> {
            if (listenResult.failed()) {
                System.out.println("Could not start HTTP server");
                listenResult.cause().printStackTrace();
            } else {
                System.out.println("Server started");
            }
        });
    }


    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Server());
    }

}
