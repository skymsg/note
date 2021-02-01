#hive sql note

## create table
```sql
CREATE external TABLE `logs`(
		`id` STRING,
		`name` STRING
)
PARTITIONED BY (
		`dt` string,
		`hour string`
)
ROW FORMAT DELIMITED 
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
STORED AS TEXTFILE LOCATION 'hdfs://localhost:port/path';
```
## drop table
drop  table logs

## add partition
```sql
ALTER TABLE logs  ADD IF NOT EXISTS PARTITION(dt='2020-04-01') LOCATION 'hdfs://localhost:port/path/dt=2020-04-01';
ALTER TABLE logs  ADD IF NOT EXISTS PARTITION(dt='2020-04-01',hour=11) LOCATION 'hdfs://localhost:port/path/dt=2020-04-01/hour=11';
```

## drop partition
```sql
ALTER TABLE logs DROP IF EXISTS PARTITION(dt='2020-04-02');
```

## show partitions
```sql
show partitions logs
```


## with clause
```sql

wiht q1 as (select key from src where key = '5')
select *
from q;

--- from style
with q1 as (select * from src where key = '5')
from q1
select *;

--- chaining CTEs
with q1 as (select * from src where key='5'),
     q2 as (select * from src where key='4')
select * from (select key from q1) a;

--- union example
with q1 as (select * from src where key='5'),
    q2 as (select * from src where key='4')
select * from q1 union all select * from q2;

--- insert example

create table s1 like src;
with q1 as (select * from src where key='5')
from q1
insert overwrite table s1
select *;

--- view example
create view v1 as 
with q1 as (select key from src where key='5')
select * from q1;
select * from v1;


--- view example, name collision
create view v1 as 
with q1 as (select key from src where key='5')
select * from q1;
with q1 as (select key from src where key='4')
select * from v1;

```

##  lateral view explode
```sql

SELECT
    tmp.itemid as app_id,
    SPLIT_PART(tmp.sim_item_score,':',1) as sim_app,
    SPLIT_PART(tmp.sim_item_score,':',2) as sim_score
FROM (
    SELECT
        itemid,
        sim_item_score
    FROM
         ${t1}
    lateral view explode(split(similarity,' ')) similarity AS sim_item_score
) tmp
```
