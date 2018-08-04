package com.ex

import org.apache.spark.sql.SparkSession

/**
  * Count: Swiss students who have debt & financial dependents:
  * 1. Cartesian product on both the datasets
  * 2. Filter to select resulting of cartesian with same IDs
  * 3. Filter to select people in Switzerland who have debt and financial dependents
  *
  */
object Ex3_ScholashipRecipient {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder.master("local[*]").appName("Dataframe Example").getOrCreate()

    sparkSession.sparkContext.hadoopConfiguration.set("fs.s3n.awsAccessKeyId", "AKIAILD7YRY57HZC3LYQ")
    sparkSession.sparkContext.hadoopConfiguration.set("fs.s3n.awsSecretAccessKey", "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")

    val demographicsRDD = sparkSession.sparkContext.textFile("s3n://sunilvittarent2/demographic.csv")
    val financesRDD = sparkSession.sparkContext.textFile("s3n://sunilvittarent2/finances.csv")

    val demographics = demographicsRDD.map(record => record.split(","))
        .map(record => Demographic(record(0).toInt, record(1).toInt, record(2).toBoolean,
              record(3), record(4), record(5).toBoolean, record(6).toBoolean, record(7).toInt))
        .map(demographic => (demographic.id, demographic))      //Pair RDD, (id, demographics)
    val finances = financesRDD.map(record => record.split(","))
          .map(record => Finance(record(0).toInt, record(1).toBoolean, record(2).toBoolean,
              record(3).toBoolean, record(4).toInt))
        .map(finance => (finance.id, finance))                  //Pair RDD, (id, finances)

    val cartesian = demographics.cartesian(finances)
    cartesian.filter{
      case (dem, fin) => dem._1 == fin._1
    }.filter{
      case(dem, fin) =>
        (dem._2.country == "Switzerland") && (fin._2.hasDebt) && (fin._2.hasFinancialDependents)
    }.foreach(println)

    sparkSession.close()
  }
}