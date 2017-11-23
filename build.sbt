name := "scrooge-shapeless-maps"

version := "1.0"

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  "org.apache.thrift" % "libthrift" % "0.10.0",
  "org.scalatest" %% "scalatest" % "3.0.4" % "test",
  "com.chuusai" %% "shapeless" % "2.3.2",
  "com.twitter" %% "scrooge-core" % "17.11.0"
)

mainClass in (Compile,run) := Some("App")