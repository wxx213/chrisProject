# request seckill
#   curl --location --request POST 'http://192.168.2.101:9997/seckill/go?userId=wxx&goodsId=1&num=1'
# query result
#   curl --location --request GET 'http://192.168.2.101:9997/seckill/result?userId=wxx&goodsId=1'
#
build: clean
	mvn package

exec:
	mvn exec:java -Dexec.mainClass="com.mydeo.seckilldemo.SeckilldemoApplication"

clean:
	mvn clean
