cd /root
yum install htop -y
yum install git -y
cd /root/.ssh

cat <<EOF > id_rsa
-----BEGIN RSA PRIVATE KEY-----
xxxxxxxxx
-----END RSA PRIVATE KEY-----
EOF

echo 'ssh-rsa ksfdjkfas;fk' > id_rsa.pub
chmod 0600 id_rsa
cd /root/code
git clone xxxxx/elasticsearch.git



groupadd es
useradd -m -g es es
cp -r /root/code/elasticsearch /home/es/
chown -R es:es /home/es/elasticsearch



#!/bin/bash 
echo "* soft nofile 65536" >> /etc/security/limits.conf 
echo "* hard nofile 65536" >> /etc/security/limits.conf 
echo "* soft memlock unlimited" >> /etc/security/limits.conf 
echo "* hard memlock unlimited" >> /etc/security/limits.conf 
echo "vm.max_map_count = 262144" >> /etc/sysctl.conf 
sysctl -p 
ulimit -l unlimited


