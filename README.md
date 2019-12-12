# youi-product

##modules - 业务模块文件夹

##service - 业务服务文件夹

##service-common - 公共服务文件夹

##wars - web应用文件夹

#一、系统启动（filing用户）

##1、启动mysql (3307)
    cd /home/filing/gxb-project/bin
    sh start-mysql.sh
##2、启动mongodb(27515)
    cd /home/filing/gxb-project/mongodb4
    sh start-mongo.sh
##3、启动prestodb(8071)
    cd /home/filing/gxb-project/presto-server-0.224/bin/
    ./launcher start
##4、启动服务(8761,8868,9112,8250)
    cd /home/filing/gxb-project/bin
    sh start-registry.sh
    sh start-sso.sh
    sh start-file.sh
    sh start-metadata.sh
##5、启动应用(8088)
    cd /home/filing/gxb-project/webserver/bin
    sh startup.sh

#二、开放CentOS7端口(root用户)
    firewall-cmd --add-port=3307/tcp --permanent
    firewall-cmd --add-port=27515/tcp --permanent
    firewall-cmd --add-port=8071/tcp --permanent
    firewall-cmd --add-port=8761/tcp --permanent
    firewall-cmd --add-port=8868/tcp --permanent
    firewall-cmd --add-port=9112/tcp --permanent
    firewall-cmd --add-port=8250/tcp --permanent
    firewall-cmd --add-port=8088/tcp --permanent
    firewall-cmd --reload
#三、系统关闭（filing用户）

##1、关闭mysql (3307)
    cd /home/filing/gxb-project/bin
    sh stop-mysql.sh
##2、关闭mongodb(27515)
    
##3、关闭prestodb(8071)
    cd /home/filing/gxb-project/presto-server-0.224/bin/
    ./launcher stop
##4、关闭服务(8761,8868,9112,8250)
    cd /home/filing/gxb-project/bin
    sh stop-registry.sh
    sh stop-sso.sh
    sh stop-file.sh
    sh stop-metadata.sh
##5、关闭应用(8088)
    cd /home/filing/gxb-project/webserver/bin
    sh shutdown.sh
    
#四、其他操作命令
## 替换confing中的IP地址
    #把当前文件夹下的yml文件里面的192.168.43.223替换成192.168.173.130
    cd /home/filing/gxb-project/config
    sed -i s/192.168.43.223/192.168.173.130/g ./*.yml