package com.ex

import org.apache.spark.sql.{SaveMode, SparkSession}

object Ex11_PersistInRedshiftDb {
  def main(args: Array[String]): Unit = {
    try {
      val sparkSession = SparkSession.builder.master("local[*]").appName("Dataframe Example").getOrCreate()

      sparkSession.sparkContext.hadoopConfiguration.set("fs.s3n.awsAccessKeyId", "AKIAILD7YRY57HZC3LYQ")
      sparkSession.sparkContext.hadoopConfiguration.set("fs.s3n.awsSecretAccessKey", "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")

      val postsDf = sparkSession.read
        .option("header","true")
        .option("delimiter", "|")
        .csv("s3n://sunilvittarent2/posts.csv")

//      postsDf.show()
//      postsDf.printSchema()
      postsDf.write
        .format("com.databricks.spark.redshift")
        .option("url", "jdbc:redshift://redshiftcluster.czv3lhhopyhk.eu-west-1.redshift.amazonaws.com:5439/test_db?user=master&password=Temp1234")
        .option("tempdir", "s3n://sunilvittarent2/temp")
        .option("forward_spark_s3_credentials", "true")
        .option("dbtable", "staging.POST")
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
