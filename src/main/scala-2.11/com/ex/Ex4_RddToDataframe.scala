package com.ex

import org.apache.spark.sql.SparkSession

/**
  * Created by sidhartha.ray on 27-11-2017.
  */
object Ex4_RddToDataframe {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder.master("local[*]").appName("Dataframe Example").getOrCreate()

    sparkSession.sparkContext.hadoopConfiguration.set("fs.s3n.awsAccessKeyId", "AKIAILD7YRY57HZC3LYQ")
    sparkSession.sparkContext.hadoopConfiguration.set("fs.s3n.awsSecretAccessKey", "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")

    val demographicsRawRDD = sparkSession.sparkContext.textFile("s3n://sunilvittarent2/demographic.csv")
    val demographicsRDD = demographicsRawRDD.map(record => record.split(","))
      .map(record => (record(0).toInt, record(1).toInt, record(2).toBoolean,
        record(3), record(4), record(5).toBoolean, record(6).toBoolean))

    // Convert to DataFrame
    import sparkSession.sqlContext.implicits._
    val demographicsDF = demographicsRDD.toDF
//    val demographicsDF = demographicsRDD.toDF("id", "age", "codingBootcamp", "country", "gender", "isEthnicMinority", "servedInMilitary")
    demographicsDF.show()
    sparkSession.close()
  }
}
