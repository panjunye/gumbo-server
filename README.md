# Gumbo,一个Android APP的更新服务器
## 适用平台 linux-x86-64,windows-x86-64,macOS-darwin
## 使用方法
下载最新版本 gumbo-server.tar.gz

解压
$ tar xzvf gumbo-server.tar.gz

$ cd gumbo-server


$ vim conf/application.properties



spring.datasource.url=jdbc:mysql://数据库ip地址/qxyd_gumbo_server

spring.datasource.username=数据库用户名

spring.datasource.password=数据库密码

server.port=8080 端口


app.apk.dir=./gumbo-server/download

app.apk.download-base-url=http://服务器IP地址:端口/download

修改完成后保存退出

$ cd bin;

运行

./gumbod.sh start



