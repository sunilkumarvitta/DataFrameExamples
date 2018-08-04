package com.ex

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._

object Ex7_SqlApi {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder.master("local[2]").appName("Dataframe Example").getOrCreate()

    sparkSession.sparkContext.hadoopConfiguration.set("fs.s3n.awsAccessKeyId", "AKIAILD7YRY57HZC3LYQ")
    sparkSession.sparkContext.hadoopConfiguration.set("fs.s3n.awsSecretAccessKey", "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")

    val postsDFTemp = sparkSession.read
      .option("header","true")
      .option("delimiter", "|")
      .csv("s3n://sunilvittarent2/posts.csv")

    import sparkSession.implicits._
    var postsDF = postsDFTemp
      .withColumn("authorID", $"authorID".cast(IntegerType))
      .withColumn("likes",  $"likes".cast(IntegerType))
    postsDF.printSchema()
    postsDF.show()

    postsDF.groupBy($"authorID", $"subforum")
      .agg(count($"authorId"))
      .orderBy($"subforum", $"count(authorId)".desc)
      .show()

    sparkSession.close()
  }
}
