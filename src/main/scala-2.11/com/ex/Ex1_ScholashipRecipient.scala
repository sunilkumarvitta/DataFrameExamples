package com.ex
import org.apache.spark.sql.SparkSession

/**
  * Count: Swiss students who have debt & financial dependents:
  * 1. Inner join first
  * 2. Filter to select people in Switzerland
  * 3. Filter to select people with debt & financial dependents
  *
  */
object Ex1_ScholashipRecipient {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder.master("local[*]").appName("Dataframe Example123").getOrCreate()

    sparkSession.sparkContext.hadoopConfiguration.set("fs.s3n.awsAccessKeyId", "AKIAILD7YRY57HZC3LYQ")
    sparkSession.sparkContext.hadoopConfiguration.set("fs.s3n.awsSecretAccessKey", "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")

    val demographicsRDD = sparkSession.sparkContext.textFile("s3n://sunilvittarent2/demographic.csv")
    println("# of records = " + demographicsRDD.count())

    val financesRDD = sparkSession.sparkContext.textFile("s3n://sunilvittarent2/finances.csv")
    val coursesRDD = sparkSession.sparkContext.textFile("s3n://sunilvittarent2/course.csv")

    val demographics = demographicsRDD.map(record => record.split(","))
        .map(record => Demographic(record(0).toInt, record(1).toInt, record(2).toBoolean,
              record(3), record(4), record(5).toBoolean, record(6).toBoolean, record(7).toInt))
        .map(demographic => (demographic.id, demographic))      //Pair RDD, (id, demographics)
    val finances = financesRDD.map(record => record.split(","))
          .map(record => Finance(record(0).toInt, record(1).toBoolean, record(2).toBoolean,
              record(3).toBoolean, record(4).toInt))
        .map(finance => (finance.id, finance))                  //Pair RDD, (id, finances)
    val courses = coursesRDD.map(record => record.split(","))
        .map(record => Course(record(0).toInt, record(1)))
        .map(course => (course.id, course))                  //Pair RDD, (id, finances)

    demographics.join(finances)
          .filter(p => p._2._1.country == "Switzerland"
            && p._2._2.hasFinancialDependents
            && p._2._2.hasDebt)
          .map(p => (p._2._1.courseId, (p._2._1, p._2._2)))
          .join(courses)
          .map(p => (p._2._1._1.id, (p._2._1._1, p._2._1._2, p._2._2)))
          .foreach(println)


    sparkSession.close()
  }
}