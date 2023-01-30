cd ..

mvn package

docker cp ./target namenode:/

docker cp ./input namenode:/

docker exec -it namenode /bin/bash -c "hdfs dfs -mkdir -p /user/root"

docker exec -it namenode /bin/bash -c "hdfs dfs -rm -r -f /user/root/output/jointable"

docker exec -it namenode /bin/bash -c "hdfs dfs -put -f /input/data/join_input/inner/r /user/root/r"
docker exec -it namenode /bin/bash -c "hdfs dfs -put -f /input/data/join_input/outer/s /user/root/s"

docker exec -it namenode /bin/bash -c "hadoop jar /target/Example-1.0-SNAPSHOT.jar JoinTable /user/root/s /user/root/r /user/root/output/jointable"

# cat the file at /user/root/output/jointable/part-r-00000
docker exec -it namenode /bin/bash -c "hdfs dfs -cat /user/root/output/jointable/part-r-00000"