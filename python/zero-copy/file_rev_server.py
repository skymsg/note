import socket
import hashlib
sock = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
server_address = ('0.0.0.0',10000)
print(f'start up on host:{server_address[0]} port:{server_address[1]}')
sock.bind(server_address)
sock.listen(1)

while True:
    print("waiting for a connection")
    connection, client_address = sock.accept()
    size = 0
    try:
        i = 0
        while True:
            data = connection.recv(65536)
            i+=1
            if data:
                size+=len(data)
                if i%10000==0:
                    print(f"current loop:{i}")
            else:
                print("Done!")
                break
        print(f"loop:{i} total size:{size}")
        #print("hash of received data: %s",hashlib.md5(data).hexdigest())
    finally:
        connection.close()
