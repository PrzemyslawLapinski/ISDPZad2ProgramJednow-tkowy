#!/usr/bin/expect
set timeout -1
spawn javac -g ./3/src/main/java/pl/lodz/p/it/isdp/Start.java ./3/src/main/java/pl/lodz/p/it/isdp/SortTabNumbers.java 
spawn jdb -classpath ./3/target/classes -sourcepath ./3/src/main/java  pl.lodz.p.it.isdp.Start 2
expect ">"
send "stop in pl.lodz.p.it.isdp.SortTabNumbers.sort\r"
expect ">"
send "run\r"
expect -re "main... "
send "step\r"
set counter 0
for {set i 0} {$i < 1} {incr i 1} {
	for {set j 1} {$j < 2} {incr j 1} {
		expect -re "main... "
	        send "step\r"
		incr counter 1
		expect -re "main... "
		send "step\r"
		expect -re "main... "
		send "next\r"
	}
	expect -re "main... "
	send "step\r"
	expect -re "main... "
	send "step\r"
}
expect -re "main... "
send_user "Liczba porównań=$counter\r"
expect eof

