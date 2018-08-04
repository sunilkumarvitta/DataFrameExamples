package com.ex

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._

/*
  * 1. Reading multiple files from S3
  * 2.
 */
object Ex8_SqlLiterals {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder.master("local[2]").appName("Dataframe Example").getOrCreate()

    sparkSession.sparkContext.hadoopConfiguration.set("fs.s3n.awsAccessKeyId", "AKIAILD7YRY57HZC3LYQ")
    sparkSession.sparkContext.hadoopConfiguration.set("fs.s3n.awsSecretAccessKey", "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")

    var currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
    currentDate = "20171009"

    val regisDFTemp = sparkSession.read
      .option("header","true")
      .option("delimiter", "|")
      .csv("s3n://sunilvittarent2/KC_Extract_[1-9]_" + currentDate + ".csv")
      .toDF

    val regisDF = regisDFTemp
      .withColumn("REGIS_CTY_CODE", regisDFTemp("REGIS_CTY_CODE").cast(IntegerType))
      .withColumn("REGIS_ID", regisDFTemp("REGIS_ID").cast(LongType))
      .withColumn("CHILD_ID", regisDFTemp("CHILD_ID").cast(LongType))
      .withColumn("CHILD_NB", regisDFTemp("CHILD_NB").cast(IntegerType))

    val regisDim = regisDF
      .select(
        regisDF("REGIS_CNTRY_CODE"),
        regisDF("REGIS_CTY_CODE"),
        regisDF("REGIS_ID"),
        regisDF("REGIS_LTY_ID"),
        regisDF("REGIS_CNSM_ID"),
        regisDF("REGIS_DATE"),
        regisDF("REGIS_TIME"),
        regisDF("REGIS_CHANNEL"),
        regisDF("REGIS_GENDER"),
        regisDF("REGIS_CITY"))
      .distinct()

    val childDim = regisDF
      .select(
        regisDF("REGIS_CTY_CODE"),
        regisDF("REGIS_ID"),
        regisDF("CHILD_ID"),
        regisDF("CHILD_NB"),
        regisDF("CHILD_GENDER"),
        regisDF("CHILD_DOB"),
        regisDF("CHILD_DECEASED"))

    println("Regis count = " + regisDF.count())
    println("Child Dim count = " + childDim.count())
    println("Regis Dim count = " + regisDim.count())

    sparkSession.close()
  }

}
