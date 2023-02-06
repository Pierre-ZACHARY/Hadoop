arbres = LOAD '/user/hadoop/arbres/*' USING PigStorage(';') AS (geopoint:tuple(lat:double,lon:double),genre:chararray,espece:chararray,adresse:chararray,arron:chararray,circonf:double,hauteur:double,id:long,nom:chararray);

result = FOREACH (GROUP arbres by genre) GENERATE group, AVG(arbres.hauteur), MIN(arbres.hauteur), MAX(arbres.hauteur);

STORE result INTO '/user/hadoop/arbres-out' USING PigStorage();