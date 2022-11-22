#!/usr/bin/env python
# coding: utf-8

# In[4]:


from clickhouse_driver import Client
from collections import defaultdict
import re


# In[5]:


config = { 
'clickhouse_tap_bi': {
        'host': '',
        'user': '',
        'pwd': '',
        'port': '3306'
    }
}


# In[6]:


def get_client(db):
    user = config[ db]["user"]
    password = config[ db]["pwd"]
    host = config["%s" % db]["host"]
    port = config["%s" % db]["port"]
    client = Client(host=host, port=port, user=user, password=password,compression='lz4')
    return client


# In[7]:


client_v2 = get_client("clickhouse_tap_bi")


# In[ ]:


df = 
table = 
values = []
for idx,row in df.iterrows():
    value={}
    for col in df.columns:
        value[col]=row[col]
    values.append(value)
print(len(values))
if len(values)>0:
    cols= ",".join(df.columns)
    client_v2.execute(f"INSERT INTO  {table}({cols}) VALUES ",values)

