file = io.open("test.lua","w+")
file:write('print("hello world!")')
file:flush()
file:close()

read_f = io.open("test.lua",'r')
print(read_f:read())
read_f:close()
