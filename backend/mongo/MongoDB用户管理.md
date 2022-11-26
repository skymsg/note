# MongoDB用户管理

# 创建用户
db.createUser({user:"root",pwd:"c2RmZHNmMjM0MzI",roles:[   {role:"readWrite",db:"engine" } ]})
db.createUser({user:"root",pwd:"c2RmZHNmMjM0MzI",roles:[   {role:"readWrite",db:"dw" } ]})

# 修改用户
db.grantRolesToUser("root", [{role:"readWrite", db:"dw"},{role:"readWrite", db:"engine"}]) 