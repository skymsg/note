# presto 
## base64 decode
from_base64()
## utf8 decode
from_utf8
select from_utf8(from_base64(name)) from log;
