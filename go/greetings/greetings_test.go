package greetings

import (
    "testing"    
    "regexp"
)

func TestHelloName(t *testing.T) {
    name := "Gladys"
    want := regexp.MustCompile(`\b`+name+`\b`)
    msg,err := Hello("Gladys")
    if !want.MatchString(name)||err!=nil{
        t.Fatalf(`Hello("Gladys") = %q,%v, want match for %#q,nil`,msg,err,want)
    }
}


func TestEmptyName(t *testing.T) {
    msg,err := Hello("")
    if msg!=""||err==nil{
        t.Fatalf(`Hello("") = %q,%v`,msg,err)
    }
}
