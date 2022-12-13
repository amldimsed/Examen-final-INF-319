from pyspark import SparkContext
from pyspark.streaming import StreamingContext

#Iinicializar

sc = SparkContext("local[2]","NetworkWordCount")
scc = StreamingContext(sc,10)
lineas = scc.socketTextStream("localhost", 9099)

#procesamiento
words = lineas.flatMap(lambda lineas: lineas.split(" "))
pairs = words.map(lambda word: (word, 1))
wordCounts = pairs.reduceByKey(lambda x, y: x + y)

wordCounts.pprint()

scc.start()
scc.awaitTermination()

#pequeño servidor
#nc -lk 9099

#ejecutar spark archivo
#spark-submit --master spark://andres-ads:7077 /home/andres/Documentos/streamingpaypay.py
