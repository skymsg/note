#hbase
## get
get 'table' , rowkey
## scan
scan 'table', { CLOUMNS => ['c1','c2'], LIMIT => 10}
scan 'table', {COLUMNS => 'info', FILTER => SingleColumnValueFilter.new(Bytes.toBytes('info'), Bytes.toBytes('userId'), CompareFilter::CompareOp.valueOf('EQUAL'), Bytes.toBytes('1012'))}
