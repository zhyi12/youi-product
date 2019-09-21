#分布式文件服务

在服务中使用fileClientManager（org.youi.framework:youi-framework-mongo-fileClient）获取分布式文件

在服务中配置文件库连接
youi:
  file:
    client:
      mongo:
        sameMongo: false
        uri: mongodb://query:query123@192.168.173.130:27515/stats2-common

@Autowired
private FileClientManager fileClientManager;

##解析文件，文件解析后继续留存
fileClientManager.parseFile
##消费文件，文件消费后即删除
fileClientManager.consumeFile