#!/usr/bin/expect
set timeout -1
spawn javac -g ./foreach/src/main/java/pl/lodz/p/it/isdp/Start.java ./foreach/src/main/java/pl/lodz/p/it/isdp/SortTabNumbers.java
spawn jdb -classpath ./foreach/target/classes -sourcepath ./foreach/src/main/java  pl.lodz.p.it.isdp.Start 2
expect ">"
send "stop in pl.lodz.p.it.isdp.SortTabNumbers.<init>\r"
expect ">"
send "run\r"
expect -re "main... "
send "step\r"
expect -re "main... "
send "step\r"
for {set i 0} {$i < 2} {incr i 1} {
        expect -re "main... "
        send "step\r"
	expect -re "main... "
	send "step\r"
	expect -re "main... "
	send "set tab\[$i\] = 9\r"
}
expect -re "main... "
send "dump tab\r"
expect eof
