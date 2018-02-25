package com.aleksandrbogomolov.rdd

import org.apache.spark.{SparkConf, SparkContext}

object Main extends App {

  val sc = new SparkContext("local", "rdd", new SparkConf())

  val stations = sc.textFile("/Users/aleksandrbogomolov/Documents/Spark/stations.csv")

  private val strings: Array[String] = stations.takeSample(withReplacement = false, 5)

  strings.foreach(println)
}
