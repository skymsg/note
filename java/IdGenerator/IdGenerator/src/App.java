import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class App {
    public static void main(String[] args) throws Exception {
        var idGeneratorService = new IdGeneratorService();
        var countLatch = new CountDownLatch(200);
        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                try {
                    countLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                var key = "aa";
                var id = idGeneratorService.nextId(key);
                System.out.println(String.format("key:%s %s",key,id));
            }).start();
            countLatch.countDown(); 
        }
        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                try {
                    countLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                var key = "bb";
                var id = idGeneratorService.nextId(key);
                System.out.println(String.format("key:%s %s",key,id));
            }).start();
            countLatch.countDown(); 
        }
        countLatch.await(20, TimeUnit.SECONDS);
    }
}
