package com.ex

import org.apache.spark.sql.SparkSession

object df_ex {

  val peopleJsonPath = "G:\\dataframe-examples\\src\\main\\resources\\people.json"

  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().master("local[*]").appName("Comparision with Dataframe").getOrCreate()

    // Create dataframe from json file
    val peopleDf = sparkSession.read.json(peopleJsonPath)
    peopleDf.printSchema()

    // Applying filter on dataframe
    peopleDf.filter("age > 20").show()
    sparkSession.stop()
  }
}

