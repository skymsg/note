# 句柄数 文件数 线程数
echo "* soft nofile 196605" >> /etc/security/limits.conf
echo "* hard nofile 196605" >> /etc/security/limits.conf
echo "* soft nproc 196605" >> /etc/security/limits.conf
echo "* hard nproc 196605" >> /etc/security/limits.conf
