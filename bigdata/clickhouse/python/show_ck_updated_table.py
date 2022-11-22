#!/usr/bin/env python
# coding: utf-8

import redis
from clickhouse_driver import Client

config = {
    "rank_redis":{
        "host" : "localhost",
        "port" : 6379,
        "password" : "",
        "database" : 9
    },
    'bj_ck': {
        'host': 'localhost',
        'user': 'a',
        'pwd': 'b',
        'port': '3306'
    },
    'hk_ck': {
        'host': 'localhost',
        'user': 'a',
        'pwd': 'b',
        'port': '3306'
    }
}


dt = '2021-01-09'




def get_ck_client(db):
    user = config[db]["user"]
    password = config[db]["pwd"]
    host = config[db]["host"]
    port = config[db]["port"]
    client = Client(host=host, port=port, user=user, password=password,compression='lz4')
    return client


def get_redis_client(db):
    redis_client = redis.Redis(host=config[db]['host'],
                port=config[db]['port'],
                db=config[db]['database'],
               password=config[db]['password'])
    return redis_client




redis_client = get_redis_client('rank_redis')
bj_ck_client = get_ck_client('bj_ck')
hk_ck_client = get_ck_client('hk_ck')




def get_event_table_changed_dt(ck_client):
    sql = """
        select database,
           table,
           partition,
           max(modification_time)  update_time
        from system.parts
        where database!='system'
        group by 
            database,
            table,
            partition
        having update_time>(now() -900)
    """
    changed_partitions = ck_client.query_dataframe(sql)
    changed_dt = []
    for idx,row in changed_partitions.iterrows():
        table = row['table']
        partition = row['partition']
        if "dwd_bi_events" in table:
            changed_dt.append(partition)
    return changed_dt




changed_dt = get_event_table_changed_dt(bj_ck_client)
print("changed_dt",changed_dt)




def delete_query_cache_by_dt(redis_client,project_id,dt):
    for key in redis_client.scan_iter(f"env_*project_{project_id}:queryHash_*"):
        print(key,redis_client.hdel(key,dt))




delete_query_cache_by_dt(redis_client,1,'2022-01-10')
