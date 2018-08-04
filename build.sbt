name := "dataframe-examples"

version := "1.0"

scalaVersion := "2.11.8"

// Code below is to resolve an error on line no. 18 fetch jar for redshift on local but if it cannot download
// it will download from resolver..it will check line 19 and if it fails it goes to line 9 and download
resolvers ++= Seq(
  "Redshift" at "http://redshift-maven-repository.s3-website-us-east-1.amazonaws.com/release"
)


libraryDependencies += "org.apache.spark" % "spark-core_2.11" % "2.1.1"
libraryDependencies += "org.apache.spark" % "spark-sql_2.11" % "2.1.1"
libraryDependencies += "org.apache.hadoop" % "hadoop-aws" % "2.7.4"
//libraryDependencies += "com.oracle" % "ojdbc14" % "10.2.0.4.0"
libraryDependencies += "com.databricks" % "spark-redshift_2.11" % "3.0.0-preview1"
libraryDependencies += "com.amazon.redshift" % "redshift-jdbc42" % "1.2.1.1001"
libraryDependencies += "com.springml" % "spark-sftp_2.11" % "1.1.1"
//
dependencyOverrides += "com.databricks" % "spark-avro_2.11" % "3.2.0"
// https://mvnrepository.com/artifact/mysql/mysql-connector-java
libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.11"


