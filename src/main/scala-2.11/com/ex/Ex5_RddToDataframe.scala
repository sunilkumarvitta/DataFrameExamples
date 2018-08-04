package com.ex

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql.Row

/**
  * Created by sidhartha.ray on 27-11-2017.
  */
object Ex5_RddToDataframe {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder.master("local[*]").appName("Dataframe Example").getOrCreate()

    sparkSession.sparkContext.hadoopConfiguration.set("fs.s3n.awsAccessKeyId", "AKIAILD7YRY57HZC3LYQ")
    sparkSession.sparkContext.hadoopConfiguration.set("fs.s3n.awsSecretAccessKey", "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")

    val financesRawRDD = sparkSession.sparkContext.textFile("s3n://sunilvittarent2/finances.csv")
    val financesRDD = financesRawRDD.map(record => record.split(","))
      .map(record => Row(record(0).toInt, record(1).toBoolean, record(2).toBoolean,
        record(3).toBoolean, record(4).toInt))

    // Convert to DataFrame
    val financesSchema = StructType(
        StructField("id", IntegerType, true) ::
        StructField("hasDebt", BooleanType, true) ::
        StructField("hasFinancialDependents", BooleanType, true) ::
        StructField("hasStudentLoans", BooleanType, true) ::
        StructField("income", IntegerType, true) :: Nil)

    val financesDF = sparkSession.createDataFrame(financesRDD, financesSchema)
    financesDF.show()

    sparkSession.close()
  }
}
