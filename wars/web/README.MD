#presto
 ## 用于连接各种镜像数据库
#mongo
 ## youi3-common  公共库（文件、登录信息等）
 ## stats2-filing 业务库 (元数据)
#mysql
 ## 用户登录信息存储
#gxb-project/config

替换各种地址配置为实际服务器的地址，里面的localhost不用替换，内部代理使用

#webserver 配置 gxb-project/webserver/bin/catalina.sh 文件中的固定路径
配置启动参数
export JAVA_HOME="/home/filing/gxb-project/jdk"
export JAVA_OPTS="-server -Xms1024m -Xmx2048m  -Djava.awt.headless=true -Dfile.encoding=UTF8 -Dspring.config.location=/home/filing/gxb-project/config/config-web.yml"

特别注意JSESSIONID的名称，SSO和web的配置需要不同


#上传组件使用
    <youi:script src="/asserts/js/webuploader/webuploader.min.js"/>
    <youi:script src="/asserts/js/webuploader/field.webuploader.js"/>
    <youi:style href="/asserts/js/webuploader/webuploader.css"/>

    <youi:form id="form_upload" action="a.json">
        <youi:fieldLayout columns="1" labelWidths="100">
            <youi:fieldCustom property="xlsFileName" custom="fieldWebuploader" notNull="true"
                              customOptions="{limits:1,mineTypeCaption:'',mineTypeExtensions:'xls,xlsx'}" caption="选择文件"/>
    
        </youi:fieldLayout>
    </youi:form>