package io.vertx.howtos.httpclient;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.FileProps;
import io.vertx.core.file.FileSystem;
import io.vertx.core.file.OpenOptions;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;

public class Client extends AbstractVerticle {


    @Override
    public void start() {
        String filename = "upload.txt";
        FileSystem fs = vertx.fileSystem();

        WebClient client = WebClient.create(vertx);

        fs.props(filename, ares -> {

            FileProps props = ares.result();
            System.out.println("props is " + props);
            long size = props.size();
            HttpRequest<Buffer> req = client.put(8080, "localhost", "/");
            req.putHeader("content-length", "" + size);

            fs.open(filename, new OpenOptions(), ares2 -> {
                System.out.println("open succeed");
                req.timeout(300).sendStream(ares2.result(), ar -> {
                    if (ar.succeeded()) {
                        HttpResponse<Buffer> response = ar.result();
                        System.out.println(" Got HTTP response with status " + response.statusCode());
                    } else {
                        ar.cause().printStackTrace();
                    }
                });
            });

        });

    }


    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Client());
    }

}
