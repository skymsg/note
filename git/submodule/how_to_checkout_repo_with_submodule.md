# how to checkout repository with submodule
their have to way 
## clone and initalize all submodule wihtin
```shell
git clone repo_url  --recursive
```

## first clone then init all submodule
```
git clone repo_url
git submodule update --init --recursive
```
