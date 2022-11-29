# 内存配额
java在k8s中内存配额要大于java命令行参数的-Xmx ,不然启动后内存就会占满
k8s就会kill进程
