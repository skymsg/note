# ssh
~/.ssh/config
```
Host github.com
    Hostname github.com
    ServerAliveInterval 55
    ForwardAgent yes
    ProxyCommand nc -x 192.168.10.1:7070 %h %p
```
# https
 ~/.gitconfig
```
[http]
[http "https://github.com"]
	proxy = http://proxyUsername:proxyPassword@proxy.server.com:port
	sslVerify = false
```
