# 句柄数 文件数 线程数
echo "* soft nofile 196605" >> /etc/security/limits.conf
echo "* hard nofile 196605" >> /etc/security/limits.conf
echo "* soft nproc 196605" >> /etc/security/limits.conf
echo "* hard nproc 196605" >> /etc/security/limits.conf

echo "kernel.threads-max=196605" >> /etc/sysctl.conf
echo "kernel.pid_max=196605" >> /etc/sysctl.conf
echo "vm.max_map_count=393210" >> /etc/sysctl.conf
sysctl -p  
