# hbase
## get
get 'table' , rowkey
## scan
scan 'table', { CLOUMNS => ['c1','c2'], LIMIT => 10}
scan 'table', {COLUMNS => 'info', FILTER => SingleColumnValueFilter.new(Bytes.toBytes('info'), Bytes.toBytes('userId'), CompareFilter::CompareOp.valueOf('EQUAL'), Bytes.toBytes('1012'))}

## delete
delete 'table','row','cloumnFamily','timestamp'
deleteall 'table','row'

## rename table
因为hbase中没有rename命令，所以更改表名比较复杂。重命名主要通过hbase的快照功能。

1. 停止表继续插入
hbase shell>disable 'tableName'

2. 制作快照
hbase shell> snapshot 'tableName', 'tableSnapshot'

3. 克隆快照为新的名字
hbase shell> clone_snapshot 'tableSnapshot', 'newTableName'

4. 删除快照
hbase shell> delete_snapshot 'tableSnapshot'

5. 删除原来表
hbase shell> drop 'tableName'
