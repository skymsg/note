package io.vertx.howtos.httpclient;


import io.vertx.core.*;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisAPI;
import io.vertx.redis.client.RedisConnection;
import io.vertx.redis.client.RedisOptions;

public class Server extends AbstractVerticle {
    private static final int MAX_RECONNECT_RETRIES = 16;

    private RedisOptions options = new RedisOptions();
    private RedisConnection client;
    private RedisAPI redis;

    @Override
    public void start() {
        createRedisClient(onCreate -> {
            if (onCreate.succeeded()) {
                // connected to redis!
                redis = RedisAPI.api(client);
            }
        });
        Router router = Router.router(vertx);
        router.get("/hello").handler(this::hello);
        vertx.createHttpServer().requestHandler(router).listen(8080);
    }

    private void hello(RoutingContext routingContext) {
        String key = routingContext.request().getParam("key");
        redis.get(key, res -> {
            if (res.succeeded()) {
                routingContext.response().end(res.result().toString());
            }
        });
    }


    /**
     * Will create a redis client and setup a reconnect handler when there is
     * an exception in the connection.
     */
    private void createRedisClient(Handler<AsyncResult<RedisConnection>> handler) {
        Redis.createClient(vertx, "redis://172.26.192.107:6379")
                .connect(onConnect -> {
                    if (onConnect.succeeded()) {
                        client = onConnect.result();
                        // make sure the client is reconnected on error
                        client.exceptionHandler(e -> {
                            // attempt to reconnect
                            attemptReconnect(0);
                        });
                    }
                    // allow further processing
                    handler.handle(onConnect);
                });
    }

    /**
     * Attempt to reconnect up to MAX_RECONNECT_RETRIES
     */
    private void attemptReconnect(int retry) {
        if (retry > MAX_RECONNECT_RETRIES) {
            // we should stop now, as there's nothing we can do.
        } else {
            // retry with backoff up to 10240 ms
            long backoff = (long) (Math.pow(2, Math.min(retry, 10)) * 10);

            vertx.setTimer(backoff, timer -> createRedisClient(onReconnect -> {
                if (onReconnect.failed()) {
                    attemptReconnect(retry + 1);
                }
            }));
        }
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Server(), new DeploymentOptions());
    }

}
