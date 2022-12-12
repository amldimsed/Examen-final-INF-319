//+++++++++++++++++++++++++INCISO 1+++++++++++++++++++++++++++++

//-----------------------Ejemplo de pagina oficial------------------------------------------------

//punto de entrada a toda la funcionalidad de Spark es la SparkSessionclase. Para crear una sesion
import org.apache.spark.sql.SparkSession
val spark = SparkSession
  .builder()
  .appName("Spark SQL basic ejemplo")
  .config("simple-app","some-value")
  .getOrCreate()
val dataSet:Dataset[String]=spark.read.texFile("/home/andres/Documentos/ads.csv")
val df:DataFrame=dataSet.toDF()

//****************************lectura de archivo alternativa2************************************************

val path = "/home/andres/Documentos/ads.csv"//lectura de archivo scv
val df2 = spark.read.option("delimiter", ";").csv(path)
df2.show()
//---------------------------------------------------------------------------------------------------------


//+++++++++++++++++++++++++INCISO 2+++++++++++++++++++++++++++++

//-----------------------Ejemplo de pagina oficial------------------------------------------------

val streamingContext:StreamingContext=new StreamingContext(sparkContext, Seconds(20))
val lines:ReceiveInputDStream[String]=streamingContext.socketTextStream("localhost",9999)
//------------------------------------------------------------------------------------------------

import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._ 


// Cree un StreamingContext local con dos subprocesos de trabajo y un intervalo de lote de 1 segundo.
// El maestro requiere 2 núcleos para evitar un escenario de inanición

val conf = new SparkConf().setMaster("local[2]").setAppName("NetworkWordCount")
val ssc = new StreamingContext(conf, Seconds(1))

val lines = ssc.socketTextStream("localhost", 9999)
