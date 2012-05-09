#!/bin/bash
if [ "$1" == "prod" ]; then 
  ENV="prod"
  HOST="kabbadi.thoughtworks.com"
else
  ENV="qa"
  HOST="10.4.3.1"
fi

/opt/gradle-1.0-rc-1/bin/gradle -P env=$ENV war
scp ./build/libs/root.war twu@$HOST:/opt/jetty/webapps
if [ "$1" == "prod" ]; then
	ssh twu@$HOST "~/restart-kabbadi"
else
	ssh twu@$HOST /etc/init.d/jetty restart
fi