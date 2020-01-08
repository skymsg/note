from elasticsearch import Elasticsearch
import json

if __name__ == "__main__":
    app_name_set = set()
    es_client = Elasticsearch(['localhost:9200'])
    index='topic'
    response = es_client.search(
        index='topic',
        body={
            "size": 2900,
            "query": {
                "match_all": {
                }
            },"_source":["title","summary","forum_title"]
        },
        scroll='1h'
    )
    scroll_id = response['_scroll_id']
    total = 0
    while scroll_id:
        count = len(response['hits']['hits'])
        if count<=0:
            break
        total+=count
        print(total)
        bulk_docs = []
        for hits in response['hits']['hits']:
            source = hits['_source']
            doc_id = int(hits['_id'])
            data= {}
            data['id'] = doc_id
            title = source.get('title','')
            summary = source.get('summary','')
            forum_title = source.get('forum_title',"")
            if forum_title is None:
                print(source)
                print(doc_id)
            forum_title_concat_title = forum_title + " "+title
            forum_title_concat_title = forum_title_concat_title.lower()
            data['title_init'] = forum_title_concat_title
            data['title_not_analyzed'] = forum_title_concat_title
            data['title_smart'] = forum_title_concat_title
            data['title_max'] = forum_title_concat_title
            data['title_std'] = title

            forum_title_concat_summary = forum_title + " "+summary
            forum_title_concat_summary = forum_title_concat_summary.lower()
            data['summary_init'] = forum_title_concat_summary
            data['summary_not_analyzed'] = forum_title_concat_summary
            data['summary_smart'] = forum_title_concat_summary
            data['summary_max'] = forum_title_concat_summary
            data['summary_std'] = summary
            index_doc={}
            index_doc['update'] = {
                     '_index': index,
                     '_type': '_doc',
                     '_id': doc_id
                     }
            doc_values ={"doc": data}
            bulk_docs.append(index_doc)
            bulk_docs.append(doc_values)
        if len(bulk_docs) > 0:
            es_client.bulk(bulk_docs, '_doc', index)
        response = es_client.scroll(scroll_id=scroll_id, scroll='1h')
        scroll_id = response['_scroll_id']
