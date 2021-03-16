from py4j.java_gateway import JavaGateway
gateway = JavaGateway()
lruCache = gateway.entry_point.getLruCache()


print("lru:",lruCache.toString())
lruCache.put("a","1")
print("lru:",lruCache.toString())

lruCache.put("b","2")
print("lru:",lruCache.toString())

lruCache.put("c","3")
print("lru:",lruCache.toString())

lruCache.put("d","4")
print("lru:",lruCache.toString())

