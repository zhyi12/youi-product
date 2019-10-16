#模块初始化maven 插件
Maven Projects
youi-module-init
    Plugins
        youi-framework
            youi-framework:moduleCreate
      
#模块配置示例

basePackage 用于配置包前缀

mongo配置 用于生成数据生成测试的连接
modules 用于配置模块所在的文件夹

    <build>
        <plugins>
            <plugin>
                <groupId>org.youi.framework.plugin</groupId>
                <artifactId>youi-framework-maven-plugin</artifactId>
                <version>${youi.version}</version>
                <configuration>
                    <basePackage>org.youi</basePackage>
                    <groupId>org.youi.product</groupId>

                    <modules>
                        <param>base/agencydatauth</param>
                        <param>base/agencydatauthImpl</param>
                        <param>base/dataccess</param>
                        <param>base/dataccessImpl</param>
                    </modules>
                    <mongoHost>192.168.173.130:27515</mongoHost>
                    <mongoUsername>query</mongoUsername>
                    <mongoPassword>query123</mongoPassword>
                    <mongoDatabase>youi3-common</mongoDatabase>
                </configuration>
            </plugin>
        </plugins>
    </build>