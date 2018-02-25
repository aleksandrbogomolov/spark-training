name := "spark-training"

version := "0.1"

scalaVersion := "2.11.12"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "2.2.1",
  "org.apache.spark" %% "spark-sql" % "2.2.1",
  "org.apache.spark" %% "spark-streaming" % "2.2.1" % "provided",
  "org.apache.spark" %% "spark-mllib" % "2.2.1",
  "org.apache.spark" %% "spark-graphx" % "2.2.1"
)
