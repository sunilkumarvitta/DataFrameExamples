package com.ex

import org.apache.spark.sql.SparkSession

object samp2 {
def main(args: Array[String]): Unit = {
    val sparksession = SparkSession.builder().master("local[*]").appName("test").getOrCreate()
  val text1 = sparksession.sparkContext.textFile("C:\\Sunil\\Spark\\Fractus\\dataframe-examples\\src\\main\\resources\\text1.txt")
 text1.foreach(println)
  val test = Seq(1 to 4)
  val test1 = sparksession.sparkContext.parallelize(test)
  test1.foreach(println)
  sparksession.close()
  }
}
