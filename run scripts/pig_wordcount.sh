cd ..

docker cp ./input namenode:/

docker exec -it namenode /bin/bash -c "hdfs dfs -mkdir -p user/hadoop/"
docker exec -it namenode /bin/bash -c "hdfs dfs -put /input/data/gutenberg /user/hadoop/"

docker exec -it namenode /bin/bash -c "bash /root/pig/bin/pig -x mapreduce /input/WordCount.pig"
