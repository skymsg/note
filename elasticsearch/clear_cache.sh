curl  -H "Content-Type:application/json" -XPOST 'http://localhost:9200/elasticsearch/_cache/clear' -d '{ "fielddata": "true" }'  
