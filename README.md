## Gumbo,一个Android应用的增量更新服务器
### 使用方法
下载 gumbo-server.tar.gz

```
$ tar xzvf gumbo-server.tar.gz
$ cd gumbo-server
$ vim conf/application.properties
```
修改application.properties里的内容，并保存
```
spring.datasource.url=jdbc:mysql://数据库ip地址/database
spring.datasource.username= #数据库用户名
spring.datasource.password= #数据库密码
server.port=8080  # 端口
app.apk.dir=./gumbo-server/download # apk存放的目录
app.apk.download-base-url=http://服务器IP地址:端口/download
```
$ cd bin;
$ sh ./gumbod.sh start # 启动服务



