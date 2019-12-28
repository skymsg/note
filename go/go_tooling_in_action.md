# go tooling in action
## gofmt
gofmt [flags]  [path ..]
 -s simplify code
 -w write result to source file

## go install
 compile the source to $GOPATH/bin

## go build
 compile the source to the source file folder
### cross os compile 
 GOOS=windows go build

## goimports 
automatic imports package

## godoc
go doc package function
go doc -http 6060
