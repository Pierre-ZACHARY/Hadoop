cd ..

docker cp ./input namenode:/

docker exec -it namenode /bin/bash -c "hdfs dfs -mkdir -p /user/hadoop/arbres/"
docker exec -it namenode /bin/bash -c "hdfs dfs -put -f /input/data/arbres.csv /user/hadoop/arbres/arbres.csv"


docker exec -it namenode /bin/bash -c "bash /root/pig/bin/pig -x mapreduce /input/arbres_load.pig"
