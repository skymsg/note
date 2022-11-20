# deploy single node clickhouse by docker
```
docker run -d --user $(id -u):$(id -g) -p 8123:8123 -p 9000:9000 -v $(pwd)/data:/var/lib/clickhouse -v $(pwd)/log:/var/log/clickhouse-server -v $(pwd)/config/config.d:/etc/clickhouse-server/config.d -v $(pwd)/config/users.d:/etc/clickhouse-server/users.d -v $(pwd)/client/.clickhouse-client-history:/.clickhouse-client-history  --name ck_server clickhouse/clickhouse-server

```
# partition by key
# primary key / order by key 
# 
