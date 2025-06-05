name := "scala-spark-big-data"

version := "1.0.0"

scalaVersion := "2.12.15"

val sparkVersion = "3.3.0"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-mllib" % sparkVersion,
  "org.apache.spark" %% "spark-streaming" % sparkVersion,
  "org.apache.spark" %% "spark-graphx" % sparkVersion,
  "org.apache.spark" %% "spark-hive" % sparkVersion,
  
  // Delta Lake
  "io.delta" %% "delta-core" % "2.0.0",
  
  // AWS Integration
  "org.apache.hadoop" % "hadoop-aws" % "3.3.1",
  "com.amazonaws" % "aws-java-sdk-bundle" % "1.12.262",
  
  // JSON Processing
  "org.json4s" %% "json4s-jackson" % "4.0.5",
  
  // Config
  "com.typesafe" % "config" % "1.4.2",
  
  // Logging
  "org.slf4j" % "slf4j-api" % "1.7.36",
  "ch.qos.logback" % "logback-classic" % "1.2.11",
  
  // Testing
  "org.scalatest" %% "scalatest" % "3.2.12" % Test,
  "org.mockito" % "mockito-core" % "4.6.1" % Test
)

// Assembly settings
assembly / assemblyMergeStrategy := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case "reference.conf" => MergeStrategy.concat
  case x => MergeStrategy.first
}

// Java options
javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

// Scala options
scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-unchecked",
  "-Xfatal-warnings",
  "-language:implicitConversions",
  "-language:postfixOps"
)

// Test settings
Test / fork := true
Test / parallelExecution := false

