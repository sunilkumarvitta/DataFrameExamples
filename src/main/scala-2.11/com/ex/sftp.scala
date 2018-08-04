package com.ex
import org.apache.spark.sql.SparkSession
object sftp {
  def main(args: Array[String]): Unit = {
    try {
      val sparkSession = SparkSession.builder.master("local[*]").appName("Dataframe Example").getOrCreate()
      val filePath = "/sftpfolder/receipts_delta_GBR_14_10_2017.csv";
      val df = sparkSession.read.
        format("com.springml.spark.sftp").
        option("host", "localhost").
        option("port", "22").
        option("username", "admin").
        option("password", "Temp1234").
        option("fileType", "csv").
        option("delimiter", "|").
        option("inferSchema", true).
        load(filePath)
      df.show(10)
      sparkSession.close()

    } catch {
      case ex: Throwable =>
        ex.printStackTrace()
      case ex: Exception => println(ex.getMessage)
    }
  }}
