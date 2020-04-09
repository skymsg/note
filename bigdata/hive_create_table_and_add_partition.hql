CREATE external TABLE `logs`(
		`id` STRING,
		`name` STRING
)
PARTITIONED BY (
		`dt` string
)
ROW FORMAT DELIMITED 
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
STORED AS TEXTFILE LOCATION 'hdfs://localhost:9000/logs';


ALTER TABLE logs  ADD PARTITION(dt='2020-04-01') LOCATION 'hdfs://localhost:9000/logs/dt=2020-04-01';
select count(*) from logs where dt ='2020-04-02';

ALTER TABLE logs DROP IF EXISTS PARTITION(dt='2020-04-02');
drop  table logs
