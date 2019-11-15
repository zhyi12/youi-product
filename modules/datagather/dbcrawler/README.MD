#数据源 etl 规则 (key: %catalog%_%schema% )


#数据源表数据的变化情况表(key: %catalog%_%schema%_%tableName% )
<ul>
    <li>
        字段：id,catalog,schema,最近加载时间-lastLoaderTime,下次加载时间-nextLoaderTime,最新数据时间 - lastDataTime
    </li>
    <li>
        规则1：定时（每30分组）扫描业务镜像库表数据（数据库日志监控或者max业务表时间戳字段方式），获取最新的数据时间
    </li>
<ul>

#数据源同步的任务

#