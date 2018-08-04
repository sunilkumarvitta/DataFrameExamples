package com.ex

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types._

/**
  * Created by sidhartha.ray on 27-11-2017.
  */
object Ex6_TextfileToDataframe {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder.master("local[*]").appName("Dataframe Example").getOrCreate()

    sparkSession.sparkContext.hadoopConfiguration.set("fs.s3n.awsAccessKeyId", "AKIAILD7YRY57HZC3LYQ")
    sparkSession.sparkContext.hadoopConfiguration.set("fs.s3n.awsSecretAccessKey", "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")

    val financesDF = sparkSession.read
//      .option("header","true")
      .csv("s3n://sunilvittarent2/finances.csv")
      .toDF("id", "hasDebt", "hasFinancialDependents", "hasStudentLoans", "income")

    financesDF.show()

    sparkSession.close()
  }
}
