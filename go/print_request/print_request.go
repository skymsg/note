package main

import "fmt"

type http_request struct {
    host string
    port int
    path string
}

type tcp_request struct {
    host string
    port int
}
func print_any_request[T any](req T){
    fmt.Printf("%s\n",req)
    //fmt.Printf("host:%s\n",req.host)
    //fmt.Printf("port:%d\n",req.port)
}
func print_http_request(req http_request){
    fmt.Printf("host:%s\n",req.host)
    fmt.Printf("port:%d\n",req.port)
}

func main(){
    host,port,path:="localhost",80,"/helllo"
    http_req := http_request{
        host,port,path,
    }
    tcp_req := tcp_request{
        host,port,
    }
    print_http_request(http_req)
    //print_http_request(tcp_req)
    print_any_request(http_req)
    print_any_request(tcp_req)
}
