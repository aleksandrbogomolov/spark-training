package com.aleksandrbogomolov.df

import org.apache.spark.sql._
import org.apache.spark.sql.types._

object DataFrameTraining extends App {

  val spark = SparkSession.builder().appName("training").config("spark.master", "local").getOrCreate()
  val initDF = readSrc("/Users/aleksandrbogomolov/Documents/Spark/thads2013n.txt")

  def readSrc(path: String): DataFrame = {
    val rdd = spark.sparkContext.textFile(path)
    val columns = rdd.first().split(",").toList
    val schema = dfSchema(columns)

    val rows = rdd
      .mapPartitionsWithIndex((i, it) => if (i == 0) it.drop(1) else it)
      .map(_.split(",").toList)
      .map(row)

    spark.createDataFrame(rows, schema)
  }

  def dfSchema(columns: List[String]): StructType = {
    StructType(StructField(columns.head, StringType, nullable = false) :: columns.tail.map(StructField(_, StringType,
      nullable = false)))
  }

  def row(list: List[String]): Row = {
    Row(list.head.toString :: list.tail.map(_.toString): _*)
  }

  initDF.show()
}
