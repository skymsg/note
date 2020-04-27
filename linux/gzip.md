# gzip
## gzip -c 
hdfs dfs -cat filename | gzip -c | hdfs dfs -put -f - ${1}.gz
## gzip -d
gzip -d aaa.gz
