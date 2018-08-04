package com.ex

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import org.apache.spark.sql.SparkSession

object Ex7_JsonfileToDataframe {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder.master("local[2]").appName("Dataframe Example").getOrCreate()

    sparkSession.sparkContext.hadoopConfiguration.set("fs.s3n.awsAccessKeyId", "AKIAILD7YRY57HZC3LYQ")
    sparkSession.sparkContext.hadoopConfiguration.set("fs.s3n.awsSecretAccessKey", "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")

    var currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
    currentDate = "20180123"
    val employeeDf = sparkSession.read.json("s3n://sunilvittarent2/employee_[1-9]_" + currentDate + ".json")

    employeeDf.printSchema()
    employeeDf.show()

    sparkSession.close()
  }
}
