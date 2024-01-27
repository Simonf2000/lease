以下是配置文件相关(application.yml)：
server:<br />
  port: 8081<br />

spring:<br />
  datasource:<br />
    type: com.zaxxer.hikari.HikariDataSource<br />
    url: jdbc:mysql://localhost:3306/lease?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=GMT%2b8 <br />
    username: root<br />
    password: root<br />
    hikari:<br />
      connection-test-query: SELECT 1 # 自动检测连接<br />
      connection-timeout: 60000 #数据库连接超时时间,默认30秒<br />
      idle-timeout: 500000 #空闲连接存活最大时间，默认600000（10分钟）<br />
      max-lifetime: 540000 #此属性控制池中连接的最长生命周期，值0表示无限生命周期，默认1800000即30分钟<br />
      maximum-pool-size: 12 #连接池最大连接数，默认是10<br />
      minimum-idle: 10 #最小空闲连接数量<br />
      pool-name: SPHHikariPool # 连接池名称<br />
  data:<br />
    redis:<br />
      host: 192.168.6.100<br />
      port: 6379<br />
      database: 0<br />

#用于打印框架生成的sql语句，便于调试<br />
mybatis-plus:<br />
  configuration:<br />
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl<br />

springdoc:<br />
  default-flat-param-object: true<br />

aliyun:<br />
  sms:<br />
    access-key-id: <access-key-id><br />
    access-key-secret: <access-key-secret><br />
    endpoint: dysmsapi.aliyuncs.com<br />

minio:<br />
  endpoint: http://192.168.6.100:9000<br />
  access-key: minioadmin<br />
  secret-key: minioadmin<br />
  bucket-name: minio-bucket<br />