/*  Avant de charger le fichier "arbres.csv", Vous devez tout d'abord copier le fichier "arbres.csv" sur le HDFS dans le dossier "arbres" */

-- Pour charger le fichier .csv
arbres = LOAD '/user/hadoop/arbres/*' USING PigStorage(';') AS (geopoint:tuple(lat:double,lon:double),genre:chararray,espece:chararray,adresse:chararray,arron:chararray,circonf:double,hauteur:double,id:long,nom:chararray);

-- Recupere l'adresse et l'arrondissement de l'arbre ayant la hauteur maximale without group and filter
resultat =