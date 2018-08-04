package com.ex

import org.apache.spark.sql.SparkSession

object df_proj {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().master("local[*]").appName("df_proj").getOrCreate()
    //val emp_rdd = sparkSession.sparkContext.textFile("C:\\Sunil\\Spark\\Fractus\\dataframe-examples\\src\\main\\resources\\emp.csv")
    //emp_rdd.foreach(println)
    //print("total records count : " + emp_rdd.count() + "   ")
    /*val fir = emp_rdd.first()
    val emp_fun = emp_rdd.filter(x=>x!=fir).map(x=>x.split(",")).map(x => emp(x(0).toLong,x(1).toString,x(2).toString,x(3).toString,x(4).toString,x(5).toString,
      x(6).toString,x(7).toString,x(8).toString,x(9).toString,x(10).toString,x(11).toString,x(12).toDouble,x(13).toDouble,x(14).toString,
      x(15).toString,x(16).toString,x(17).toInt,x(18).toInt,x(19).toString,x(20).toString,x(21).toInt,x(22).toString,
      x(23).toString,x(24).toDouble,x(25).toLong,x(26).toString,x(27),x(28).toString,x(29).toString,x(30).toString,
      x(31).toString))
    import sparkSession.sqlContext.implicits._
    val emp_df = emp_fun.toDF()*/

    //emp_df.select("emp_id").show()

    //emp_df.select("gender","salary").groupBy("gender").sum().show()
    //emp_df.createOrReplaceTempView("emp_tab")
    //sparkSession.sql("select gender,sum(salary) as sal from emp_tab group by gender").show()
    //val emp1_df = sparkSession.read.csv("C:\\Sunil\\Spark\\Fractus\\dataframe-examples\\src\\main\\resources\\emp.csv")
    //emp1_df.show()
    val df = sparkSession.read.format("csv").option("header", "true").option("inferSchema","true").load("C:\\Sunil\\Spark\\Fractus\\dataframe-examples\\src\\main\\resources\\emp1.csv")
    //df.createOrReplaceTempView("edf")
    df.printSchema()
    //sparkSession.sql("select gender,sum(cast(salary)) as sal from edf group by gender").show()
    //df.select("gender","salary").groupBy("gender").sum("salary").show()
    sparkSession.close()
  }

}
