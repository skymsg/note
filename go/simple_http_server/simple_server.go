package main
import (
    "io"
    "net/http"
)

func helloHandle(w http.ResponseWriter,r *http.Request){
    io.WriteString(w,"hello\n")
}

func main(){
    http.HandleFunc("/hello",helloHandle) 
    http.ListenAndServe(":3000",nil) 
}

