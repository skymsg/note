import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class IdGeneratorService {
	private Map<String,AtomicLong> idGenMap = new ConcurrentHashMap<>();
	
	public long nextId(String key){
		var idGen = idGenMap.get(key);
		if(idGen==null){
			synchronized(idGenMap){
				idGenMap.putIfAbsent(key, new AtomicLong(0L));
			}
			idGen = idGenMap.get(key);
		}
		return idGen.incrementAndGet();
	}
}
