# deploy single node clickhouse by docker
```
docker run -d --user $(id -u):$(id -g) -p 8123:8123 -p 9000:9000 -v $(pwd)/data:/var/lib/clickhouse -v $(pwd)/log:/var/log/clickhouse-server -v $(pwd)/config/config.d:/etc/clickhouse-server/config.d -v $(pwd)/config/users.d:/etc/clickhouse-server/users.d -v $(pwd)/client/.clickhouse-client-history:/.clickhouse-client-history  --name ck_server clickhouse/clickhouse-server

```
# partition by key
Partitioning is available for the MergeTree family tables (including replicated tables). Materialized views based on MergeTree tables support partitioning, as well.
You should never use too granular of partitioning. Don't partition your data by client identifiers or names. Instead, make a client identifier or name the first column in the ORDER BY expression.
You should't make overly granular partitions(more than about a thousand partitions)
The the system.parts table to view the table parts and partitions.
```
select partition,name,active from system.parts where table = 'visits'
```

# primary key / order by key 
When choosing primary key columns, follow several simple rules:
1). Pick only columns that you plan to use in most of your queries.
2). Pick the order that will cover most of partial primary key usage use cases (e.g. 1 or 2 columns are used in query, while primary key contains 
3). If not sure, put columns with low cardinality first and then columns with high cardinality. This will lead to better data compression and better disk usage.

A long primary key will negatively affect the insert performance and memory consumption, but extra columns in the primary key do not affect ClickHouse performance during SELECT queries.

It is possible to specify a primary key (an expression with values that are written in the index file for each mark) that is different from the sorting key (an expression for sorting the rows in data parts). In this case the primary key expression tuple must be a prefix of the sorting key expression tuple.




In this case it makes sense to leave only a few columns in the primary key that will provide efficient range scans and add the remaining dimension columns to the sorting key tuple.





