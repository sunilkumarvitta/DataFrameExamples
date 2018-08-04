package com.ex


import org.apache.spark
import org.apache.spark.sql.SparkSession
object mysql {
  def main(args: Array[String]): Unit = {
    import java.sql.DriverManager
    // Loading the database driver// Loading the database driver

    //Class.forName("com.mysql.cj.jdbc.Driver")
    val conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sunil", "root", "Temp1234")
    val stmt = conn.createStatement
     val resultSet = stmt.executeQuery("select * from emp1")
    while ( resultSet.next)
      println(resultSet.getInt(1) + "  " + resultSet.getString(2) )
    //stmt.close
  }
}

