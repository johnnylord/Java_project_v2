build: server-build client-build
	

server: server-build server-run
	

server-build:
	ant server

server-run:
	java -jar ./build/server.jar

client: client-build client-run
	

client-build:
	ant client

client-run:
	java -jar ./build/client.jar

test: test-build test-run
	ant test
	java -jar ./build/test.jar

test-build:
	ant test

test-run:
	java -jar ./build/test.jar

clear:
	rm -rf ./bin
	rm -rf ./build
	rm -rf ./bak
	rm ./takedPhoto.png
