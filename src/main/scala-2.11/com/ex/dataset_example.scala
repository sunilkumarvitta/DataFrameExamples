package com.ex

import com.ex.person
import org.apache.spark.sql.{Dataset, SparkSession}
object dataset_example {

  val peopleJsonPath = "C:\\Sunil\\Spark\\Fractus\\dataframe-examples\\src\\main\\resources\\people.json"

  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().master("local[*]").appName("Comparision with Dataframe").getOrCreate()

    // Create dataframe from json file
    val peopleDf = sparkSession.read.json(peopleJsonPath)
    peopleDf.printSchema()

    // Get a dataset out of dataframe
    import sparkSession.implicits._
    val peopleDs = peopleDf.as[person]
    peopleDs.filter(p => p.age > 26).show()

    // The below will give you compilation error
    // peopleDs.filter(p => p.salary > 1000).show()

    sparkSession.stop()
  }
}