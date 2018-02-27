package com.aleksandrbogomolov.df

import org.apache.spark.sql._
import org.apache.spark.sql.types._
import com.aleksandrbogomolov.helper.Conversion._

object DataFrameTraining extends App {

  val spark = SparkSession.builder().appName("training").config("spark.master", "local").getOrCreate()
  val moviesDF = readSrc("/Users/aleksandrbogomolov/Documents/Spark/ml-latest/movies.csv")
  val ratingsDF = readSrc("/Users/aleksandrbogomolov/Documents/Spark/ml-latest/ratings.csv")

  def readSrc(path: String): DataFrame = {
    val rdd = spark.sparkContext.textFile(path)
    val columns = rdd.first().split(",").toList
    val schema = dfSchema(columns)
    val rows = rdd.mapPartitionsWithIndex((i, it) => if (i == 0) it.drop(1) else it).map(splitRow).map(row)
    spark.createDataFrame(rows, schema)
  }

  def dfSchema(columns: List[String]): StructType = {
    StructType(StructField(columns.head, StringType, nullable = false)
      :: columns.tail.map(StructField(_, StringType, nullable = false)))
  }

  def splitRow(line: String): List[String] = {
    if (line.contains("\"")) {
      line.split("\"").map(removeComa)
    } else {
      line.split(",")
    }
  }

  def removeComa(word: String): String = if (word.startsWith(",") || word.endsWith(",")) word.replace(",", "") else word

  def row(list: List[String]): Row = {
    Row(list.head.toString :: list.tail.map(_.toString): _*)
  }

  moviesDF.createOrReplaceTempView("movies")
  ratingsDF.createOrReplaceTempView("ratings")

  val joinByMovieId = spark.sql("select * from movies m join ratings r on m.movieId = r.movieId")
  joinByMovieId.show()
}
