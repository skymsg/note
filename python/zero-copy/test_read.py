with open("test_read.py","rb") as f:
    data = f.read(65536)
    print(data)
    while data:
        data = f.read(65536)
        print(data)
