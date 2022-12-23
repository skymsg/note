-- open the file in append mode
file = io.open("test_write.csv","a")
io.output(file)
--apend a line to the file
io.write("1,2,3")
io.close(file)
