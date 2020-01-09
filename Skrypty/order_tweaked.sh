#!/usr/bin/expect
set timeout -1
spawn javac -g ./5/src/main/java/pl/lodz/p/it/isdp/Start.java ./5/src/main/java/pl/lodz/p/it/isdp/SortTabNumbers.java 
spawn jdb -classpath ./5/target/classes -sourcepath ./5/src/main/java  pl.lodz.p.it.isdp.Start 2
expect ">"
send "stop in pl.lodz.p.it.isdp.SortTabNumbers.checkMinOrderSort\r"
expect ">"
send "run\r"
expect -re "main... "
send "cont\r"
expect eof

