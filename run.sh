mvn package

docker cp ./target namenode:/target

docker cp ./bonjour.txt namenode:/bonjour.txt

docker exec -it namenode /bin/bash -c "hdfs dfs -mkdir -p /user/root && hdfs dfs -put -f /input/bonjour.txt /user/root/bonjour.txt"

# remove /output on the namenode
docker exec -it namenode /bin/bash -c "hdfs dfs -rm -r -f /output"

docker exec -it namenode /bin/bash -c "hadoop jar /target/Example-1.0-SNAPSHOT.jar WordCount /bonjour.txt /user/root/output"

