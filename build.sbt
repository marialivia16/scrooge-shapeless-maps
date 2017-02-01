name := "scrooge-shapeless-maps"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.apache.thrift" % "libthrift" % "0.8.0",
  "org.scalatest" %% "scalatest" % "2.2.6" % "test",
  "com.chuusai" %% "shapeless" % "2.3.2",
  "com.twitter" %% "scrooge-core" % "3.17.0"
)

mainClass in (Compile,run) := Some("App")

com.twitter.scrooge.ScroogeSBT.newSettings

unmanagedSourceDirectories in Compile += baseDirectory.value / "target" / "scala-2.11" / "src_managed"
