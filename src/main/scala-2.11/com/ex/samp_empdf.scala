package com.ex

import org.apache.spark.sql.SparkSession

object samp_empdf {
  //val empPath = "C:\\Sunil\\Spark\\Fractus\\dataframe-examples\\src\\main\\resources\\employee.csv"

  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder.master("local[*]").appName("Dataframe Example").getOrCreate()
    val financesDF = sparkSession.read
      //      .option("header","true")
      .csv("C:\\Sunil\\Spark\\Fractus\\dataframe-examples\\src\\main\\resources\\employee.csv")
      .toDF("empid", "empname", "sname", "sid", "bonus", "increment", "spl_alw", "location", "dept", "perf")

    financesDF.show()

    sparkSession.close()
  }
}
