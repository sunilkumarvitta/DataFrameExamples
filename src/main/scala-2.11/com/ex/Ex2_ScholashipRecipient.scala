package com.ex

import org.apache.spark.sql.SparkSession

/**
  * Count: Swiss students who have debt & financial dependents:
  * 1. Filter down the data set first (look at only people with debt & financial dependents)
  * 2. Filter to select people in Switzerland (look at only people in Switzerland)
  * 3. Inner join on smaller, filtered down data set
  *
  */
object Ex2_ScholashipRecipient {
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

    val filteredFin = finances.filter(p => p._2.hasFinancialDependents && p._2.hasDebt)
    val filteredDem = demographics.filter(p => p._2.country == "Switzerland")
    filteredFin.join(filteredDem).foreach(println)

    sparkSession.close()
  }
}