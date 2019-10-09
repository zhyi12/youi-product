#server-exchange 数据交互服务

#server-metadata 元数据服务

#server-softwaresp 软件提供商服务

#传输文件
scp /Users/zhouyi/project/workspace-youi3/youi-product/services/server-metadata/target/server-metadata-1.0.0-SNAPSHOT.jar filing@192.168.173.130:/home/filing/gxb-project/server/server-metadata.jar
scp /Users/zhouyi/project/workspace-youi3/youi-product/services-common/server-file/target/server-file-1.0.0-SNAPSHOT.jar filing@192.168.173.130:/home/filing/gxb-project/server/server-file.jar
scp /Users/zhouyi/project/workspace-youi3/youi-product/services-common/server-registry/target/server-registry-2.0.0.M7.jar filing@192.168.173.130:/home/filing/gxb-project/server/server-registry.jar
scp /Users/zhouyi/project/workspace-youi3/youi-product/services-common/server-sso/target/server-sso-1.0.0-SNAPSHOT.jar filing@192.168.173.130:/home/filing/gxb-project/server/server-sso.jar


scp /Users/zhouyi/project/workspace-youi3/youi-product/services/server-metadata/src/main/resources/application-product.yml filing@192.168.173.130:/home/filing/gxb-project/config/config-server-metadata.yml
scp /Users/zhouyi/project/workspace-youi3/youi-product/services-common/server-file/src/main/resources/application-product.yml filing@192.168.173.130:/home/filing/gxb-project/config/config-server-file.yml
scp /Users/zhouyi/project/workspace-youi3/youi-product/services-common/server-registry/src/main/resources/application-product.yml filing@192.168.173.130:/home/filing/gxb-project/config/config-server-registry.yml
scp /Users/zhouyi/project/workspace-youi3/youi-product/services-common/server-sso/src/main/resources/application-product.yml filing@192.168.173.130:/home/filing/gxb-project/config/config-server-sso.yml

scp /Users/zhouyi/project/workspace-youi3/youi-product/services-common/server-sso/src/main/resources/keys/youi.keystore filing@192.168.173.130:/home/filing/gxb-project/keys

scp /Users/zhouyi/project/workspace-youi3/youi-product/wars/web/target/ROOT.war filing@192.168.173.130:/home/filing/gxb-project/webserver/webapps
scp /Users/zhouyi/project/workspace-youi3/youi-product/wars/web/src/main/resources/application-product.yml filing@192.168.173.130:/home/filing/gxb-project/config/config-web.yml
#服务器开放端口
firewall-cmd --add-port=9112/tcp --permanent
firewall-cmd --add-port=8250/tcp --permanent
firewall-cmd --add-port=8088/tcp --permanent
firewall-cmd --reload

#启动presto
