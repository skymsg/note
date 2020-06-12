1. 下载jenkins rpm 包
https://pkg.jenkins.io/redhat-stable/
2. 安装
 rpm -ivh jenkins-2.156-1.1.noarch.rpm
3. 配置
vim /etc/sysconfig/jenkins
端口改为8082

4. 修改用户
JENKINS_USER="root"

5. 安装jdk 和 maven
注意要建立一个/usr/bin/java 软连接

6. service jenkins start

7. 暴露Jenkins地址   使用浏览器访问jenkins 完成初始化

8.  修改 /etc/rc.d/init.d/jenkins  
在
```
# Source function library.
. /etc/init.d/functions
```
下面加一行  . /etc/profile
导入系统环境变量


9. build base 镜像
base
java_base
python37_base

10. 进入Jenkins的插件管理界面
安装Docker Pipeline插件

11. 添加任务
