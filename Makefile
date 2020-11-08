
build: clean
	mvn package

exec:
	mvn exec:java -Dexec.mainClass="com.mydeo.seckilldemo.SeckilldemoApplication"

clean:
	mvn clean
