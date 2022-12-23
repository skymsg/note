-- open the file
f = io.open("for_loop.lua",'r')

-- set the file as input 
io.input(f)

-- prints the first line of the file
line = io.read('*l')
while (line ~= nil)
do
    print(line)
    line = io.read()
end

-- closes the open file
io.close(f)


lines = io.lines("iterator.lua")
for line in lines
do
    print(line)
end
