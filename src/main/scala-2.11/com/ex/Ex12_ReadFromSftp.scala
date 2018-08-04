package com.ex

import org.apache.spark.sql.{SaveMode, SparkSession}

object Ex12_ReadFromSftp {
  def main(args: Array[String]): Unit = {
    try {
      val sparkSession = SparkSession.builder.master("local[*]").appName("Dataframe Example").getOrCreate()

      sparkSession.sparkContext.hadoopConfiguration.set("fs.s3n.awsAccessKeyId", "AKIAJTEUGCOW4GQUEGAQ")
      sparkSession.sparkContext.hadoopConfiguration.set("fs.s3n.awsSecretAccessKey", "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")

      val postsDf = sparkSession.read
        .option("header","true")
        .option("delimiter", "|")
        .csv("s3n://spark-session-2017/sample-data/posts.csv")

      //      postsDf.show()

      postsDf.write
        .format("com.databricks.spark.redshift")
        .option("url", "jdbc:redshift://test.czrbxeb11tgi.eu-west-1.redshift.amazonaws.com:5439/test?user=test&password=Temp1234")
        .option("tempdir", "s3n://spark-session-2017/temp")
        .option("forward_spark_s3_credentials", "true")
        .option("dbtable", "PUBLIC.POST")
        .mode(SaveMode.Append)
        .save()

      sparkSession.close()
    }  catch{
      case ex: Throwable => {
        ex.printStackTrace()
      }
    }
  }
}
