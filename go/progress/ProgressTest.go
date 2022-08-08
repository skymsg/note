package main

import (
	"time"

)

func main() {
	count := 10000
	for i := 0; i < count; i++ {
		time.Sleep(time.Millisecond)
	}
}
