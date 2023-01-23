cd ..

mvn package

docker cp ./target namenode:/target

docker cp ./input namenode:/input

docker exec -it namenode /bin/bash -c "hdfs dfs -mkdir -p /user/root && hdfs dfs -put -f /input/bonjour.txt /user/root/bonjour.txt"

# remove /output on the namenode
docker exec -it namenode /bin/bash -c "hdfs dfs -rm -r -f /user/root/output"

docker exec -it namenode /bin/bash -c "hadoop jar /target/Example-1.0-SNAPSHOT.jar WordCount /user/root/bonjour.txt /user/root/output"

docker exec -it namenode /bin/bash -c "hdfs dfs -cat /user/root/output/part-r-00000"