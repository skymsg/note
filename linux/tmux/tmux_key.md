# tmux key
* 启动tmux : tmux
* 开启新窗口: ctrl+b   c
* 重命名窗口: ctrl+b   ,
* 切换到上一个窗口: ctrl+b p
* 切换到下一个窗口: ctrl+b n
* 浏览所有窗口: ctrl+b w
* 垂直分屏:   ctrl+b %
* 水平分屏:  先ctrl+b "
* 切换分屏窗口: ctrl+b o
* 切换水平分屏和垂直分屏: ctrl+b 空格
* 切换pane全屏状态: ctrl + b, z
* 
* 创建session: tmux new -s sessionBackup
* detach session :  ctrl+b d
* 列出所有session ： tmux ls
* attach session:  tmux attach -t sessionBackup
* rename-session:   tmux rename-session -t sessionOldName sessionNewName
