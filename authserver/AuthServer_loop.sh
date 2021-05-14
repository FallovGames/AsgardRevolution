#!/bin/bash

while :;
do
	java -server -Duser.timezone=GMT+5 -Dfile.encoding=UTF-8 -Xmx256m -cp config:../lib/* l2ar.authserver.AuthServer > log/stdout.log 2>&1

	[ $? -ne 2 ] && break
	sleep 10;
done
