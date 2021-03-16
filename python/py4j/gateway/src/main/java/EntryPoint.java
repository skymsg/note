import py4j.GatewayServer;

public class EntryPoint {

    private LRUCache<String, String> lruCache;

    public EntryPoint() {
        lruCache = new LRUCache<>(3);
    }

    public static void main(String[] args) {
        GatewayServer gatewayServer = new GatewayServer(new EntryPoint());
        gatewayServer.start();
        System.out.println("gateway server start!");
    }

    public LRUCache<String, String> getLruCache() {
        return lruCache;
    }
}
