GameClient: *
	javac -cp .:../lib/bridj-0.7-20140918.jar:../lib/dx-1.7.jar:../lib/logback-classic-1.0.13.jar:../lib/logback-core-1.0.13.jar:../lib/slf4j-api-1.7.2.jar:../lib/webcam-capture-0.3.12-20161206.184756-3.jar GameClient.java

run:
	java -cp .:../lib/bridj-0.7-20140918.jar:../lib/dx-1.7.jar:../lib/logback-classic-1.0.13.jar:../lib/logback-core-1.0.13.jar:../lib/slf4j-api-1.7.2.jar:../lib/webcam-capture-0.3.12-20161206.184756-3.jar GameClient

Windows:
	javac -cp ".;../lib/bridj-0.7-20140918.jar;../lib/dx-1.7.jar;../lib/logback-classic-1.0.13.jar;../lib/logback-core-1.0.13.jar;../lib/slf4j-api-1.7.2.jar;../lib/webcam-capture-0.3.12-20161206.184756-3.jar" GameClient.java

Server:
	javac -cp .:../lib/json.jar GameServer.java
	javac ./event/ExampleObject.java

Server-run:
	java -cp .:../lib/json.jar GameServer

WinServer:
	javac -cp ".;../lib/json.jar" GameServer.java
	javac ./event/ExampleObject.java

WinServer-run:
	java -cp ".;../lib/json.jar" GameServer

Client: 
	javac -cp .:../lib/json.jar UnitTest.java

Client-run: 
	java -cp .:../lib/json.jar UnitTest

WinClient: 
	javac -cp ".;../lib/json.jar" UnitTest.java

WinClient-run: 
	java -cp ".;../lib/json.jar" UnitTest
