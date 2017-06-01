#!/bin/bash
GUMBO_HOME=$(dirname $(dirname $(readlink -f "$0")))
echo $GUMBO_HOME
SERVICE_NAME="gumbod"
JAR_NAME=$GUMBO_HOME/gumbo-server.jar
LOGS=$GUMBO_HOME/logs
TMP=$GUMBO_HOME/tmp
PID=$TMP/gumbo.pid
DOWNLOAD=$GUMBO_HOME/download
CONF=$GUMBO_HOME/conf
JAVA_OPTS=--spring.config.location=$GUMBO_HOME/conf/application.properties

mkdir -p $LOGS
mkdir -p $TMP
mkdir -p $DOWNLOAD

case "$1" in
start)
	nohup java -jar $JAR_NAME $JAVA_OPTS > $LOGS/gumbo.logs 2>&1 &
	echo $!>$PID
	echo "$SERVICE_NAME started."
;;

stop)
	kill `cat $PID`
	rm -rf $PID
	echo "$SERVICE_NAME stopped."
;;

restart)
	$0 stop
	$0 start
	echo "$SERVICE_NAME restarted."
;;

status)
	if [ -e "$PID" ] 
	then
		echo gumbo is running,pid=`cat $PID`
	else
		echo gumbod is stopped.
		exit 1
	fi
;;

*)
	echo "Usage: $0 {start|stop|status|restart}"
esac

exit 0
