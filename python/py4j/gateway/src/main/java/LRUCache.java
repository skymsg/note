import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private final int cacheSize;

    public LRUCache(int cacheSize) {
        super(cacheSize,0.75f,true);
        this.cacheSize = cacheSize;
    }

    protected boolean removeEldestEntry(Map.Entry eldest) {
        System.out.println("this.size: "+this.size());
        System.out.println("cacheSize: "+this.cacheSize);
        System.out.println("cache: "+this);
        boolean rem = this.size() > cacheSize;
        System.out.println("rem: "+rem);
        return rem;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<K, V> entry : entrySet()) {
            sb.append(String.format("%s:%s ", entry.getKey(), entry.getValue()));
        }
        return sb.toString();
    }
}
