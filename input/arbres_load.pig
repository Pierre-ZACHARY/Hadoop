/*  Avant de charger le fichier "arbres.csv", Vous devez tout d'abord copier le fichier "arbres.csv" sur le HDFS dans le dossier "arbres" */

-- Pour charger le fichier .csv
arbres = LOAD '/user/hadoop/arbres/*' USING PigStorage(';') AS (geopoint:tuple(lat:double,lon:double),genre:chararray,espece:chararray,adresse:chararray,arron:chararray,circonf:double,hauteur:double,id:long,nom:chararray);

-- pour visualiser la structure de  l'alias arbres
DESCRIBE arbres;

--  pour afficher le contenu de la relation arbres
DUMP arbres;
