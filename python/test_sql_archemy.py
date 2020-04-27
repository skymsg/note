from sqlalchemy import create_engine
import pandas as pd

engine = create_engine("mysql+pymysql://username:password@localhost:3306/db?charset=utf8mb4",pool_size=10,max_overflow=0)
conn = engine.connect()
result = pd.read_sql("select * from table where id <1000",conn)
result.to_csv("query_resultset.csv",index=False)
