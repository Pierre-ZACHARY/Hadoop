-- Charger les données depuis le répertoire /user/hadoop/gutenberg
data = LOAD '/user/hadoop/gutenberg/*.txt' AS (line:chararray);

-- Diviser les lignes en mots
words = FOREACH data GENERATE FLATTEN(TOKENIZE(line)) as word;

-- Compter le nombre d'occurrences de chaque mot
word_count = GROUP words BY word;
word_count = FOREACH word_count GENERATE group as word, COUNT(words) as count;

-- Stocker les résultats dans le répertoire /user/hadoop/gutenberg-pig-out
STORE word_count INTO '/user/hadoop/gutenberg-pig-out' USING PigStorage();