with open("file_for_test","w") as f:
    t = "just_for_test"*10 +"\n"
    for i in range(15000000):
        f.write(t)
