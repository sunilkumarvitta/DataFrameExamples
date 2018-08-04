package com.ex
import org.apache.spark
import org.apache.spark.sql.SparkSession
object temp {
def main(args: Array[String]): Unit = {
    import java.sql.DriverManager
  val conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sunil","root","Temp1234")
  val stmt = conn.createStatement
  val resultset = stmt.executeQuery("select * from emp1 where id <> 1")
  while (resultset.next)
    println(resultset.getInt(1) + "  " + resultset.getString(2) )
  }
}
