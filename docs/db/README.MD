##design -数据库设计

##mongo - mongo初始化脚本

mongo collection导入示例,进入mongo bin文件下操作
./mongoimport -h localhost:27515 -d youi3-common -u query -p 'query123' -c youi_role  /home/filing/youi_role.bson
./mongoimport -h localhost:27515 -d youi3-common -u query -p 'query123' -c youi_sso_auth_client  /home/filing/youi_sso_auth_client.bson
./mongoimport -h localhost:27515 -d youi3-common -u query -p 'query123' -c youi_user_roles  /home/filing/youi_user_roles.bson

##mysql - mysql初始化脚本

youi3-common.sql 平台提供的登录用户初始化脚本
   
平台用户： 10010001/123456
